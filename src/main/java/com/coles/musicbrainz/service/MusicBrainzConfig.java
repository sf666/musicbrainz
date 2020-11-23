package com.coles.musicbrainz.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mb")
@Getter
@Setter
public class MusicBrainzConfig {

    private String host;

}
