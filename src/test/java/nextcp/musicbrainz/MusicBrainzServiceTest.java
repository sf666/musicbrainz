package nextcp.musicbrainz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@Configuration
@ContextConfiguration(classes = SpringTestConfiguration.class)
@ComponentScan(
{ "nextcp" })
/**
 * This test case demonstrates how to use the service.
 */
public class MusicBrainzServiceTest implements AlbumLookupCallback
{
    @Lazy
    @Autowired
    private MusicBrainzService service = null;
    
    @Lazy
    @Autowired
    private BatchLookupService batchService = null;        

    public MusicBrainzServiceTest()
    {
    }
    
    @Test
    public void testMusicBrainzRelease()
    {
        AlbumDto dto = service.getReleaseInfo("1e0eee38-a9f6-49bf-84d0-45d0647799af");
        assertEquals("Ava Max", dto.albumArtist);
        assertEquals("Not Your Barbie Girl", dto.albumTitle);
        assertEquals("2018-08-13", dto.albumYear);
    }
    
    @Test
    public void testMusicBatchLookup() throws InterruptedException, ExecutionException
    {
        batchService.addCallbackListener(this);
        batchService.queueLookup("1e0eee38-a9f6-49bf-84d0-45d0647799af");
        batchService.queueLookup("ecc28175-395b-4b40-b605-6ee6dc8d2d47");
        
        // give some time to lookup and call the releaseDiscovered() method
        Thread.sleep(4000);
    }
    
    @Bean
    public MusicBrainzConfig getConfig()
    {
        MusicBrainzConfig config = new MusicBrainzConfig();
        
        return config;
    }

    @Override
    public void releaseDiscovered(AlbumDto discoveredRelease)
    {
        // Callbacks from testMusicBatchLookup() will arrive here
        System.out.println(discoveredRelease);
    }
}
