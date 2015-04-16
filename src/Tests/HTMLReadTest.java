package Tests;

import Implementations.HTMLRead;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HTMLReadTest {

    /**
     * Test HTMLRead constructor
     */
    private HTMLRead reader;
    private InputStream is;
    private final File file = new File("TestData/htmlTest.html");

    @Before
    public void setUp() throws FileNotFoundException {
        reader = new HTMLRead();
        is = new FileInputStream(file);
    }

    /**
     * Removing HTMLRead and closing InputStream
     */
    @After
    public void tearDown(){
        try {
            if(is != null){
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = null;
    }
    /*
    *
    * TEST BATCH FOR readUntil
    *
    */

    /**
     * Test with first passed character occurring first
     *
     * Test should return true
     */
    @Test
    public void testReadUntilFirstChar() {
        char ch1 = 'l';
        char ch2 = ')';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with second passed character occurring first
     *
     * Test should return false
     */
    @Test
    public void testReadUntilSecondChar() {
        char ch1 = ')';
        char ch2 = 'l';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with first passed character occurring first
     * Character Capitalised in this instance
     *
     * Test should return true
     */
    @Test
    public void testReadUntilFirstCharCapitalised() {
        char ch1 = 'L';
        char ch2 = 'A';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with second passed character occurring first
     * Character Capitalised in this instance
     *
     * Test should return false
     */
    @Test
    public void testReadUntilSecondCharCapitalised() {
        char ch1 = 'A';
        char ch2 = 'L';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with first passed character occurring first
     * Checking html tags
     *
     * Test should return true
     */
    @Test
    public void testReadUntilFirstCharTag() {
        char ch1 = '<';
        char ch2 = '>';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with second passed character occurring first
     * Checking html tags
     *
     * Test should return false
     */
    @Test
    public void testReadUntilSecondCharTag() {
        char ch1 = '>';
        char ch2 = '<';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with first passed character occurring first
     * Checking numbers
     *
     * Test should return true
     */
    @Test
    public void testReadUntilFirstCharNumber() {
        char ch1 = '8';
        char ch2 = '0';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    /**
     * Test with second passed character occurring first
     * Checking numbers
     *
     * Test should return false
     */
    @Test
    public void testReadUntilSecondCharNumber() {
        char ch1 = '0';
        char ch2 = '8';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }
    /*
    *
    * TEST BATCH FOR skipSpace
    *
    */

    /**
     * Test with ch as first non-whitespace character
     *
     * Test should return char minValue
     */
    @Test
    public void testSkipSpaceCharFound() {
        char ch = '<';
        char minValue = Character.MIN_VALUE;
        assertEquals(minValue, reader.skipSpace(is, ch));
    }

    /**
     * Test with ch not first non-whitespace character
     *
     * Test should return char firstCharacter
     */
    @Test
    public void testSkipSpaceCharNotFound() {
        char ch = 'a';
        char firstCharacter = '<';
        assertEquals(firstCharacter, reader.skipSpace(is, ch));
    }

    /**
     * Test with ch as a whitespace character
     *
     * Test should return char firstCharacter
     */
    @Test
    public void testSkipSpaceWithWhiteSpace() {
        char ch = ' ';
        char firstCharacter = '<';
        assertEquals(firstCharacter, reader.skipSpace(is, ch));
    }

    /**
     * Test to check multiple calls of skip space sequentially
     *
     * Test should return as expected...
     */
    @Test
    public void testSkipSpaceMultiple() {
        //passing the first non-whitespace character
        char firstCharacter = '<';
        char minValue = Character.MIN_VALUE;
        assertEquals(minValue, reader.skipSpace(is, firstCharacter));

        //passing an arbitrary character
        char arbitraryCharacter = 'a';
        char nextCharacter = '!';
        assertEquals(nextCharacter, reader.skipSpace(is, arbitraryCharacter));

        //passing the next non-whitespace character
        char thirdCharacter = 'D';
        assertEquals(minValue, reader.skipSpace(is, thirdCharacter ));
    }

    /**
     * Test with ch as a number character
     *
     * Test should return return as expected...
     */
    @Test
    public void testSkipSpaceNumbers() throws FileNotFoundException {
        //new input file
        InputStream newStream = new FileInputStream("TestData/skipSpaceTest.html");

        char minValue = Character.MIN_VALUE;

        //skipping comment deceleration
        reader.skipSpace(newStream, '<');
        reader.skipSpace(newStream, '!');
        reader.skipSpace(newStream, '-');
        reader.skipSpace(newStream, '-');

        //passing next number
        char firstNo = '1';
        assertEquals(minValue, reader.skipSpace(newStream, firstNo));

        //passing next number
        char nextNo = '0';
        assertEquals(nextNo , reader.skipSpace(newStream, firstNo));
    }
    /*
    *
    * TEST BATCH FOR readString
    *
    */

    /**
     * Test with ch1 occurring first
     *
     * Test should return the String declaration
     */
    @Test
    public void testReadStringFirstChar() {
        char ch1 = 'l';
        char ch2 = 'a';
        String declaration = "<!DOCTYPE html";
        assertEquals(declaration, reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch2 occurring first
     *
     * Test should return null
     */
    @Test
    public void testReadStringSecondChar() {
        char ch1 = 'a';
        char ch2 = 'l';
        assertNull(reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch1 occurring first
     * Character Capitalised in this instance
     *
     * Test should return the String declaration
     */
    @Test
    public void testReadStringFirstCharCapitalised() {
        char ch1 = 'T';
        char ch2 = 'A';
        String declaration = "<!DOCT";
        assertEquals(declaration, reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch2 occurring first
     * Character Capitalised in this instance which occure before lower case
     * version in test file
     *
     * Test should return null
     */
    @Test
    public void testReadStringSecondCharCapitalised() {
        char ch1 = 't';
        char ch2 = 'T';
        assertNull(reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch1 occurring first
     * Checking html tags
     *
     * Test should return the String tag
     */
    @Test
    public void testReadStringFirstCharTag() {
        char ch1 = '<';
        char ch2 = '>';
        String tag = "<";
        assertEquals(tag, reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch2 occurring first
     * Checking html tags
     *
     * Test should return null
     */
    @Test
    public void testReadStringSecondCharTag() {
        char ch1 = '>';
        char ch2 = '<';
        assertNull(reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch1 occurring first
     * Checking numbers
     *
     * Test should return the String str
     */
    @Test
    public void testReadStringFirstCharNumber() {
        char ch1 = '8';
        char ch2 = '0';
        String str = "<!DOCTYPE html>\n" + "<html>\n" + "<head lang=\"en\">\n" + "<meta charset=\"UTF-8";
        assertEquals(str, reader.readString(is, ch1, ch2));
    }

    /**
     * Test with ch2 occurring first
     * Checking numbers
     *
     * Test should return null
     */
    @Test
    public void testReadStringSecondCharNumber() {
        char ch1 = '0';
        char ch2 = '8';
        assertNull(reader.readString(is, ch1, ch2));
    }
}