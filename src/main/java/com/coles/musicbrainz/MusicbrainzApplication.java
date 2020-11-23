package com.coles.musicbrainz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coles"})
public class MusicbrainzApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicbrainzApplication.class, args);
    }

}
