package com.coles.musicbrainz.model.artist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alias {
    private
    String id;

    @JsonProperty("sort-name")
    private String sortName;

    @JsonProperty("typeId")
    private String typeId;

    private String name;

    private String locale;

    private String type;

    private boolean primary;

    @JsonProperty("begin-date")
    private String beginDate;

    @JsonProperty("end-date")
    private String endDate;


}
