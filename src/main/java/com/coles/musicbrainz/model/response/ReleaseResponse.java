package com.coles.musicbrainz.model.response;

import com.coles.musicbrainz.model.release.Release;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReleaseResponse {
    private Date created;

    private int count;

    private int offset;

    private List<Release> releases;
}
