package nextcp.musicbrainz.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import nextcp.musicbrainz.model.response.ArtistList;
import nextcp.musicbrainz.model.response.ReleaseList;

@SpringBootTest
@Configuration
@ContextConfiguration(classes = SpringTestConfiguration.class)
@ComponentScan(
{ "nextcp.musicbrainz"})
public class MusicBrainzServiceTest {

    @InjectMocks
    MusicBranizService musicBranizService;
    @Mock
    private MusicBrainzConfig musicBrainzConfig;

    @Test
    public void artistShouldReturn() throws Exception {
        when(musicBrainzConfig.getHost()).thenReturn("https://musicbrainz.org/ws/2/");

        ArtistList artistResponse = musicBranizService.queryArtist("test");

        assertTrue(artistResponse != null);
    }

    @Test
    public void artistWithReleasesShouldReturn() throws Exception {
        when(musicBrainzConfig.getHost()).thenReturn("https://musicbrainz.org/ws/2/");

        ReleaseList releaseResponse = musicBranizService.queryRelease("3a20365a-a478-4587-a026-3eb501e0e4af");

        assertTrue(releaseResponse != null);
    }
}
