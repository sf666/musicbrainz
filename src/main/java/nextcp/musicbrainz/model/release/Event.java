package nextcp.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import nextcp.musicbrainz.model.artist.Area;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event
{

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Area getArea()
    {
        return area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    private String date;

    private Area area;
}
