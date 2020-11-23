package com.coles.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release {

    private String id;

    private int score;

    private int count;

    private String title;

    private String status;

    private Packaging packaging;

    @JsonProperty("text-representation")
    private TextRepresentation textRepresentation;

    @JsonProperty("artist-credit")
    private List<Credit> credit;

    @JsonProperty("release-group")
    private ReleaseGroup releaseGroup;

    private String date;

    private String country;

    @JsonProperty("release-events")
    private List<Event> events;

    private String barcode;

    @JsonProperty("track-count")
    private int trackCount;

    private List<Media> media;
}
