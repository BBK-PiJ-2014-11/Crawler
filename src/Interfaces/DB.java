package Interfaces;

import java.sql.SQLException;

/**
 * A class to create a database to store all unique crawled links
 *
 * @author Ehshan Veerabangsa
 */
public interface DB {

    /**
     * Writes the url (in String format) and its priority number to a table
     *
     * @param priority  the depth scraped
     * @param url the link to be stored
     * @param tableName the name of the table be written to
     */
    void writeString(int priority, String url, String tableName) throws SQLException;

    /**
     * Checks if the current link is already present in designated table
     *
     * @param url the link to be checked
     * @return true if link is already present table, false if not
     * @param tableName the name of the table be checked
    */
    boolean checkLinks (String url, String tableName) throws SQLException;
}