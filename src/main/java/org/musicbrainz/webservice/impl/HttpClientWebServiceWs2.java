package org.musicbrainz.webservice.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.client.Authentication;
import org.eclipse.jetty.client.ContentResponse;
import org.eclipse.jetty.client.DigestAuthentication;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.StringRequestContent;
import org.eclipse.jetty.http.HttpMethod;
import org.musicbrainz.webservice.DefaultWebServiceWs2;
import org.musicbrainz.webservice.WebServiceException;
import org.musicbrainz.wsxml.MbXMLException;
import org.musicbrainz.wsxml.element.Metadata;

/**
 * A simple http client.
 */
public class HttpClientWebServiceWs2 extends DefaultWebServiceWs2 {

    private static final String USER_AGENT = "musicbrainz-lib-java/1.2 ( https://github.com/sf666/musicbrainz )";

    private Log log = LogFactory.getLog(HttpClientWebServiceWs2.class);

    private static HttpClient httpClient = null;

    /**
     * Default constructor creates a HttpClient with default properties.
     */
    public HttpClientWebServiceWs2() {
        if (httpClient == null) {
            initHttpClient(null, null);
        }
    }

    public HttpClientWebServiceWs2(String user, String pass, String clientName) {
        if (httpClient == null) {
            initHttpClient(user, pass);
        }
        setClient(clientName);
    }

    private void initHttpClient(String user, String pass) {
        try {
            httpClient = new HttpClient();
            httpClient.setConnectTimeout(10_000);
            httpClient.setIdleTimeout(10_000);
            httpClient.setFollowRedirects(true);

            if (user != null && pass != null) {
                httpClient.getAuthenticationStore().addAuthentication(
                    new DigestAuthentication(URI.create("https://musicbrainz.org"), Authentication.ANY_REALM, user, pass)
                );
            }

            httpClient.start();
        } catch (Exception e) {
            log.error("failed to start Jetty HttpClient", e);
        }
    }

    @Override
    public void login(String username, String password) {
        try {
            String formBody = "username=" + username + "&password=" + password;
            httpClient.newRequest("https://musicbrainz.org/login")
                .method(HttpMethod.POST)
                .body(new StringRequestContent("application/x-www-form-urlencoded", formBody))
                .send();
        } catch (Exception e) {
            log.warn("login failed to musicbrainz.org", e);
        }
    }

    /**
     * @return HTML page content or NULL, if page is not available
     */
    public String getUserRatingsPage(String user, int page) throws WebServiceException {
        String requestUrl = String.format("https://musicbrainz.org/user/%s/ratings/recording?page=%d", user, page);
        try {
            ContentResponse response = httpClient.newRequest(requestUrl)
                .method(HttpMethod.GET)
                .send();
            // detect redirect away from page (not authenticated)
            if (response.getRequest().getURI().toString().equals(requestUrl)) {
                return response.getContentAsString();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn("unable to retrieve rating data from musicbrainz.org", e);
        }
        return null;
    }

    @Override
    protected Metadata doGet(String url) throws WebServiceException, MbXMLException {
        boolean repeat = true;
        int trial = 0;
        int maxtrial = 5;

        while (repeat) {
            trial++;

            // MusicBrainz BUG workaround
            if (!url.contains("?inc=")) {
                url += "&fmt=xml";
            }
            System.out.println("Hitting url: " + url);

            try {
                ContentResponse response = httpClient.newRequest(url)
                    .method(HttpMethod.GET)
                    .headers(h -> h
                        .put("User-Agent", USER_AGENT)
                        .put("Accept", "application/xml"))
                    .send();

                Metadata md = handleResponse(response);

                if (md == null && trial > maxtrial) {
                    String em = "ABORTED: web service returned an error " + maxtrial + " time consecutively";
                    log.error(em);
                    throw new WebServiceException(em);
                } else if (md != null) {
                    return md;
                }
            } catch (WebServiceException | MbXMLException e) {
                throw e;
            } catch (Exception e) {
                log.error("Fatal transport error: " + e.getMessage());
                throw new WebServiceException(e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    protected Metadata doPost(String url, Metadata md) throws WebServiceException, MbXMLException {
        String postBody = getWriter().getXmlString(md);
        try {
            ContentResponse response = httpClient.newRequest(url)
                .method(HttpMethod.POST)
                .headers(h -> h
                    .put("User-Agent", USER_AGENT)
                    .put("Accept", "application/xml"))
                .body(new StringRequestContent("application/xml; charset=UTF-8", postBody))
                .send();
            return handleResponse(response);
        } catch (WebServiceException | MbXMLException e) {
            throw e;
        } catch (Exception e) {
            log.error("Fatal transport error: " + e.getMessage());
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected Metadata doPut(String url) throws WebServiceException, MbXMLException {
        try {
            ContentResponse response = httpClient.newRequest(url)
                .method(HttpMethod.PUT)
                .headers(h -> h
                    .put("User-Agent", USER_AGENT)
                    .put("Accept", "application/xml"))
                .body(new StringRequestContent("application/xml; charset=UTF-8", ""))
                .send();
            return handleResponse(response);
        } catch (WebServiceException | MbXMLException e) {
            throw e;
        } catch (Exception e) {
            log.error("Fatal transport error: " + e.getMessage());
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected Metadata doDelete(String url) throws WebServiceException, MbXMLException {
        try {
            ContentResponse response = httpClient.newRequest(url)
                .method(HttpMethod.DELETE)
                .headers(h -> h
                    .put("User-Agent", USER_AGENT)
                    .put("Accept", "application/xml"))
                .body(new StringRequestContent("application/xml; charset=UTF-8", ""))
                .send();
            return handleResponse(response);
        } catch (WebServiceException | MbXMLException e) {
            throw e;
        } catch (Exception e) {
            log.error("Fatal transport error: " + e.getMessage());
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Metadata handleResponse(ContentResponse response) throws MbXMLException, WebServiceException {
        lastHitTime = System.currentTimeMillis();
        int statusCode = response.getStatus();
        switch (statusCode) {
            case 503: {
                lastHitTime = System.currentTimeMillis();
                waitSeconds(1);
                return null;
            }
            case 502: {
                lastHitTime = System.currentTimeMillis();
                waitSeconds(1);
                return null;
            }
            case 200: {
                boolean debug_response = false;
                try (InputStream instream = new ByteArrayInputStream(response.getContent())) {
                    Metadata mtd;
                    if (debug_response) {
                        String resp = IOUtils.toString(instream, "UTF-8");
                        mtd = getParser().parse(IOUtils.toInputStream(resp, "UTF-8"));
                    } else {
                        mtd = getParser().parse(instream);
                    }
                    lastHitTime = System.currentTimeMillis();
                    return mtd;
                } catch (IOException e) {
                    throw new WebServiceException(e.getMessage(), e);
                }
            }
            default: {
                log.error("Fatal web service error: " + statusCode);
                throw new WebServiceException(statusCode + " " + response.getReason());
            }
        }
    }

    private static long lastHitTime = 0;

    private static void waitSeconds(int seconds) {
        long t1;
        if (lastHitTime > 0) {
            do {
                t1 = System.currentTimeMillis();
            } while (t1 - lastHitTime < seconds * 1000);
        }
    }
}