package Interfaces;

import java.net.URL;

/**
 * A class to scrape web-pages for links
 */
public interface Crawler {

    /**
     * Opens an HTTP connection to the starting URL and review the entire web page found there,
     * saving all URL links found within the page to a temporary database table
     *
     * @param url the web page to be scraped for links
     * @param file the name of the file for stored links
     */
    void crawl(URL url, String file);

    /**
     * Default search method
     *
     * @param url the web page to be scraped for links
     * @return true
     */
    default boolean search(String url) {
        return true;
    }
}
