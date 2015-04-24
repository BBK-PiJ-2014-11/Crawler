package Implementations;

import Interfaces.DB;

import java.sql.*;

public class CrawlerDB implements DB {

    public static final String JDBC_URL = "jdbc:derby:memory:testdb;create=true";
    private Statement statement;
    private Connection connection = DriverManager.getConnection(JDBC_URL);

    public CrawlerDB(Connection connection) throws SQLException {

        this.connection = connection;
        statement.execute("drop table tempTable");
        statement.execute("drop table resultsTable");
        connection.createStatement().execute("create table " + "tempTable" + "(priority int, url varchar(2000))");
        connection.createStatement().execute("create table " + "resultsTable" + "(priority int, url varchar(2000))");
    }

    @Override
    public void writeString(int priority, String url) {

    }

    @Override
    public boolean checkLinks(String url) {
        return false;
    }
}
