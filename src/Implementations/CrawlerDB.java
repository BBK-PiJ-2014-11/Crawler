package Implementations;

import Interfaces.DB;

public class CrawlerDB implements DB {
    @Override
    public void writeString(int priority, String url) {

    }

    @Override
    public boolean checkLinks(String url) {
        return false;
    }
}
