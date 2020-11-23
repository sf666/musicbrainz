package com.coles.musicbrainz.model.release;

import com.coles.musicbrainz.model.artist.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private String date;

    private Area area;
}
