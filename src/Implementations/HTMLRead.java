package Implementations;

import Interfaces.Reader;

import java.io.IOException;
import java.io.InputStream;

public class HTMLRead implements Reader {

    /**
     * HTMLRead class constructor
     *
     * Creates a new instance of the html parser
     */
    public HTMLRead(){

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readUntil(InputStream is, char ch1, char ch2) {
        int x;
        try {
            while ((x = is.read()) != -1) {
                if (Character.toLowerCase((char) x) == Character.toLowerCase(ch1)){
                    return true;
                }else if(Character.toLowerCase((char) x) == Character.toLowerCase(ch2)) {
                   return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * {@inheritDoc}
     *
     * N.B. characters will NOT be case sensitive
     */
    @Override
    public char skipSpace(InputStream is, char ch) {
        int x;
        try {
            while ((x = is.read()) != -1) {
                if (Character.toLowerCase((char) x) == Character.toLowerCase(ch)){
                    return Character.MIN_VALUE;
                }else if (!Character.isWhitespace(x)){
                    return (char) x;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Character.MIN_VALUE;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String readString(InputStream is, char ch1, char ch2) {
        int x;
        String str = "";
        try {
            while((x = is.read()) != -1){
                if ((char) x == ch1) {
                    str += (char) x;
                    return str;
                }
                else if((char) x == ch2)
                    return null;
                else
                    str += (char) x;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
