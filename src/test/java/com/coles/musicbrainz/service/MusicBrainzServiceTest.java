package com.coles.musicbrainz.service;

import com.coles.musicbrainz.MusicbrainzApplication;
import com.coles.musicbrainz.model.response.ArtistResponse;
import com.coles.musicbrainz.model.response.ReleaseResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MusicbrainzApplication.class)
public class MusicBrainzServiceTest {

    @InjectMocks
    MusicBranizService musicBranizService;
    @Mock
    private MusicBrainzConfig musicBrainzConfig;

    @Test
    public void artistShouldReturn() throws Exception {
        when(musicBrainzConfig.getHost()).thenReturn("https://musicbrainz.org/ws/2/");

        ArtistResponse artistResponse = musicBranizService.queryArtist("test");

        assertTrue(artistResponse != null);
    }

    @Test
    public void artistWithReleasesShouldReturn() throws Exception {
        when(musicBrainzConfig.getHost()).thenReturn("https://musicbrainz.org/ws/2/");

        ReleaseResponse releaseResponse = musicBranizService.queryRelease("3a20365a-a478-4587-a026-3eb501e0e4af");

        assertTrue(releaseResponse != null);
    }
}
