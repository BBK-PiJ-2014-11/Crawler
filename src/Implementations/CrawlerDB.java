package Implementations;

import Interfaces.DB;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class CrawlerDB implements DB {

    public static String db_url = "jdbc:derby:memory:testdb;create=true";
    private Statement statement;
    private Connection connection = DriverManager.getConnection(db_url);
    private String tempTable;
    private String resultsTable;

    /**
     * CrawlerDB class constructor
     *
     * Creates a new database with two (empty table) - one for temp (to be crawled) links
     * and one for final (crawled links). Table names are passed as @params
     */
    public CrawlerDB(Connection connection, String tempTable, String resultsTable) throws SQLException {

        this.connection = connection;
        this.tempTable = tempTable;
        this.resultsTable = resultsTable;
        //statement.execute("drop table tempTable");
        //statement.execute("drop table resultsTable");
        connection.createStatement().execute("create table " +tempTable+ "(priority int, url varchar(2000) not null) ");
        connection.createStatement().execute("create table " +resultsTable+ "(priority int, url varchar(2000) not null)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeString(int priority, String url, String tableName) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("insert into "  +tableName+ " values (" + priority + ", '" + url + "')");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkLinks(String url, String tableName) throws SQLException {
        ResultSet results;
        statement = connection.createStatement();
        results = statement.executeQuery("select * from " +tableName+ " where" + " url =' " + url + " ' ");
        return results.next();
    }
}
