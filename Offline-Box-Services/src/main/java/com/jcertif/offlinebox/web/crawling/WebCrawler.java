package com.jcertif.offlinebox.web.crawling;

import com.jcertif.offlinebox.web.WebResourceGetter;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * A Web Crawler to enable offline browsing capabilities. 
 * This Implementation uses fork/join asynchronous tasks model.<br />
 * This class is the "main" task and coordinates the others which are of type
 * {@link WebResourceGetter}.
 * </p>
 *
 * @author Martial SOMDA
 * @since 1.0
 * @see WebResourceGetter
 */
public class WebCrawler extends RecursiveAction implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(WebCrawler.class.getName());
    private final ForkJoinPool forkJoinExecutorService;
    private final ExecutorService standardExecutorService;
    private final Collection<String> urls;
    private final File rootDirectory;
    private final CrawlingStrategy strategy;

    public WebCrawler(File rootDirectory, Collection<String> urls, int crawlerMaxDepth) {
        //TODO Benchmark to know if Fork/join API is more efficient in that case than old style ThreadExecutorServices
        //TODO the web crawler is not enough monitorable. Start by implementing a UncaughtExceptionHandler to build
        // a kind of report on what goes wrong, may give it another try
        this.forkJoinExecutorService = new ForkJoinPool(
                Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        this.standardExecutorService = Executors.newCachedThreadPool();
        this.urls = urls;
        this.rootDirectory = rootDirectory;
        this.strategy = new CrawlingStrategy(crawlerMaxDepth);
    }

    public void start() {
        //forkJoinExecutorService.execute(this);
        standardExecutorService.execute(this);
    }

    @Override
    public void run() {
        compute();
    }
    
    @Override
    protected void compute() {
        final long start = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "[Thread-{0}]Web crawler started!", Thread.currentThread().getId());

        for (String url : urls) {
            if (url.endsWith("/")) {
                url = url.substring(url.lastIndexOf("/"));
            }
            new WebResourceGetter(rootDirectory, url, 0, true, strategy).run();
            //new WebResourceGetter(rootDirectory, url, 0, true, strategy).fork();
        }

        //wait until all workers threads are idle (only "main" thread is still active and there's no more job sheduled)
        /*while (!forkJoinExecutorService.isQuiescent()) {
            if (forkJoinExecutorService.getActiveThreadCount() == 1 && forkJoinExecutorService.getQueuedTaskCount() == 0) {
                //shut down the executor service
                forkJoinExecutorService.shutdown();
                break;
            }
        }*/

        LOGGER.log(Level.INFO, "[Thread-{0}]Web crawler finished!", Thread.currentThread().getId());
        LOGGER.log(Level.INFO, "[Thread-{0}]Elapsed time : {1} min.", new Object[]{Thread.currentThread().getId(), Long.toString((System.currentTimeMillis() - start) / (1000 * 60))});
    }

    public void interrupt() {
        System.out.println("[Thread-" + Thread.currentThread().getId() + "]Web crawler interrupted!");
        //forkJoinExecutorService.shutdownNow();
        standardExecutorService.shutdownNow();
    }

    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("executor = ").append(forkJoinExecutorService.toString()).append(
                " visited urls=").append(strategy.getVisitedUrls().size()).append(
                        " purcentage=").append(
                        //assiming this cannot exceed Integer.MAX_VALUE
                        Integer.toString((strategy.getVisitedUrls().size() / (strategy.getVisitedUrls().size() + (int) forkJoinExecutorService.getQueuedTaskCount())) * 100)).append("%");
        return sb.toString();
    }
}
