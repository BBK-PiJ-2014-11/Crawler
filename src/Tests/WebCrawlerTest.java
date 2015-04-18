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
        secondPage = new URL ("http://ehshan.com/");
        thirdPage = new URL ("http://ehshan.com/portfolio");
        fourthPage = new URL ("http://ehshan.com/contact/gallery");
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

    @Test
    public void testGetLinks()  {

    }

    @Test
    public void testParseURL()  {

    }

    @Test
    public void testGetHomePath()  {

    }

    @Test
    public void testSetAbsolutePath()  {

    }

    @Test
    public void testCheckString()  {

    }

    @Test
    public void testSearch()  {

    }

}