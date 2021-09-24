package nextcp.musicbrainz;

import java.util.HashMap;
import java.util.ResourceBundle.Control;

import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.RatingController;
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
     * @throws MBWS2Exception 
     */
    public void setRating(String recordingID, int stars) throws MBWS2Exception
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

        controller.lookUp(recordingID);
        controller.rate(stars);
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
