package Tests;

import Implementations.WebCrawler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    private WebCrawler crawler;
    private URL linksPage; //local html file to be tested
    private URL webPage;  //web-page to be tested via http connection
    private URL secondPage; //link to be retrieved
    private URL thirdPage; //link to be retrieved
    private URL fourthPage; //link to be retrieved
    private String filename; //name of database
    private File database;

    /**
     * Test crawler setup
     */
    @Before
    public void setUp() throws  MalformedURLException {
        crawler = new WebCrawler();
        linksPage = new URL("file:TestData/linksPage.html");
        webPage = new URL ("http://ehshan.com/");
        secondPage = new URL ("http://ehshan.com/portfolio");
        thirdPage = new URL ("http://ehshan.com/contact");
        fourthPage = new URL ("http://ehshan.com/gallery");
        database = new File("filename");
    }

    /**
     * Closing crawler
     */
    @After
    public void tearDown()  {
        crawler = null;
    }

    /**
     * Test to check if local html file exists and can be referenced via url
     *
     * Test should return the file content type
     */
    @Test
    public void testFileURLExists() throws IOException {
        URLConnection con = linksPage.openConnection();
        assertEquals("text/html", con.getContentType() );
    }

    /**
     * Test to check if web-page exists and can be accessed via http
     * Checking for http 200 status code (request successful)
     *
     * Test should return true
     */
    @Test
    public void testURLExists() throws IOException {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection con = (HttpURLConnection) webPage.openConnection();
        con.setRequestMethod("HEAD");
        assertTrue (con.getResponseCode() == HttpURLConnection.HTTP_OK);

    }

    @Test
    public void testCrawl()  {

    }

    /**
     * Test to check if html file can be successfully scraped for links
     *
     * Test should return a list contain all links found, as Strings
     */
    @Test
    public void testGetLinks() throws IOException {
        InputStream is = linksPage.openStream();
        List urlList = crawler.getLinks(is);

        //converting URL to Strings
        String home = webPage.toString();
        String portfolio = secondPage.toString();
        String contact = thirdPage.toString();
        String gallery = fourthPage.toString();

        //check list contain all links
        assertEquals(home,urlList.get(0));
        assertEquals(portfolio ,urlList.get(1));
        assertEquals(contact ,urlList.get(2));
        assertEquals(gallery ,urlList.get(3));
    }

    /**
     * Test to check if web-page can be successfully scraped for links
     *
     * Test should return a list contain all links found, as Strings
     */
    @Test
    public void testGetLinksWithHttp() throws IOException {
        InputStream is = webPage.openStream();
        List urlList = crawler.getLinks(is);

        //converting URL to Strings (urls form navigation bar)
        String home = webPage.toString();
        String portfolio = secondPage.toString();
        String contact = thirdPage.toString();
        String gallery = fourthPage.toString();

        //check list contain all links
        assertEquals(home,urlList.get(0));
        assertEquals(portfolio ,urlList.get(1));
        assertEquals(contact ,urlList.get(2));
        assertEquals(gallery ,urlList.get(3));
    }

    /**
     * Test to check if static web-page can be successfully scraped for links
     *
     * Test should return a list contain all links found, as Strings
     */
    @Test
    public void testGetLinksWithHttpStaticPage() throws IOException {
        InputStream is = new URL ("http://ehshan.com/crawler").openStream();
        List urlList = crawler.getLinks(is);

        //converting URL to Strings (urls form navigation bar)
        String home = "http://ehshan.com";
        String portfolio = secondPage.toString();
        String contact = thirdPage.toString();
        String gallery = fourthPage.toString();

        //check list contain all links
        assertEquals(home,urlList.get(0));
        assertEquals(portfolio ,urlList.get(1));
        assertEquals(contact ,urlList.get(2));
        assertEquals(gallery ,urlList.get(3));
    }

    /**
     * Test to check whether relative link can be successfully captured
     * with full address
     *
     * Test should return a list contain all links found, as Strings
     */
    @Test
    public void testGetRelativeLink() throws IOException {
        InputStream is = new URL ("http://ehshan.com/crawler").openStream();
        List urlList = crawler.getLinks(is);

        //this link is present on searched page as <a href="books.php">
        String relative = "http://ehshan.com/books.php";

        //check that desired link is present in returned list
        assertTrue(linkFound(urlList, relative ));
    }

    /**
     * Test to check whether the homepage of a visited page can be
     * successfully returned
     *
     * Test should return the homepage of the site being visited
     */
    @Test
    public void testGetHomePath()  {
        //page will be -> http://ehshan.com/contact
        String contactPage = thirdPage.toString();
        String homePath = crawler.getHomePath(contactPage);

        //expect result will be -> http://ehshan.com/
        assertEquals(homePath, webPage.toString());
    }

    @Test
    public void testSetAbsolutePath()  {

    }

    /**
     * Test to check if continuous sequence of chars can be successfully read from string
     * (ignoring whitespace)
     *
     * Test should return true
     */
    @Test
    public void testCheckString() throws IOException {
        String testStr = "<a   href=\"http://ehshan.com/\">Link 1</a>";
        InputStream is = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
        assertTrue(crawler.checkString(is, "<ahref"));
    }

    /**
     * Test to confirm that method will only work is chars are continuous from start of InputStream
     *
     * Test should return false
     */
    @Test
    public void testCheckStringFromMiddle() throws IOException {
        String testStr = "<a   href=\"http://ehshan.com/\">Link 1</a>";
        InputStream is = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));
        assertFalse(crawler.checkString(is, "http://"));
    }

    @Test
    public void testSearch()  {

    }

    /**
     * Additional method to check whether a list contains a url
     * (useful for pages with large amounts of links)
     *
     * @param urlList the list to be searched
     * @param link the linkk to be searched for
     * @return true if link found found, false if not
     */
    private boolean linkFound(List<String> urlList, String link) {
        boolean found = false;
        for (String url : urlList) {
            if (url.equals(link)) {
                found = true;
            }
        }
        return found;
    }
}