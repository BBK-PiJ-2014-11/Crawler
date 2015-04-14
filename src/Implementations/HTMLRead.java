package Implementations;

import Interfaces.Reader;

import java.io.IOException;
import java.io.InputStream;

public class HTMLRead implements Reader {

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

    @Override
    public char skipSpace(InputStream is, char ch) {
        return 0;
    }

    @Override
    public String readString(InputStream is, char ch1, char ch2) {
        return null;
    }
}
