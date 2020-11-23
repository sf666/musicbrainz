package nextcp.musicbrainz.model.artist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import nextcp.musicbrainz.model.release.Release;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist
{
    private String id;

    private int score;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    private String country;

    private Area area;

    @JsonProperty("begin-area")
    private Area beginArea;

    @JsonProperty("end-area")
    private Area endArea;

    @JsonProperty("life-span")
    private LifeSpan lifeSpan;

    private List<Tag> tags;

    private String disambiguation;

    private List<String> isnis;

    private List<String> ipis;

    private List<Release> releases;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSortName()
    {
        return sortName;
    }

    public void setSortName(String sortName)
    {
        this.sortName = sortName;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Area getArea()
    {
        return area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    public Area getBeginArea()
    {
        return beginArea;
    }

    public void setBeginArea(Area beginArea)
    {
        this.beginArea = beginArea;
    }

    public Area getEndArea()
    {
        return endArea;
    }

    public void setEndArea(Area endArea)
    {
        this.endArea = endArea;
    }

    public LifeSpan getLifeSpan()
    {
        return lifeSpan;
    }

    public void setLifeSpan(LifeSpan lifeSpan)
    {
        this.lifeSpan = lifeSpan;
    }

    public List<Tag> getTags()
    {
        return tags;
    }

    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }

    public String getDisambiguation()
    {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation)
    {
        this.disambiguation = disambiguation;
    }

    public List<String> getIsnis()
    {
        return isnis;
    }

    public void setIsnis(List<String> isnis)
    {
        this.isnis = isnis;
    }

    public List<String> getIpis()
    {
        return ipis;
    }

    public void setIpis(List<String> ipis)
    {
        this.ipis = ipis;
    }

    public List<Release> getReleases()
    {
        return releases;
    }

    public void setReleases(List<Release> releases)
    {
        this.releases = releases;
    }
}
