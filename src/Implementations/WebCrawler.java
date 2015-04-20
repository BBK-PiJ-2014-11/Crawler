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
    private URL currentPage;

    public WebCrawler() {
        reader = new HTMLRead();
        filename = "";
        database = new File(filename);
        maxBreath = 11; //TEMP VALUE
        maxDepth = 11; //TEMP VALUE
        priorityNo = 0;
    }

    @Override
    public void crawl(URL url, String file) throws IOException {
        currentPage =  new URL ("http://tempUrl.com");
    }

    @Override
    public List<URL> getLinks(InputStream is) throws IOException {
        List urlList = new LinkedList<>();
        String url;
        while ((is.read() != -1)) {
            if(reader.readUntil(is,'<', (char) -1)){
                if (checkString(is, "ahref")) {
                    if (reader.readUntil(is, '=', (char) -1)) {
                        if ((reader.skipSpace(is, '"') == Character.MIN_VALUE) ) {
                            String temp = reader.readString(is,'"',(char) -1);
                            url = temp.substring(0, temp.length() - 1);
                        }else{
                            url = reader.readString(is, '>', (char) -1);
                        }
                        System.out.println(url);
                        //remove link referring to same page
                        if(url.equals("#") || url.equals(currentPage.toString())){
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
        String home = "";
        if (path.contains("http")){
            String[] parts = path.split("\\.");
            home = parts[0];
            String[] parts2 = parts[1].split("/");
            home += "."+parts2[0]+"/";
            System.out.println("homepage = "+home);
        }
        return home;
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
