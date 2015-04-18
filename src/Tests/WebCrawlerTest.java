package Tests;

import Implementations.WebCrawler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    private WebCrawler crawler;
    private URL linksPage; //local html file to be tested
    private URL webPage;  //web-page to be tested via http connection
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