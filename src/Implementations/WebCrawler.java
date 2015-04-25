package Implementations;

import Interfaces.Crawler;
import Interfaces.DB;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class WebCrawler implements Crawler {

    private HTMLRead reader;
    private int maxBreath;
    private int maxDepth;
    private int priorityNo;
    private URL currentPage;
    private String currentHome; // to store the home page of last visited absolute link
    private String base; // picks up any base link found
    private List scrapedLinks;

    //database setup
    private static String dbName = "crawlerDB";
    private static String tempTable = "tempTable";
    private static String resultsTable = "resultsTable";
    public static String db_url = "jdbc:derby:memory:"+dbName+"+;create=true";
    private Connection connection = DriverManager.getConnection(db_url);
    /**
     * WebCrawler class constructor
     *
     * Creates a new instance of the web crawler
     */
    public WebCrawler() throws MalformedURLException, SQLException {
        reader = new HTMLRead();
        maxBreath = 111; //TEMP VALUE
        maxDepth = 11; //TEMP VALUE
        priorityNo = 0;
        currentPage =  new URL ("http://xyz.com/"); //INITIAL VALUE TO PREVENT NULL POINTER EXCEPTION
        scrapedLinks = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void crawl(URL url) throws IOException, SQLException {
        //initial page
        while (priorityNo < maxBreath){
            currentPage =  url;
            priorityNo++;

            //set up database
            DB database = new CrawlerDB(connection, tempTable, resultsTable);
            database.writeString(0, currentPage.toString(), tempTable);

            //scrape links from first page
            InputStream is = url.openStream();
            scrapedLinks = getLinks(is);
            is.close();

            //write results to temp table
            if(!scrapedLinks.isEmpty()){
                for(Object link : scrapedLinks){
                    String strLink = link.toString();
                    System.out.println(priorityNo+": "+strLink);
                    if(!database.checkLinks(strLink, tempTable)){
                        database.writeString(priorityNo, strLink, tempTable);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getLinks(InputStream is) throws IOException {
        List urlList = new LinkedList<>();
        String url;
        String element;
        while ((is.read() != -1)) {
            if(reader.readUntil(is,'<', (char) -1)){
                char c = reader.skipSpace(is, '/');
                if (c == 'a' || c == 'b'){
                    element= checkTag(c+reader.readString(is, '=', '>'));
                    if (element.equals("a href")) {
                        if ((reader.skipSpace(is, '"') == Character.MIN_VALUE)) {
                            currentHome = getDomain(is); // gets the domain (if any)
                            String temp = reader.readString(is, '"', (char) -1); //remove trailing "
                            url = currentHome + temp.substring(0, temp.length() - 1); //adds rest of the address to domain
                        } else {
                            url = reader.readString(is, '>', (char) -1);
                        }
                        System.out.println("Found link = " + url);
                        //remove link referring to same page
                        if (url.equals("#") || url.equals(currentPage.toString())) {
                            System.out.println("Link same page");
                        //remove Strings contain spanning >1 tags
                        } else if (url.contains("<") || url.contains("</")) {
                            System.out.println("non link found");
                        }else{
                            urlList.add(url);
                        }
                    }else if(element.equals("base")){
                        base = getBase(is);
                    }
                }
            }
        }
        return urlList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDomain (InputStream is) {
        if (checkString(is, "http")) {
            currentHome = "http";
            int count = 0;
            //looking for the first three /
            while(count <3){
                String tempHome = reader.readString(is,'/', '"');
                //will only return links with a trailing /
                if(tempHome !=null){
                    currentHome += tempHome;
                }else if (base != null){
                    currentHome = base;
                }
                count++;
            }
        }
        System.out.println("homepage = "+currentHome);
        return currentHome;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean checkString(InputStream is,String checkStr) {
        for (char ch: checkStr.toCharArray()) {
            if(reader.skipSpace(is,ch)!= Character.MIN_VALUE){
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean search(String url) {
        return true;
    }

    /**
     * Checks if a html tag starting with 'a' or 'b' contains a
     * link attribute
     *
     * @param str the String within the tag to be analysed
     * @return the Strings  "a href" or "base" if either found as sub-String
     * or an empty String if not
     */
    private String checkTag (String str) {
        str = str.toLowerCase();
        if(str.substring(0, 1).equals("a")){
            if (str.contains("href")){
                return "a href";
            }
        } else if (str.substring(0, 4).equals("base")){
            return "base";
        }
        return "";
    }

    /**
     * Establishes the base link of a page (if any)
     *
     * @param is the input stream to be read
     * @return the base link found as a String
     */
    private String getBase(InputStream is) {
        String tempBase = "";
        if(reader.skipSpace(is,'"')== Character.MIN_VALUE){
            String temp = reader.readString(is, '"', (char) -1);
            tempBase = tempBase + (temp.substring(0, temp.length() - 1))+"/";
        }
        System.out.println("base = "+tempBase);
        return tempBase;
    }
}
