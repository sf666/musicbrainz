package com.coles.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
    private String format;

    @JsonProperty("disc-count")
    private int discCount;

    @JsonProperty("track-count")
    private int trackCount;
}
