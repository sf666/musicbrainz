package com.coles.musicbrainz.model.artist;

import com.coles.musicbrainz.model.release.Release;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private
    String id;

    private int score;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    private String country;


    private Area area;

    @JsonProperty("begin-area")
    private Area beginArea;

    @JsonProperty("end-area")
    private Area endArea;

    @JsonProperty("life-span")
    private LifeSpan lifeSpan;

    private List<Tag> tags;

    private String disambiguation;

    private List<String> isnis;

    private List<String> ipis;

    private List<Release> releases;
}
