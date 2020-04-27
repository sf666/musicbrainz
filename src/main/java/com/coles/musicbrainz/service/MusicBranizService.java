package com.coles.musicbrainz.service;

import com.coles.musicbrainz.model.response.ArtistResponse;
import com.coles.musicbrainz.model.response.ReleaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MusicBranizService {
    Logger logger = LoggerFactory.getLogger(MusicBranizService.class);

    MusicBrainzConfig musicBrainzConfig;

    @Autowired
    public void setMusicBrainzConfig(MusicBrainzConfig musicBrainzConfig) {
        this.musicBrainzConfig = musicBrainzConfig;
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
    public ArtistResponse queryArtist(String name) throws Exception {
        ArtistResponse artistResponse;

        try {
            artistResponse = new ObjectMapper().readValue(doHttpRequest("artist/?query=artist:" + name), ArtistResponse.class);

            // When only one artist returned in the first response get all releases for this artist
            if (artistResponse.getCount() == 1 && artistResponse.getArtists().size() == 1) {
                ReleaseResponse releaseResponse = queryRelease(artistResponse.getArtists().get(0).getId());
                if (releaseResponse != null)
                    artistResponse.getArtists().get(0).setReleases(releaseResponse.getReleases());
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new Exception("Something happened!");
        }

        return artistResponse;
    }

    /**
     * Query release releases based on artist id
     *
     * @param artistId
     *
     * @return
     *
     * @throws Exception
     */
    public ReleaseResponse queryRelease(String artistId) throws Exception {
        ReleaseResponse releaseResponse;

        try {
            releaseResponse = new ObjectMapper().readValue(doHttpRequest("release/?query=arid:" + artistId), ReleaseResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new Exception("Something happened!");
        }

        return releaseResponse;
    }

    /**
     * Send http request
     *
     * @param uri
     *
     * @return
     */
    public String doHttpRequest(String uri) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(buildUri(uri).toString(), String.class);
        logger.debug("doHttpRequest::get result: [{}]", result);
        return result;
    }

    /**
     * Build uri
     *
     * @param path
     *
     * @return
     *
     * @throws URISyntaxException
     */
    private URI buildUri(String path) throws URISyntaxException {
        return new URI(musicBrainzConfig.getHost() + path + "&fmt=json");
    }

}
