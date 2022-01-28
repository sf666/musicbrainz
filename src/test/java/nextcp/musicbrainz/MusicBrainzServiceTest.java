package nextcp.musicbrainz;

import org.junit.jupiter.api.Test;
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
public class MusicBrainzServiceTest
{

    @Lazy
    @Autowired
    private MusicBrainzService service = null;
    
    public MusicBrainzServiceTest()
    {
    }
    
    @Test
    public void testMusicBrainzRelease()
    {
        AlbumDto dto = service.getReleaseInfo("1e0eee38-a9f6-49bf-84d0-45d0647799af");
        System.out.println(dto);
    }
    
    @Bean
    public MusicBrainzConfig getConfig()
    {
        MusicBrainzConfig config = new MusicBrainzConfig();
        
        return config;
    }
}
