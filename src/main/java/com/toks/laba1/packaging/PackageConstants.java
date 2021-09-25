package com.toks.laba1.packaging;

public class PackageConstants {
    public static final byte FCS_OFFSET = 19;
    public static final byte DATA_OFFSET = 3;
    public static final byte ESCAPE_CONFIRMATION = 0x4B;
    public static final byte FLAG_CONFIRMATION = 0x4C;
    public static final byte ESCAPE_BYTE = 0x4A;
    public static final int DATA_SIZE = 16;
    public static final int PACKAGE_SIZE = 20;
    public static final byte FLAG_BYTE = 0x4D;
    public static final byte EMPTY_BYTE = 0x0;
    public static final byte FLAG_OFFSET = 0;
    public static final byte FROM_OFFSET = 1;
    public static final byte TO_OFFSET = 2;
}
