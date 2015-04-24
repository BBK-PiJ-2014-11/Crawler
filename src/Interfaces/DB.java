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
     */
    void writeString(int priority, String url) throws SQLException;

    /**
     * Checks if the current link is already present in designated table
     *
     * @param url the link to be checked
     * @return true if link is already present table, false if not
     */
    boolean checkLinks (String url) throws SQLException;
}