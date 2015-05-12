package com.jcertif.offlinebox.web;

import com.jcertif.offlinebox.web.crawling.CrawlingStrategy;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Martial SOMDA
 * @since 1.0
 */
public class WebResourceGetter extends RecursiveAction implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(WebResourceGetter.class.getName());
    private static final Pattern CONTENT_DISPOSITION_FILE_NAME_PATTERN = Pattern.compile("^.+filename=\"(.+?)\".*$");
    private final File rootDirectory;
    private int depth = 0;
    private URL url = null;
    private final Client webClient;
    private boolean canParse = false;
    private boolean isValid = true;
    private boolean isCrawable = false;
    private final CrawlingStrategy strategy;
    final long threadId;

    public WebResourceGetter(File rootDirectory, String url, int depth, boolean canParse, CrawlingStrategy strategy) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            this.isValid = false;
            LOGGER.log(Level.SEVERE, null, ex);
        }
        this.rootDirectory = rootDirectory;
        this.depth = depth;
        this.webClient = ClientBuilder.newClient();
        this.canParse = canParse;
        this.strategy = strategy;
        this.isCrawable = strategy.isCrawable(this.url, depth);
        this.threadId = Thread.currentThread().getId();
    }

    @Override
    public void run() {
        fetch();
    }

    @Override
    protected void compute() {
        fetch();
    }

    private void fetch() {
        if (!isValid) {
            LOGGER.log(Level.INFO, "[Thread-{0}]Invalid URL : {1} will be ignored", new Object[]{threadId, url});
        } else if (!isCrawable) {
            LOGGER.log(Level.INFO, "[Thread-{0}]URL : {1} doesn''t respect crawling strategy", new Object[]{threadId, url});
        } else if (canParse) {
            fetchAndComputeNext();
        } else {
            fetchAndSaveDocument();
        }
    }

    private void fetchAndComputeNext() {
        Document doc = fetchDocument();
        String fileName = rootDirectory.getAbsolutePath() + "/" + url.getHost() + "/" + url.getPath();
        if (url.getPath() == null || url.getPath().length() == 0) {
            fileName += "index.html";
        } else if (url.getPath().endsWith("/")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('/')).concat(".html");
        } else {
            fileName = fileName.concat(".html");
        }
        //new WebResourceSaver(doc.html(), fileName).fork();
        new WebResourceSaver(doc.html(), fileName).run();
        fetchLinks(doc);
    }

    private Document fetchDocument() {
        LOGGER.log(Level.FINE, "[Thread-{0}]{1}: Downloading {2}", new Object[]{threadId, depth, url});
        System.out.println();
        String htmlText = webClient.target(url.toString()).request(MediaType.TEXT_HTML).get(String.class);
        return Jsoup.parse(htmlText);
    }

    private void fetchAndSaveDocument() {
        LOGGER.log(Level.FINE, "[Thread-{0}]{1}: Downloading {2}", new Object[]{threadId, depth, url});
        Response response = webClient.target(this.url.toString()).request(MediaType.WILDCARD_TYPE).get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            String contentDispositionHeader = response.getHeaderString("Content-Disposition");
            String fileName = rootDirectory.getAbsolutePath() + "/" + url.getHost() + "/" + url.getPath();
            if (contentDispositionHeader != null) {
                fileName += "/" + CONTENT_DISPOSITION_FILE_NAME_PATTERN.matcher(response.getHeaderString("Content-Disposition")).group(1);
            }
            //new WebResourceSaver(response.readEntity(InputStream.class), fileName).fork();
            new WebResourceSaver(response.readEntity(InputStream.class), fileName).run();
        }
    }

    private void fetchLinks(Document htmlDocument) {
        Elements links = htmlDocument.select("a[href]");
        Elements media = htmlDocument.select("[src]");
        Elements imports = htmlDocument.select("link[href]");
        String linkUrl;
        // save imports and media
        for (Element src : media) {
            linkUrl = src.attr("abs:src");
            if (linkUrl != null && linkUrl.length() > 0) {
                //new WebResourceGetter(rootDirectory, linkUrl, depth + 1, false, strategy).fork();
                new WebResourceGetter(rootDirectory, linkUrl, depth + 1, false, strategy).run();
            }
        }
        for (Element link : imports) {
            linkUrl = link.attr("abs:href");
            if (linkUrl != null && linkUrl.length() > 0) {
                //new WebResourceGetter(rootDirectory, linkUrl, depth + 1, false, strategy).fork();
                new WebResourceGetter(rootDirectory, linkUrl, depth + 1, false, strategy).run();
            }
        }
        //queued real links for download
        for (Element link : links) {
            linkUrl = link.attr("abs:href");
            if (linkUrl != null && linkUrl.length() > 0) {
                //new WebResourceGetter(rootDirectory, linkUrl, depth + 1, true, strategy).fork();
                new WebResourceGetter(rootDirectory, linkUrl, depth + 1, true, strategy).run();
            }
        }
    }

}
