package Interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

/**
 * A class to scrape web-pages for links
 *
 * @author Ehshan Veerabangsa
 */
public interface Crawler {

    /**
     * Opens an HTTP connection to the starting URL and review the entire web page found there,
     * saving all URL links found within the page to a temporary database table
     *
     * @param url the web page to be scraped for links
     */
    void crawl(URL url) throws IOException, SQLException;

    /**
     * Creates a list of links scraped from a web-page
     *
     * @param is the input stream to be read
     * @return the list of scraped urls
     */
    List<String> getLinks (InputStream is) throws IOException;

    /**
     * Establishes the Domain of the current web-page
     *
     * @param is the input stream to be read (a link)
     * @return the the home page of the current page if path absolute, empty string if
     * link is relative
     */
    String getDomain (InputStream is);

    /**
     * Checks if an input contains a specific sequence of characters
     *
     * @param is the input stream to be read
     * @param checkStr String to be checked for
     * @return true if the search String is preset, false if not
     */
    Boolean checkString (InputStream is,String checkStr);

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
