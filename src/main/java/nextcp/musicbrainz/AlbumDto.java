package nextcp.musicbrainz;

public class AlbumDto
{

    public String albumArtist;
    public String albumTitle;
    public String albumYear;
    public String albumArtUrl;

    @Override
    public String toString()
    {
        return "AlbumDto [albumArtist=" + albumArtist + ", albumTitle=" + albumTitle + ", albumYear=" + albumYear + ", albumArtUrl=" + albumArtUrl + "]";
    }

}
