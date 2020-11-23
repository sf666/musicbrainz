package nextcp.musicbrainz.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
{ "nextcp" })
public class SpringTestConfiguration
{
    @Bean
    public MusicBrainzConfig getConfig()
    {
        return new MusicBrainzConfig();
    }
}
