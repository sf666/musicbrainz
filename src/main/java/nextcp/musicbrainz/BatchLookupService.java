package nextcp.musicbrainz;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import nextcp.musicbrainz.coverart.CoverartService;

@Service
public class BatchLookupService
{
    private static final Logger log = LoggerFactory.getLogger(BatchLookupService.class.getName());

    private LookupThread lookupThread = null;

    private int timeToSleep = 50;
    
    @Lazy
    @Autowired
    private CoverartService coverartService = null;

    @Lazy
    @Autowired
    private MusicBrainzService mbService = null;

    /**
     * releaseIds to lookup on remote musicbrainz service
     */
    private BlockingQueue<String> releaseIdToDiscover = new LinkedBlockingQueue<>(1000);

    /**
     * Inform listeners of album lookup
     */
    private Set<AlbumLookupCallback> callbacks = new HashSet<>();
    
    private boolean stopLookup = false;

    public BatchLookupService(CoverartService coverartService, MusicBrainzService mbService)
    {
        super();
        this.coverartService = coverartService;
        this.mbService = mbService;
    }

    public void setCoverartService(CoverartService coverartService)
    {
        this.coverartService = coverartService;
    }

    public void setMbService(MusicBrainzService mbService)
    {
        this.mbService = mbService;
    }

    private class LookupThread extends Thread
    {
        @Override
        public void run()
        {
            while (!Thread.interrupted() && !stopLookup)
            {
                synchronized (releaseIdToDiscover)
                {
                    try
                    {
                        while (!stopLookup && !interrupted())
                        {
                            String releaseId = releaseIdToDiscover.take();
                            AlbumDto dto = mbService.getReleaseInfo(releaseId);
                            dto.albumArtUrl = coverartService.getCoverartUrl(releaseId);
                            for (AlbumLookupCallback inform : callbacks)
                            {
                                inform.releaseDiscovered(dto);
                            }
                            Thread.sleep(timeToSleep);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        log.warn("terminating BatchLookupService Thread ... ");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
    
    public void addCallbackListener(AlbumLookupCallback callback)
    {
        callbacks.add(callback);
    }

    public void removeCallbackListener(AlbumLookupCallback callback)
    {
        callbacks.remove(callback);
    }
    
    public void setRequestsPerSeconds(int requestsSec)
    {
        if (requestsSec < 0)
        {
            log.warn("illegal requestsSec " + requestsSec+". Deactivating.");
        }
        if (requestsSec == 0)
        {
            timeToSleep = 0;
        }
        int waitTime = 1000 / requestsSec;
        if (waitTime < 4)
        {
            log.warn("for more than 300 req/sec approval is needed!");
        }
    }
    
    public void stopQueue()
    {
        this.stopLookup = true;
        lookupThread.interrupt();
    }

    public void startQueue()
    {
        this.stopLookup = false;
        lookupThread = new LookupThread();
        lookupThread.setDaemon(true);
        lookupThread.start();
    }

    public void clearReleasesToDiscover()
    {
        releaseIdToDiscover.clear();
    }

    public void queueLookup(String releaseId)
    {
        try
        {
            releaseIdToDiscover.put(releaseId);
        }
        catch (InterruptedException e)
        {
            log.warn("cannot look up relaseId.", e);
        }
    }

    public BatchLookupService()
    {
        setRequestsPerSeconds(50);
        startQueue();
    }
}
