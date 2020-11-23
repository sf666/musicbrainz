package nextcp.musicbrainz.controller;

import nextcp.musicbrainz.model.response.ArtistList;
import nextcp.musicbrainz.service.MusicBranizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {

    private MusicBranizService musicBranizService;

    @Autowired
    public void setMusicBrainzService(MusicBranizService musicBranizService) {
        this.musicBranizService = musicBranizService;
    }

    @GetMapping("/artists")
    public ArtistList getArtists(@RequestParam(value = "name", defaultValue = "") String name) throws Exception {
        return musicBranizService.queryArtist(name);
    }
}
