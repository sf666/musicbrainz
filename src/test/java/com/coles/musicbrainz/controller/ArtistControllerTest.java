package com.coles.musicbrainz.controller;

import com.coles.musicbrainz.model.response.ArtistResponse;
import com.coles.musicbrainz.service.MusicBranizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicBranizService musicBranizService;

    @Test
    public void artistShouldReturnFromService() throws Exception {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setCount(1);
        artistResponse.setCreated(new Date());
        artistResponse.setArtists(new ArrayList<>());
        when(musicBranizService.queryArtist(anyString())).thenReturn(artistResponse);
        mockMvc.perform(get("/artists?name=joe")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.count").value(1));
    }
}
