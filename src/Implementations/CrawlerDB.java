package Implementations;

import Interfaces.DB;

import java.sql.Connection;
import java.sql.SQLException;

public class CrawlerDB implements DB {


    public CrawlerDB(Connection connection) throws SQLException {

        connection .createStatement().execute("create table " + "tempTable" + "(priority int, url varchar(2000))");
        connection .createStatement().execute("create table " + "resultsTable" + "(priority int, url varchar(2000))");
    }

    @Override
    public void writeString(int priority, String url) {

    }

    @Override
    public boolean checkLinks(String url) {
        return false;
    }
}
