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
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
/**
 * @author Ehshan Veerabangsa
 */
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
    public void setUp() throws MalformedURLException, SQLException {
        crawler = new WebCrawler();
        linksPage = new URL("file:TestData/linksPage.html");
        webPage = new URL ("http://ehshan.com/");
        secondPage = new URL ("http://ehshan.com/portfolio/");
        thirdPage = new URL ("http://ehshan.com/contact/");
        fourthPage = new URL ("http://ehshan.com/gallery/");
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

    /**
     * Test to check if crawl can successfully store urls to database
     *
     * Test should return table values
     */
    @Test
    public void testCrawl() throws IOException, SQLException {
        URL url = new URL ("http://bbc.co.uk/");
        crawler.crawl(url);

        //check setup connection
        String dbName = "crawlerDB";
        String db_url = "jdbc:derby:memory:"+dbName+"+;create=true";
        Connection connection =  DriverManager.getConnection(db_url);

        //test for records with priority of 0
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(*) from  tempTable"+ " where priority = "+ 0);
        int record = result.getInt(0);
        assertEquals(1, record );
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
    public void testGetDomain()  {
        //page will be -> http://ehshan.com/contact
        String contactPage = thirdPage.toString();
        InputStream is = new ByteArrayInputStream(contactPage.getBytes(StandardCharsets.UTF_8));
        String homePage = crawler.getDomain(is);

        //expect result will be -> http://ehshan.com/
        assertEquals(homePage, webPage.toString());
    }

    /**
     * Test to check whether the homepage of a (long) absolute  link can be
     * successfully returned
     *
     * Test should return the homepage of the site being visited
     */
    @Test
    public void testGetDomainMockPage()  {
        String mockPage = "http://www.bbc.co.uk/sport/football/arsenal/fixtures";
        InputStream is = new ByteArrayInputStream(mockPage.getBytes(StandardCharsets.UTF_8));
        String mockHome = crawler.getDomain(is);

        assertEquals("http://www.bbc.co.uk/", mockHome);
    }

    /**
     * Test to check whether the homepage of a website can be returned
     *
     * Test should return the homepage of the site being visited
     */
    @Test
    public void testGetDomainHttp() throws IOException {
        InputStream is = new URL ("http://www.bbc.co.uk/sport/football/teams/arsenal").openStream();
        String mockHome = crawler.getDomain(is);

        assertEquals("http://www.bbc.co.uk/",mockHome);
    }

    /**
     * Test on  a relative link
     *
     * Test should return an empty String
     */
    @Test
    public void testGetDomainRelativeLink()  {
        String mockPage = "/gallery";
        InputStream is = new ByteArrayInputStream(mockPage.getBytes(StandardCharsets.UTF_8));
        String mockHome = crawler.getDomain(is);

        assertEquals(null, mockHome);
    }

    /**
     * Test to check if large web-page can be successfully scraped for links
     *
     * Test should return a list contain all links found, as Strings
     */
    @Test
    public void testGetLinksLargeSite() throws IOException {
        InputStream is = new URL ("http://bbc.co.uk/").openStream();
        List urlList = crawler.getLinks(is);

        //links to check for
        String news = "http://www.bbc.co.uk/news/";
        String sport = "http://www.bbc.co.uk/sport/";
        String iplayer = "http://www.bbc.co.uk/iplayer/";
        String weather = "http://www.bbc.co.uk/weather/";
        String tv = "http://www.bbc.co.uk/tv/";
        String radio = "http://www.bbc.co.uk/radio/";

        assertTrue(linkFound(urlList, news));
        assertTrue(linkFound(urlList, sport));
        assertTrue(linkFound(urlList, iplayer));
        assertTrue(linkFound(urlList, weather));
        assertTrue(linkFound(urlList, tv));
        assertTrue(linkFound(urlList, radio));
    }

    /**
     * Test to check if web-page with images links can be scraped
     *
     * Test should return a list contain all links found (mostly images),
     * as Strings
     */
    @Test
    public void testGetImages() throws IOException {
        InputStream is = new URL ("http://www.ehshan.com/gallery").openStream();
        List urlList = crawler.getLinks(is);

        //links to check for
        String photo1 = "http://ehshan.com/images/full/Berlin1.jpg";
        String photo2 = "http://ehshan.com/images/full/Berlin2.jpg";
        String photo3 = "http://ehshan.com/images/full/Berlin3.jpg";
        String photo4 = "http://ehshan.com/images/full/Berlin4.jpg";
        String photo5 = "http://ehshan.com/images/full/Berlin5.jpg";
        String photo6 = "http://ehshan.com/images/full/Berlin6.jpg";

        assertTrue(linkFound(urlList, photo1));
        assertTrue(linkFound(urlList, photo2));
        assertTrue(linkFound(urlList, photo3));
        assertTrue(linkFound(urlList, photo4));
        assertTrue(linkFound(urlList, photo5));
        assertTrue(linkFound(urlList, photo6));
    }

    /**
     * Test to check if the base link can be found and returned
     *
     * Test should return a list contain all links found (of which one should
     * be the base)
     */
    @Test
    public void testGetBase() throws IOException {
        InputStream is = new URL ("http://www.bbk.ac.uk/front-page/").openStream();
        List urlList = crawler.getLinks(is);

        //the base link
        String baseLink = "http://www.bbk.ac.uk/front-page/";

        assertTrue(linkFound(urlList, baseLink));
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