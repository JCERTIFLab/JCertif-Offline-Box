package com.jcertif.offlinebox.web.crawling;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import lombok.Getter;

/**
 * <p>
 * Determine if a given URL is elligible for crawling regarding :<br/>
 *     <ul>
 *          <li>Permissions defined in Standard Robot Exclusion (SRE) file (robot.txt)</li>
 *          <li>Pre-configured limits on crawling depth</li>
 *          <li>The fact the URL has already been visited</li>
 *     </ul>
 * </p>
 *
 * @author Martial SOMDA
 * @since 1.0
 */
@Getter
public class CrawlingStrategy {

    private static final Logger LOGGER = Logger.getLogger(CrawlingStrategy.class.getName());
    private static final Pattern USER_AGENT_PATTERN = Pattern.compile("User-agent:");
    private static final Pattern DISALLOW_PATTERN = Pattern.compile("Disallow:");
    private final List<URL> visitedUrls;
    private final Map<String, Collection<String>> rootUrlsPermissions;
    private final int crawlerMaxDepth;

    public CrawlingStrategy(int crawlerMaxDepth) {
        this(crawlerMaxDepth, Collections.synchronizedList(new ArrayList<URL>()), Collections.synchronizedMap(new HashMap<String, Collection<String>>()));
    }

    public CrawlingStrategy(int crawlerMaxDepth, List<URL> visitedUrls, Map<String, Collection<String>> rootUrlsPermissions) {
        this.visitedUrls = visitedUrls;
        this.rootUrlsPermissions = rootUrlsPermissions;
        this.crawlerMaxDepth = crawlerMaxDepth;
    }
    
    public boolean isCrawable(URL url, int depth) {
        if (url != null && !visitedUrls.contains(url) && isCrawlingAllowed(url) && depth < crawlerMaxDepth) {
            visitedUrls.add(url);
            if(url.getPath().endsWith("/")){
                try {
                    visitedUrls.add(new URL(url.toString().substring(0, url.toString().length() -1)));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CrawlingStrategy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
        return false;
    }

    private boolean isCrawlingAllowed(URL url) {
        boolean canCrawl = true;
        String rootUrl = url.getProtocol() + "://" + url.getAuthority();
        if (rootUrl.endsWith("/")) {
            rootUrl = rootUrl.substring(rootUrl.lastIndexOf("/"));
        }
        Collection<String> sitePermissions = rootUrlsPermissions.get(rootUrl);
        if (sitePermissions == null) {
            sitePermissions = getRobotExclusion(rootUrl);
            rootUrlsPermissions.put(rootUrl, sitePermissions);
        }
        for (String restriction : sitePermissions) {
            if (url.toString().contains(restriction)) {
                canCrawl = false;
                break;
            }
        }
        return canCrawl;
    }

    private Collection<String> getRobotExclusion(String url) {
        String robotFileContent = ClientBuilder.newClient().target(url + "/robots.txt").request(MediaType.TEXT_HTML).get(String.class);
        LOGGER.log(Level.FINER, "[Thread-{0}]robots.txt : {1}", new Object[]{Thread.currentThread().getId(), robotFileContent});
        Collection<String> pathExclusions = new ArrayList<>();
        if (robotFileContent != null && robotFileContent.length() > 0) {
            String[] allPermissions = USER_AGENT_PATTERN.split(robotFileContent);
            String applicablePermissions = "";
            for (String permission : allPermissions) {
                if (permission.trim().startsWith("*")) {
                    applicablePermissions += permission.trim().substring(1);
                }
            }
            String[] allApplicableExclusions = DISALLOW_PATTERN.split(applicablePermissions);
            LOGGER.log(Level.FINE, "[Thread-{0}]allApplicableExclusions : {1}", new Object[]{Thread.currentThread().getId(), Arrays.toString(allApplicableExclusions)});
            for (String restriction : allApplicableExclusions) {
                if (null != restriction && restriction.trim().length() > 0) {
                    pathExclusions.add(restriction.trim());
                }
            }
        }
        return pathExclusions;
    }

}
