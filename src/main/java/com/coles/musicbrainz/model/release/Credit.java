package com.coles.musicbrainz.model.release;

import com.coles.musicbrainz.model.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credit {

    private String name;

    private Artist artist;

    private String joinphrase;
}
