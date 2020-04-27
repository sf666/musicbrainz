package com.coles.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseGroup {
    private String id;

    @JsonProperty("type-id")
    private String typeId;

    private String title;

    @JsonProperty("primary-type")
    private String primaryType;

    @JsonProperty("secondary-types")
    private List<String> secondaryTypes;
}
