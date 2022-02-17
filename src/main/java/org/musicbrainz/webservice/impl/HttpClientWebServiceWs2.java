package org.musicbrainz.webservice.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.musicbrainz.webservice.DefaultWebServiceWs2;
import org.musicbrainz.webservice.WebServiceException;
import org.musicbrainz.wsxml.MbXMLException;
import org.musicbrainz.wsxml.element.Metadata;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple http client using Apache Commons HttpClient.
 * 
 */
public class HttpClientWebServiceWs2 extends DefaultWebServiceWs2
{
    /**
     * A logger
     */
    private Log log = LogFactory.getLog(HttpClientWebServiceWs2.class);

    /**
     * 
     */
    private OkHttpClient okClient = null;

    /**
     * Default constructor creates a httpClient with default properties.
     */
    public HttpClientWebServiceWs2()
    {
        initOkClient();
    }

    private void initOkClient()
    {
        okClient = new OkHttpClient.Builder().cookieJar(new CookieJar()
        {
            private List<Cookie> c = new ArrayList<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
            {
                c = cookies;
                for (Cookie cookie : cookies)
                {
                    System.out.println(cookie);
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url)
            {
                return c;
            }
        }).connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
    }

    @Override
    public void login(String username, String password)
    {
        try
        {
            RequestBody formBody = new FormBody.Builder().add("username", username).add("password", password).build();
            Request loginReq = new Request.Builder().url("https://musicbrainz.org/login").post(formBody).build();
            Call loginRespCall = okClient.newCall(loginReq);
            loginRespCall.execute();
        }
        catch (IOException e)
        {
            log.warn("login failed to musicbrainz.org", e);
        }
    }

    /**
     * @return HTML page content or NULL, if page is not available
     * @throws WebServiceException
     */
    public String getUserRatingsPage(String user, int page) throws WebServiceException
    {
        String requestUrl = String.format("https://musicbrainz.org/user/%s/ratings/recording?page=%d", user, page);
        Request request = new Request.Builder().url(requestUrl).get().build();
        Call call = okClient.newCall(request);
        try
        {
            Response response = call.execute();
            if (response.request().url().toString().equals(requestUrl))
            {
                return response.body().string();
            }
            else
            {
                return null;
            }
        }
        catch (IOException e)
        {
            log.warn("unable to retrieve rating data from musicbrainz.org", e);
        }
        return null;
    }

    @Override
    protected Metadata doGet(String url) throws WebServiceException, MbXMLException
    {

        // retry with new calls if the error is 503 Service unavaillable.
        boolean repeat = true;
        int trial = 0;
        int maxtrial = 5;

        while (repeat)
        {

            trial++;

            // MusicBrainz BUG workaround
            if (!url.contains("?inc="))
            {
                url += "&fmt=xml";
            }
            System.out.println("Hitting url: " + url);

            Request request = new Request.Builder().url(url).header("User-Agent", "musicbrainz-lib-java/1.2 ( https://github.com/sf666/musicbrainz )")
                    .addHeader("Accept", "application/xml").get().build();
            Call call = okClient.newCall(request);
            Metadata md = executeMethod(call);

            if (md == null && trial > maxtrial)
            {
                String em = "ABORTED: web service returned an error " + maxtrial + " time consecutively";
                log.error(em);
                throw new WebServiceException(em);
            }
            else if (md != null)
            {
                return md;
            }

        } // end wile
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.musicbrainz.webservice.DefaultWebServiceWs1#doGet(java.lang.String, java.io.InputStream)
     */
    @Override
    protected Metadata doPost(String url, Metadata md) throws WebServiceException, MbXMLException
    {
        String postBody = getWriter().getXmlString(md);
        RequestBody body = RequestBody.create(postBody, MediaType.parse("application/xml; charset=UTF-8"));
        Request request = new Request.Builder().url(url).header("User-Agent", "musicbrainz-lib-java/1.2 ( https://github.com/sf666/musicbrainz )")
                .addHeader("Accept", "application/xml").post(body).build();
        Call call = okClient.newCall(request);

        return executeMethod(call);
    }

    @Override
    protected Metadata doPut(String url) throws WebServiceException, MbXMLException
    {
        String postBody = "";
        RequestBody body = RequestBody.create(postBody, MediaType.parse("application/xml; charset=UTF-8"));
        Request request = new Request.Builder().url(url).header("User-Agent", "musicbrainz-lib-java/1.2 ( https://github.com/sf666/musicbrainz )")
                .addHeader("Accept", "application/xml").put(body).build();
        Call call = okClient.newCall(request);
        return executeMethod(call);
    }

    @Override
    protected Metadata doDelete(String url) throws WebServiceException, MbXMLException
    {
        String postBody = "";
        RequestBody body = RequestBody.create(postBody, MediaType.parse("application/xml; charset=UTF-8"));
        Request request = new Request.Builder().url(url).header("User-Agent", "musicbrainz-lib-java/1.2 ( https://github.com/sf666/musicbrainz )")
                .addHeader("Accept", "application/xml").delete(body).build();
        Call call = okClient.newCall(request);
        return executeMethod(call);
    }

    private Metadata executeMethod(Call call) throws MbXMLException, WebServiceException
    {
        try
        {
            // Execute the method.
            Response response = call.execute();
            lastHitTime = System.currentTimeMillis();
            int statusCode = response.code();
            switch (statusCode)
            {
                case 503:
                {
                    lastHitTime = System.currentTimeMillis();
                    wait(1);
                    return null;
                }
                case 502: // HttpStatus.SC_BAD_GATEWAY:
                {
                    // Maybe the server is too busy, let's try again.
                    lastHitTime = System.currentTimeMillis();
                    wait(1);
                    return null;
                }
                case 200:
                    boolean debug_response = false;
                    try (InputStream instream = response.body().byteStream())
                    {
                        Metadata mtd = null;
                        if (debug_response)
                        {
                            String resp = org.apache.commons.io.IOUtils.toString(instream, "UTF-8");
                            mtd = getParser().parse(IOUtils.toInputStream(resp, "UTF-8"));
                        }
                        else
                        {
                            mtd = getParser().parse(instream);
                        }
                        lastHitTime = System.currentTimeMillis();
                        return mtd;
                    }
                default:
                {
                    log.error("Fatal web service error: " + statusCode);
                    throw new WebServiceException(statusCode + " " + response.message());
                }

            }
        }
        catch (IOException e)
        {
            log.error("Fatal transport error: " + e.getMessage());
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private static long lastHitTime = 0;

    private static void wait(int seconds)
    {
        long t1;
        if (lastHitTime > 0)
        {
            do
            {
                t1 = System.currentTimeMillis();
            }
            while (t1 - lastHitTime < seconds * 1000);
        }
    }

    /**
     * method to convert an InputStream to a string using the BufferedReader.readLine() method this methods reads the InputStream line by line until the null line is encountered it
     * appends each line to a StringBuilder object for optimal performance
     * 
     * @param is
     * @return
     * @throws IOException
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        if (inputStream != null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line).append("\n");
                }
            }
            finally
            {
                inputStream.close();
            }

            return stringBuilder.toString();
        }
        else
        {
            return null;
        }
    }
}
