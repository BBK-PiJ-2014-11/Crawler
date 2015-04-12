package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HTMLReadTest {

    private HTMLRead reader;
    private InputStream is;
    private final File file = new File("htmlTest.html");

    @Before
    public void setUp() throws FileNotFoundException {
        reader = new HTMLRead();
        is = new FileInputStream(file);
    }

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

    @Test
    public void testReadUntilFirstChar() {
        char ch1 = 'l';
        char ch2 = ')';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilSecondChar() {
        char ch1 = ')';
        char ch2 = 'l';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilFirstCharCapitalised() {
        char ch1 = 'L';
        char ch2 = 'A';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilSecondCharCapitalised() {
        char ch1 = 'A';
        char ch2 = 'L';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilFirstCharTag() {
        char ch1 = '<';
        char ch2 = '>';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilSecondCharTag() {
        char ch1 = '>';
        char ch2 = '<';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilFirstCharNumber() {
        char ch1 = '8';
        char ch2 = '0';
        assertTrue(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testReadUntilSecondCharNumber() {
        char ch1 = '0';
        char ch2 = '8';
        assertFalse(reader.readUntil(is,ch1,ch2));
    }

    @Test
    public void testSkipSpace() {

    }

    @Test
    public void testReadString() {

    }
}