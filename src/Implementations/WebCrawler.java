package Implementations;

import Interfaces.Crawler;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WebCrawler implements Crawler {

    private HTMLRead reader;
    private String filename;
    private File database;
    private int maxBreath;
    private int maxDepth;
    private int priorityNo;

    public WebCrawler(){
        reader = new HTMLRead();
        filename = "";
        database = new File(filename);
        maxBreath = 11; //temp value
        maxDepth = 11; // temp value
        priorityNo = 0;
    }

    @Override
    public void crawl(URL url, String file) throws MalformedURLException {

    }

    @Override
    public List<URL> getLinks(InputStream is) {
        return null;
    }
    

    @Override
    public String getHomePath(String path) {
        return null;
    }

    @Override
    public String setAbsolutePath(String url, String link, String homePath) {
        return null;
    }

    @Override
    public Boolean checkString(InputStream is, String checkStr) {
        return null;
    }

    @Override
    public boolean search(String url) {
        return true;
    }
}
