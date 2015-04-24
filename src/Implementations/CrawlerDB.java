package Implementations;

import Interfaces.DB;

import java.sql.*;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class CrawlerDB implements DB {

    public static final String JDBC_URL = "jdbc:derby:memory:testdb;create=true";
    private Statement statement;
    private Connection connection = DriverManager.getConnection(JDBC_URL);

    /**
     * CrawlerDB class constructor
     *
     * Creates a new database with two (empty table) - one for temp (to be crawled)links
     * and one for final (crawled links)
     */
    public CrawlerDB(Connection connection) throws SQLException {

        this.connection = connection;
        //statement.execute("drop table tempTable");
        //statement.execute("drop table resultsTable");
        connection.createStatement().execute("create table tempTable" + "(priority int, url varchar(2000) not null) ");
        connection.createStatement().execute("create table resultsTable" + "(priority int, url varchar(2000) not null)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeString(int priority, String url) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("insert into"+" tempTable"+ " values (" + priority + ", '" + url + "')");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkLinks(String url) throws SQLException {
        ResultSet result;
        statement = connection.createStatement();
        result = statement.executeQuery("select * from tempTable where" + " url =' " + url + " ' ");
        return result.next();
    }
}
