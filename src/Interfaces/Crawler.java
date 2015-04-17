package Interfaces;

import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

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
     * Creates a list of links scraped from a web-page
     *
     * @param is the input stream to be read
     * @return the list of scraped urls
     */
    List<URL> getLinks (InputStream is);

    /**
     * Creates a String from a url
     *
     * @param url the web page
     * @return the url in String format
     */
    String parseURL (URL url); //nb possibly move to htmlread class?

    /**
     * Establishes the homepath of the current web-page
     *
     * @param path the path of the link being analysed
     * @return the the home path of the initial page
     */
    String getHomePath (String path);

    /**
     * Creates an absolute path for link if its path is relative to home
     *
     * @param url the current url being visited
     * @param link the path of the link being analysed
     * @param homePath the homePath of the site
     * @return the absolute path of the analysed link
     */
    String setAbsolutePath (String url, String link, String homePath);

    /**
     * Checks if an input contains a specific sequence of characters
     *
     * @param checkStr String to be checked for
     * @return true if the search String is preset, false if not
     */
    Boolean checkString (String checkStr);

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
