package Interfaces;

import java.io.InputStream;

/**
 * A class to parse data form input streams, and and constructing strings from information read.
 *
 * @author Ehshan Veerabangsa
 */
public interface Reader {

    /**
     * Reads characters from the InputStream and stops when either
     * a character that is the same as ch1 or ch2 is encountered, ignoring case
     *
     * @param is the input stream to be read
     * @param  ch1 the first character to check for
     * @param  ch2 the second character to check for
     * @return true if a character matches ch1, false is not.
     */
    boolean readUntil(InputStream is, char ch1, char ch2);

    /**
     * Reads up to and including the first non-whitespace character from the InputStream
     * or up to and including ch.
     *
     * @param is the input stream to be read
     * @param ch  the character to check for
     * @return the smallest possible value of a char if ch is encountered. Otherwise, it will return
     * the non-whitespace character that was read
     */
    char skipSpace(InputStream is, char ch);

    /**
     * Reads characters from the InputStream and stops when either a character that is the
     * same as ch1 or ch2 is encountered, not ignoring case.
     *
     * @param is the input stream to be read
     * @param  ch1 the first character to check for
     * @param  ch2 the second character to check for
     * @return a String containing all read character in order if ch1 is encountered,
     * or an String value null if ch2 is found
     */
    String readString(InputStream is, char ch1, char ch2);
}
