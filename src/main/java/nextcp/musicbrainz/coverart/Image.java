
package nextcp.musicbrainz.coverart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "approved",
    "back",
    "comment",
    "edit",
    "front",
    "id",
    "image",
    "thumbnails",
    "types"
})
@Generated("jsonschema2pojo")
public class Image {

    @JsonProperty("approved")
    private Boolean approved;
    @JsonProperty("back")
    private Boolean back;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("edit")
    private Integer edit;
    @JsonProperty("front")
    private Boolean front;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("image")
    private String image;
    @JsonProperty("thumbnails")
    private Thumbnails thumbnails;
    @JsonProperty("types")
    private List<String> types = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("approved")
    public Boolean getApproved() {
        return approved;
    }

    @JsonProperty("approved")
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @JsonProperty("back")
    public Boolean getBack() {
        return back;
    }

    @JsonProperty("back")
    public void setBack(Boolean back) {
        this.back = back;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("edit")
    public Integer getEdit() {
        return edit;
    }

    @JsonProperty("edit")
    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    @JsonProperty("front")
    public Boolean getFront() {
        return front;
    }

    @JsonProperty("front")
    public void setFront(Boolean front) {
        this.front = front;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("thumbnails")
    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    @JsonProperty("thumbnails")
    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(List<String> types) {
        this.types = types;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
