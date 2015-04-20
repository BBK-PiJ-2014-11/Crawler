package Implementations;

import Interfaces.Crawler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
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
    public List<URL> getLinks(InputStream is) throws IOException {
        List urlList = new LinkedList<>();
        String url;
        while ((is.read() != -1)) {
            if(reader.readUntil(is,'<', (char) -1)){
                if (checkString(is, "ahref")) {
                    if (reader.readUntil(is, '=', '>')) {
                        if ((reader.skipSpace(is, '"') == Character.MIN_VALUE) ) {
                            String temp = reader.readString(is,'"','>');
                            url = temp.substring(0, temp.length() - 1);
                        }else{
                            url = reader.readString(is,'>', (char) -1);
                        }
                        //remove link referring to same page
                        System.out.println(url);
                        if(url.equals("#")){
                            System.out.println("Link same page");
                        }else{
                            urlList.add(url);
                        }
                    }
                }
            }
        }
        return urlList;
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
    public Boolean checkString(InputStream is,String checkStr) {
        for (char ch: checkStr.toCharArray()) {
            if(reader.skipSpace(is,ch)!= Character.MIN_VALUE){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean search(String url) {
        return true;
    }
}
