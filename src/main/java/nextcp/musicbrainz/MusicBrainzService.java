package nextcp.musicbrainz;

import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.RatingController;
import org.musicbrainz.controller.Recording;
import org.musicbrainz.controller.Release;
import org.musicbrainz.model.entity.RecordingWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicBrainzService
{
    private static final Logger log = LoggerFactory.getLogger(MusicBrainzService.class.getName());

    @Autowired
    private MusicBrainzConfig config = null;

    public MusicBrainzService()
    {
    }

    public MusicBrainzService(MusicBrainzConfig config)
    {
        super();
        this.config = config;
    }

    public void setConfig(MusicBrainzConfig config)
    {
        this.config = config;
    }

    public AlbumDto getReleaseInfo(String releaseId)
    {
        if (StringUtils.isAllBlank(releaseId))
        {
            throw new RuntimeException("releaseID shall not be empty");
        }

        AlbumDto dto = new AlbumDto();

        Release controller = new Release();
        controller.getReadRatingWs().setClient("nextcp/2");
        // controller.getIncludes().setReleaseRelations(true);
        controller.getIncludes().setMedia(true);
        try
        {
            ReleaseWs2 result = controller.lookUp(releaseId);
            
            dto.releaseId = releaseId;
            dto.albumArtist = result.getArtistCreditString();
            dto.albumTitle = result.getTitle();
            dto.albumYear = result.getDateStr();
        }
        catch (MBWS2Exception e)
        {
            throw new RuntimeException("Musicbrainz lookup failed ...", e);
        }

        return dto;
    }

    /**
     * 
     * @param trackId
     * 
     * @param stars
     *            0 = delete rating, 1-5 stars allowed
     * @throws MBWS2Exception
     */
    public void setRating(String trackId, int stars) throws MBWS2Exception
    {
        if (stars < 0 || stars > 5)
        {
            throw new RuntimeException("star rating out of bounds. Allowed is 0-5. Given : " + stars);
        }

        Recording controller = new Recording(config.username, config.password, "nextcp2");
        controller.getIncludes().setUserRatings(true);
        controller.getIncludes().setUserTags(true);
        controller.lookUp(trackId);
        controller.rate(stars, config.username, config.password);
    }

    public HashMap<String, Integer> getAllUserRatings()
    {
        RatingController controller = new RatingController();
        controller.getReadRatingWs().setClient("nextcp/2");
        controller.login(config.username, config.password);
        return controller.getAllUserRatings(config.username, config.password);
    }

    public Integer getRating(String recordingID)
    {
        Recording controller = new Recording();
        controller.getQueryWs().setUsername(config.username);
        controller.getQueryWs().setPassword(config.password);
        controller.getQueryWs().setClient("nextcp/2");
        controller.getIncludes().setUserRatings(true);
        controller.getIncludes().setUserTags(true);

        try
        {
            RecordingWs2 rec = controller.lookUp(recordingID);
            Float userRating = rec.getUserRating().getAverageRating();
            if (userRating != null)
            {
                return userRating.intValue();
            }
            return null;
        }
        catch (MBWS2Exception e)
        {
            log.warn("Error while getting star rating ... ", e);
            return null;
        }
    }
}
