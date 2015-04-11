package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
        reader = null;
        is = null;
    }

    @Test
    public void testReadUntil() {

    }

    @Test
    public void testSkipSpace() {

    }

    @Test
    public void testReadString() {

    }
}