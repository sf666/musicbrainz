package com.coles.musicbrainz.model.response;

import com.coles.musicbrainz.model.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistResponse {
    private Date created;

    private int count;

    private int offset;

    private List<Artist> artists;
}
