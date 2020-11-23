package nextcp.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nextcp.musicbrainz.model.artist.Artist;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credit
{

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public void setArtist(Artist artist)
    {
        this.artist = artist;
    }

    public String getJoinphrase()
    {
        return joinphrase;
    }

    public void setJoinphrase(String joinphrase)
    {
        this.joinphrase = joinphrase;
    }

    private String name;

    private Artist artist;

    private String joinphrase;
}
