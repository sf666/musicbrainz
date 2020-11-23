package nextcp.musicbrainz.model.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
    private String format;

    @JsonProperty("disc-count")
    private int discCount;

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public int getDiscCount()
    {
        return discCount;
    }

    public void setDiscCount(int discCount)
    {
        this.discCount = discCount;
    }

    public int getTrackCount()
    {
        return trackCount;
    }

    public void setTrackCount(int trackCount)
    {
        this.trackCount = trackCount;
    }

    @JsonProperty("track-count")
    private int trackCount;
}
