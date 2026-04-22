package nextcp.musicbrainz.coverart;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.client.ContentResponse;
import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PreDestroy;

@Service
public class CoverartService
{
    private static final Logger log = LoggerFactory.getLogger(CoverartService.class.getName());
    private HttpClient httpClient = null;
    private ObjectMapper om = new ObjectMapper();

    public CoverartService()
    {
        initHttpClient();
    }

    public String getCoverartUrl(String releaseId)
    {
        if (StringUtils.isAllBlank(releaseId))
        {
            throw new RuntimeException("releaseId shall not be empty");
        }

        String url = String.format("http://coverartarchive.org/release/%s", releaseId);
        try
        {
            ContentResponse response = httpClient.GET(url);
            if (response.getStatus() == 404)
            {
                log.info("no coverart found for releaseId : " + releaseId);
                return "";
            }

            String body = response.getContentAsString();
            CoverArtImages images = om.readValue(body, CoverArtImages.class);
            if (images != null && images.getImages() != null && images.getImages().get(0) != null)
            {
                return images.getImages().get(0).getImage();
            }
            log.warn("no cover art url found for release id : " + releaseId);
            return "";
        }
        catch (Exception e)
        {
            log.warn("unable to retrieve cover art data from coverartarchive.org", e);
        }
        return "";
    }

    private void initHttpClient()
    {
        try
        {
            httpClient = new HttpClient();
            httpClient.setFollowRedirects(true);
            httpClient.start();
        }
        catch (Exception e)
        {
            log.error("failed to start Jetty HttpClient", e);
        }
    }

    @PreDestroy
    public void shutdown()
    {
        if (httpClient != null && httpClient.isStarted())
        {
            try
            {
                httpClient.stop();
            }
            catch (Exception e)
            {
                log.warn("error stopping Jetty HttpClient", e);
            }
        }
    }
}