package nextcp.musicbrainz.model.release;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Release
{

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Packaging getPackaging()
    {
        return packaging;
    }

    public void setPackaging(Packaging packaging)
    {
        this.packaging = packaging;
    }

    public TextRepresentation getTextRepresentation()
    {
        return textRepresentation;
    }

    public void setTextRepresentation(TextRepresentation textRepresentation)
    {
        this.textRepresentation = textRepresentation;
    }

    public List<Credit> getCredit()
    {
        return credit;
    }

    public void setCredit(List<Credit> credit)
    {
        this.credit = credit;
    }

    public ReleaseGroup getReleaseGroup()
    {
        return releaseGroup;
    }

    public void setReleaseGroup(ReleaseGroup releaseGroup)
    {
        this.releaseGroup = releaseGroup;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public List<Event> getEvents()
    {
        return events;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public int getTrackCount()
    {
        return trackCount;
    }

    public void setTrackCount(int trackCount)
    {
        this.trackCount = trackCount;
    }

    public List<Media> getMedia()
    {
        return media;
    }

    public void setMedia(List<Media> media)
    {
        this.media = media;
    }

    private String id;

    private int score;

    private int count;

    private String title;

    private String status;

    private Packaging packaging;

    @JsonProperty("text-representation")
    private TextRepresentation textRepresentation;

    @JsonProperty("artist-credit")
    private List<Credit> credit;

    @JsonProperty("release-group")
    private ReleaseGroup releaseGroup;

    private String date;

    private String country;

    @JsonProperty("release-events")
    private List<Event> events;

    private String barcode;

    @JsonProperty("track-count")
    private int trackCount;

    private List<Media> media;
}
