package org.musicbrainz.discid;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;


public class DiscInfo {

  public class TrackInfo {

    /** track number on disc */
    public final int num;
    /** sector offset for this track */
    public final int offset;
    /** length in sectors of this track */
    public final int length;

    public TrackInfo(LibDiscId libdiscid, int i) throws DiscIdException {
      this.num = i;
      this.offset = libdiscid.getTrackOffset(i);
      this.length = libdiscid.getTrackLength(i);
    }

  }

  /** MusicBrainz DiscId for the cd */
  public final String discid;
  /** FreeDB id for the cd
   * FreeDB: <a href="http://www.freedb.org/">http://www.freedb.org/</a><br/>
   * BeNOW FreeDB Project: <a href="http://benow.ca/projects">http://benow.ca/projects</a>
   */
  public final String freeDBid;
  public final String source;
  public final URL submissionURL;
  public final String webServiceURL;
  public final int firstTrackNum;
  public final int lastTrackNum;
  public final int sectors;
  public final Map<Integer, TrackInfo> trackOffsets = new TreeMap<Integer, TrackInfo>();

  public DiscInfo(LibDiscId libdiscid, String source) throws DiscIdException {
    this.discid = libdiscid.getId();
    this.freeDBid = libdiscid.getFreeDBId();
    this.source = source != null ? source : libdiscid.getDefaultDevice();
    this.submissionURL=libdiscid.getSubmissionURL();
    this.webServiceURL=libdiscid.getWebServiceURL();
    this.firstTrackNum=libdiscid.getFirstTrackNum();
    this.lastTrackNum=libdiscid.getLastTrackNum();
    this.sectors=libdiscid.getSectors();
    for (int i = firstTrackNum; i <= lastTrackNum; i++)
      trackOffsets.put(i, new TrackInfo(libdiscid,i));
  }

  public static DiscInfo read() throws DiscIdException {
    return read(null);
  }

  public static DiscInfo read(
      String device) throws DiscIdException {
    LibDiscId libdiscid = new LibDiscId(device);
    DiscInfo discId = new DiscInfo(libdiscid,
      device);
    libdiscid.close();
    return discId;
  }

  @Override
  public String toString() {
    String msg = "Device     : " + source + "\n" + "DiscId     : " + discid + "\n" + "FreeDb Id  : " + freeDBid + "\n"
        + "Submit URL : " + submissionURL + "\n" + "Web Service: " + webServiceURL + "\n" + "First Track: " + firstTrackNum
        + "\n" + "Last Track : " + lastTrackNum + "\n" + "Sectors    : " + sectors + "\n";
    for (Integer key : trackOffsets.keySet()) {
      TrackInfo info = trackOffsets.get(key);
      msg += "  Track " + (key < 10 ? " " : "") + key + " : Length: " + info.length + " Offset: " + info.offset + "\n";
    }
    return msg;
  }

  public static DiscInfo fromOffsets(
      int first,
      int last,
      int[] offsets) throws DiscIdException {
    String offStr = first + " " + last;
    for (int i = 0; i < offsets.length; i++)
      offStr += " " + offsets[i];
    LibDiscId libdiscid = new LibDiscId(first,
      last,
      offsets);
    return new DiscInfo(libdiscid,
      "offsets: " + offStr);

  }

  public static DiscInfo fromOffsets(
      int[] offsets) throws DiscIdException {
    return fromOffsets(1, offsets.length, offsets);
  }
}
