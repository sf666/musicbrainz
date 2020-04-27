package com.coles.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextRepresentation {
    private String language;

    private String script;
}
