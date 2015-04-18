package Implementations;

import Interfaces.Crawler;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WebCrawler implements Crawler {

    @Override
    public void crawl(URL url, String file) throws MalformedURLException {

    }

    @Override
    public List<URL> getLinks(InputStream is) {
        return null;
    }

    @Override
    public String parseURL(URL url) {
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
    public Boolean checkString(String checkStr) {
        return null;
    }

    @Override
    public boolean search(String url) {
        return true;
    }
}
