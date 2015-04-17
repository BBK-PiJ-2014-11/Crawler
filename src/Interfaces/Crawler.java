package Interfaces;

import java.net.URL;
import java.net.MalformedURLException;

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
    void crawl(URL url, String file) throws MalformedURLException;

    /**
     * Creates a String from a url
     *
     * @param url the web page
     * @return the url in String format
     */
    String parseURL (URL url); //nb possibly move to htmlread class?

    /**
     * Establishes the homepath of the current webpage
     *
     * @param path the path of the link being analysed
     * @return the the home path of the initial page
     */
    String homePath (String path);

    /**
     * Creates an absoulte path for link if its path is relative to home
     *
     * @param url the current url being visited
     * @param link the path of the link being analysed
     * @param homePath the homePath of the site
     * @return the absolute path of the analysed link
     */
    String absoulutePath(String url, String link, String homePath);

    /**
     * Checks if an input contains a specific sequence of characters
     *
     * @param searchStr String to be checked for
     * @return true if the search String is preset, false if not
     */
    Boolean searchString (String searchStr);

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
