package nextcp.musicbrainz.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nextcp.musicbrainz.model.response.ArtistList;
import nextcp.musicbrainz.model.response.ReleaseList;

@Service
public class MusicBranizService
{
    private static final Logger log = LoggerFactory.getLogger(MusicBranizService.class.getName());

    private MusicBrainzConfig musicBrainzConfig;

    private RestTemplateBuilder builder = null;

    @Autowired
    public MusicBranizService(MusicBrainzConfig musicBrainzConfig)
    {
        this.builder = new RestTemplateBuilder();
        this.musicBrainzConfig = musicBrainzConfig;

        if (!StringUtils.isEmpty(musicBrainzConfig.getUsername()))
        {
            builder.basicAuthentication(musicBrainzConfig.getUsername(), musicBrainzConfig.getPassword());
        }
    }

    /**
     * Query artist based on artist name
     *
     * @param name
     *
     * @return
     *
     * @throws Exception
     */
    public ArtistList queryArtist(String name)
    {
        ArtistList artistResponse = null;
        try
        {
            artistResponse = new ObjectMapper().readValue(doHttpRequest("artist/?query=artist:" + URLEncoder.encode(name, "UTF-8")), ArtistList.class);

            // When only one artist returned in the first response get all releases for this artist
            if (artistResponse.getCount() == 1 && artistResponse.getArtists().size() == 1)
            {
                ReleaseList releaseResponse = queryRelease(artistResponse.getArtists().get(0).getId());
                if (releaseResponse != null)
                    artistResponse.getArtists().get(0).setReleases(releaseResponse.getReleases());
            }

        }
        catch (Exception e)
        {
            log.warn("queryArtist error", e);
        }
        return artistResponse;
    }

    /**
     * Query artist based on artist ID
     */
    public ArtistList queryArtistById(String artistId)
    {
        ArtistList artistResponse = null;
        try
        {
            artistResponse = new ObjectMapper().readValue(doHttpRequest("artist/" + artistId), ArtistList.class);
        }
        catch (Exception e)
        {
            log.warn("queryArtistById error", e);
        }
        return artistResponse;
    }

    /**
     * Query artist based on artist ID
     */
    public ArtistList queryTrackById(String trackId)
    {
        ArtistList artistResponse = null;
        try
        {
            artistResponse = new ObjectMapper().readValue(doHttpRequest(String.format("recording/%s?inc=tags+ratings", trackId)), ArtistList.class);
        }
        catch (Exception e)
        {
            log.warn("queryArtistById error", e);
        }
        return artistResponse;
    }

    /**
     * Query releases of an artist.
     */
    public ReleaseList queryRelease(String artistId)
    {
        ReleaseList releaseResponse = null;
        try
        {
            releaseResponse = new ObjectMapper().readValue(doHttpRequest("release/?query=arid:" + artistId), ReleaseList.class);
        }
        catch (Exception e)
        {
            log.warn("query Release error", e);
        }
        return releaseResponse;
    }

    public void setRating(String trackID, int ratingInStars)
    {
        int ratingVal = ratingInStars * 20;
        
    }
    
    /**
     * Send http request
     */
    public String doHttpRequest(String uri) throws URISyntaxException
    {
        RestTemplate restTemplate = getRestTemplate();

        String result = restTemplate.getForObject(buildUri(uri).toString(), String.class);
        if (log.isDebugEnabled())
        {
            log.debug("doHttpRequest::get result: [{}]", result);
        }
        return result;
    }

    public RestTemplate getRestTemplate()
    {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("user-agent", "nextcp/2  ( https://github.com/sf666/nextcp2 )");
            return execution.execute(request, body);
        };
        return builder.additionalInterceptors(interceptor).build();
    }

    /**
     * Build uri
     *
     * @param path
     *
     * @return
     *
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    private URI buildUri(String path) throws URISyntaxException
    {
        return new URI(musicBrainzConfig.getHost() + path);
    }

}
