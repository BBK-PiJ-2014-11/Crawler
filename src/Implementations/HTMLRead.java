package Implementations;

import Interfaces.Reader;

import java.io.InputStream;

public class HTMLRead implements Reader {
    @Override
    public boolean readUntil(InputStream is, char ch1, char ch2) {
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
