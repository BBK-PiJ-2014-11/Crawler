package Tests;

import Implementations.CrawlerDB;
import Interfaces.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class CrawlerDBTest {

    private Connection connection;
    private DB database;
    private Statement statement;

    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String db_url = "jdbc:derby:memory:testdb;create=true";
    public static String tableName = "testTable";

    private String homepage;
    private String portfolio;
    private String contact;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        database = new CrawlerDB(connection);

        homepage = "http://ehshan.com/";
        portfolio = "http://ehshan.com/portfolio/";
        contact = "http://ehshan.com/contact/";

        Class.forName(driver);
        connection = DriverManager.getConnection(db_url);
        connection.createStatement().execute("create table " + tableName + "(priority int, url varchar(2000))");
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testWriteString() throws Exception {

    }

    @Test
    public void testCheckLinks() throws Exception {

    }
}