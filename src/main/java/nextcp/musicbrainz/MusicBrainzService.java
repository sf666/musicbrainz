package nextcp.musicbrainz;

import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.Recording;
import org.musicbrainz.model.entity.RecordingWs2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicBrainzService
{
    private static final Logger log = LoggerFactory.getLogger(MusicBrainzService.class.getName());

    private MusicBrainzConfig config = null;

    @Autowired
    public MusicBrainzService(MusicBrainzConfig config)
    {
        this.config = config;
    }

    /**
     * 
     * @param recordingID
     *            aka trackId
     * @param stars
     *            0 = delete rating, 1-5 stars allowed
     */
    public void setRating(String recordingID, int stars)
    {
        if (stars < 0 || stars > 5)
        {
            throw new RuntimeException("star rating out of bounds. Allowed is 0-5. Given :" + stars);
        }

        Recording controller = new Recording();
        controller.getQueryWs().setUsername(config.username);
        controller.getQueryWs().setPassword(config.password);
        controller.getQueryWs().setClient("nextcp2");
        controller.getIncludes().setUserRatings(true);
        controller.getIncludes().setUserTags(true);

        try
        {
            controller.lookUp(recordingID);
            controller.rate(stars);
        }
        catch (MBWS2Exception e)
        {
            log.warn("Error while setting star rating ... ", e);
        }
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
