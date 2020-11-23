package com.coles.musicbrainz.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Area {

    private
    String id;

    private String type;

    @JsonProperty("type-id")
    private String typeId;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    @JsonProperty("life-span")
    private LifeSpan lifeSpan;

    @JsonProperty("iso-3166-1-codes")
    private List<String> iso31661Codes;
}
