package com.coles.musicbrainz.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifeSpan {

    private String id;

    private String begin;

    private String end;

    private boolean ended;
}
