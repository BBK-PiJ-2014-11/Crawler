package Tests;

import Implementations.CrawlerDB;
import Interfaces.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;
/**
 * @author Ehshan Veerabangsa
 */
public class CrawlerDBTest {

    private Connection connection;
    private DB database;
    private Statement statement;

    public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String db_url = "jdbc:derby:memory:testdb;create=true";
    public static String tableName = "tableName";

    private String homepage;
    private String portfolio;
    private String contact;

    /**
     * Testdb setup
     */
    @Before
    public void setUp() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        homepage = "http://ehshan.com/";
        portfolio = "http://ehshan.com/portfolio/";
        contact = "http://ehshan.com/contact/";

        Class.forName(driver).newInstance();
        connection = DriverManager.getConnection(db_url);
        System.out.println("connection created");
        connection.createStatement().execute("create table " + tableName + "(priority int, url varchar(2000))");
        database = new CrawlerDB(connection);
    }

    /**
     * Shut down db
     */
    @After
    public void tearDown() throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        if (tables.next()) {
            statement.execute("drop table "+ tableName);
        }
        connection.close();
//        statement.close();
    }

    /**
     * Test to check whether table can be created data can be written
     *
     * Test should return the number of record added (by counting
     * table rows)
     */
    @Test
    public void testWriteString() throws SQLException  {
        //results to be returned
        ResultSet result;

        //Strings to be written
        database.writeString(1, homepage);
        database.writeString(2, portfolio);
        database.writeString(3, contact);

        //check the number of new records
        result = statement.executeQuery("select COUNT(*) from "+ tableName);
        int rows = result.getInt("rows");

        assertEquals(3, rows);
    }

    /**
     * Test to check a link can be found in db
     *
     * Test should return true
     */
    @Test
    public void testCheckLinks() throws Exception {
        DB newDatabase = new CrawlerDB(connection);

        //Strings to be written
        newDatabase.writeString(1, homepage);
        newDatabase.writeString(2, portfolio);
        newDatabase.writeString(3, contact);

        //check that the links a present in table
        assertTrue( newDatabase.checkLinks(homepage));
        assertTrue( newDatabase.checkLinks(homepage));
        assertTrue( newDatabase.checkLinks(homepage));
    }
}