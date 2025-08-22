package org.musicbrainz.controller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.musicbrainz.webservice.WebService;
import org.musicbrainz.webservice.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RatingController extends Controller
{
    /**
     * A logger
     */
    private static final Logger log = LoggerFactory.getLogger(RatingController.class.getName());
    
    private WebService ws = getReadRatingWs();
    private Pattern ratingTokenizer = Pattern.compile("<li>.*?current-rating.*?>(?<star>.).*?\"\\/recording\\/(?<uuid>.*?)\".*?</li>", Pattern.DOTALL);

    public RatingController()
    {
        // TODO Auto-generated constructor stub
    }

    public HashMap<String, Integer> getAllUserRatings(String user, String pass)
    {
        HashMap<String, Integer> result = new HashMap<>();

        int page = 1;
        try
        {
            String html = ws.getUserRatingsPage(user, page);
            while (html != null)
            {
                analyse(html, result);
                page++;
                html = ws.getUserRatingsPage(user, page);
            }
        }
        catch (WebServiceException e)
        {
            log.warn("Error while quering page", e);
        }
        log.debug("Imported number of elements : " + result.keySet().size());
        return result;
    }

    private void analyse(String html, HashMap<String, Integer> result)
    {
        log.debug("Analysing html rating page ...");
        Matcher m = ratingTokenizer.matcher(html);
        while (m.find())
        {
            try
            {
                result.put(m.group("uuid"), Integer.parseInt(m.group("star")));
            }
            catch (Exception e)
            {
                log.warn("Error while parsing html page", e);
            }
        }
    }

    public void login(String username, String password)
    {
        ws.login(username, password);
    }    
}
