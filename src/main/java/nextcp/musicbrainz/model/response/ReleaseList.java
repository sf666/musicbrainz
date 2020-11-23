package nextcp.musicbrainz.model.response;

import java.util.Date;
import java.util.List;

import nextcp.musicbrainz.model.release.Release;

public class ReleaseList
{
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public List<Release> getReleases()
    {
        return releases;
    }

    public void setReleases(List<Release> releases)
    {
        this.releases = releases;
    }

    private Date created;

    private int count;

    private int offset;

    private List<Release> releases;
}
