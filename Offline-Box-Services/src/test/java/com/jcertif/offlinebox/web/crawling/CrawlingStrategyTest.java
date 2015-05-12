package com.jcertif.offlinebox.web.crawling;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Test class for {@link CrawlingStrategy}</p>
 *
 * @author Martial SOMDA
 * @since 1.0
 */
public class CrawlingStrategyTest {

    private CrawlingStrategy strategy;

    @Before
    public void init() {
        List<URL> visitedUrls = new ArrayList<>();
        try {
            visitedUrls.add(new URL("http://jcertif.com/qui-sommes-nous/"));
            visitedUrls.add(new URL("http://jcertif.com/about/"));
            visitedUrls.add(new URL("http://jcertif.com/lab/teams/java-ee/"));
            visitedUrls.add(new URL("http://jcertif.com/lab/teams/members/martial-somda/"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlingStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Collection<String>> rootPermissions = new HashMap<>();
        Collection<String> permissions = new ArrayList<>();
        permissions.add("/search");
        permissions.add("/robot");
        permissions.add("/internal");
        rootPermissions.put("http://jcertif.com", permissions);
        strategy = new CrawlingStrategy(10, visitedUrls, rootPermissions);
    }

    @Test
    public void testIsCrawable() {
        try {
            URL url = new URL("http://jcertif.com/lab/2014/05/04/il-etait-une-fois-le-lab.html");
            assertTrue(strategy.isCrawable(url, 2));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlingStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Failed to instanciate the url tou test.");
        }
    }

    @Test
    public void testExceedDepthPagesNotCrawable() {
        try {
            URL url = new URL("http://jcertif.com/lab/2014/05/04/il-etait-une-fois-le-lab.html");
            assertFalse(strategy.isCrawable(url, 11));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlingStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Failed to instanciate the url tou test.");
        }
    }

    @Test
    public void testAlreadyVisitedPagesNotCrawable() {
        try {
            URL url = new URL("http://jcertif.com/qui-sommes-nous");
            assertTrue(strategy.isCrawable(url, 2));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlingStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Failed to instanciate the url tou test.");
        }
    }

    @Test
    public void testRobotExclusionsNotCrawable() {
        try {
            URL url = new URL("http://jcertif.com/search?q=test");
            assertFalse(strategy.isCrawable(url, 2));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlingStrategyTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Failed to instanciate the url tou test.");
        }
    }

}
