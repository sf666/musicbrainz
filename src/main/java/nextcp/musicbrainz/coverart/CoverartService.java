package nextcp.musicbrainz.coverart;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class CoverartService
{
    private static final Logger log = LoggerFactory.getLogger(CoverartService.class.getName());
    private OkHttpClient okClient = null;
    private ObjectMapper om = new ObjectMapper();
    
    public CoverartService()
    {
        initOkClient();
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
            Request request = new Request.Builder().url(url).get().build();
            Call call = okClient.newCall(request);
            Response response = call.execute();
            if (response.code() == 404)
            {
                log.info("no coverart found for relaseId : " + releaseId);
                return "";
            }
            
            String body = response.body().string();
            CoverArtImages images = om.readValue(body, CoverArtImages.class);
            if (images != null && images.getImages() != null && images.getImages().get(0) != null)
            {
                return images.getImages().get(0).getImage();
            }
            log.warn("no cover art url found for relase id : " + releaseId);
            return "";
        }
        catch (Exception e)
        {
            log.warn("unable to retrieve rating data from musicbrainz.org", e);
        }        
        return "";
    }
    
    private void initOkClient()
    {
        okClient = new OkHttpClient.Builder().cookieJar(new CookieJar()
        {
            private List<Cookie> c = new ArrayList<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
            {
                c = cookies;
                for (Cookie cookie : cookies)
                {
                    System.out.println(cookie);
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url)
            {
                return c;
            }
        }).build();
    }    
}
