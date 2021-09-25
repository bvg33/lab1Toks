package com.toks.laba1.packaging;

import java.util.Arrays;

import static com.toks.laba1.packaging.PackageConstants.*;

public class Package {
    private byte[] data = new byte[PACKAGE_SIZE];

    public Package(byte[] message,String from,String to) {
        Arrays.fill(data, EMPTY_BYTE);
        System.arraycopy(message, 0, this.data, DATA_OFFSET, message.length);
        data[FLAG_OFFSET] = FLAG_BYTE;
        data[FROM_OFFSET] = new Byte(from);
        data[TO_OFFSET] = new Byte(to);
        data[FCS_OFFSET] = calculateFCS();
    }

    public Package(byte[] data){
        this.data = data;
    }

    private byte calculateFCS() {
        byte fcs = data[DATA_OFFSET];
        for (int i = DATA_OFFSET + 1; i < FCS_OFFSET; i++) {
            fcs ^= data[i];
        }
        return fcs;
    }

    public boolean isFCSValid() {
        return data[FCS_OFFSET] == calculateFCS();
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getMessage(){
        return Arrays.copyOfRange(data,DATA_OFFSET,DATA_OFFSET+DATA_SIZE);
    }
}
