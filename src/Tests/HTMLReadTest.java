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
    public void testSkipSpace() {

    }

    @Test
    public void testReadString() {

    }
}