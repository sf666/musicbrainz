package nextcp.musicbrainz.model.artist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Area
{

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
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

    public LifeSpan getLifeSpan()
    {
        return lifeSpan;
    }

    public void setLifeSpan(LifeSpan lifeSpan)
    {
        this.lifeSpan = lifeSpan;
    }

    public List<String> getIso31661Codes()
    {
        return iso31661Codes;
    }

    public void setIso31661Codes(List<String> iso31661Codes)
    {
        this.iso31661Codes = iso31661Codes;
    }

    private String id;

    private String type;

    @JsonProperty("type-id")
    private String typeId;

    private String name;

    @JsonProperty("sort-name")
    private String sortName;

    @JsonProperty("life-span")
    private LifeSpan lifeSpan;

    @JsonProperty("iso-3166-1-codes")
    private List<String> iso31661Codes;
}
