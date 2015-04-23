package Tests;

import Implementations.CrawlerDB;
import Interfaces.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class CrawlerDBTest {

    private Connection connection;
    private DB database;
    private Statement statement;

    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String db_url = "jdbc:derby:memory:testdb;create=true";
    public static String tableName = "newTable";

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
    public void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    public void testWriteString() throws SQLException  {
        //results to be returned
        ResultSet result;

        //Strings to be written
        database.writeString(1, homepage);
        database.writeString(2, portfolio);
        database.writeString(3, contact);

        //check the number of new records
        result = statement.executeQuery("SELECT COUNT(*) FROM newTable");
        int rows = result.getInt("rows");

        assertEquals(3, rows);
    }

    @Test
    public void testCheckLinks() throws Exception {

    }
}