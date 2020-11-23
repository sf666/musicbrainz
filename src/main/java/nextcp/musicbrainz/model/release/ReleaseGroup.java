package nextcp.musicbrainz.model.release;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseGroup
{
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public void setPrimaryType(String primaryType)
    {
        this.primaryType = primaryType;
    }

    public List<String> getSecondaryTypes()
    {
        return secondaryTypes;
    }

    public void setSecondaryTypes(List<String> secondaryTypes)
    {
        this.secondaryTypes = secondaryTypes;
    }

    private String id;

    @JsonProperty("type-id")
    private String typeId;

    private String title;

    @JsonProperty("primary-type")
    private String primaryType;

    @JsonProperty("secondary-types")
    private List<String> secondaryTypes;
}
