package nextcp.musicbrainz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;

import nextcp.musicbrainz.coverart.CoverartService;

@SpringBootTest
@Configuration
@ContextConfiguration(classes = SpringTestConfiguration.class)
@ComponentScan(
{ "nextcp" })
/**
 * How to use coverart service. Inject the services or create them ... 
 */
public class CoverartServiceTest
{
    @Lazy
    @Autowired
    private CoverartService service = null;
    
    
    public CoverartServiceTest()
    {
        if (service == null)
        {
            // Spring not available ... 
            service = new CoverartService();
        }
    }
    
    @Test
    public void testCoverart()
    {
        String result = service.getCoverartUrl("1e0eee38-a9f6-49bf-84d0-45d0647799af");
        
    }
}
