package com.toks.laba1.packaging;

import java.util.Arrays;

import static com.toks.laba1.packaging.PackageConstants.*;

public class PackageService {
    public Package byteStaff(Package packageData) {
        byte[] staffedData = new byte[PACKAGE_SIZE * 2];
        Arrays.fill(staffedData, EMPTY_BYTE);
        byte[] data = packageData.getData();
        System.arraycopy(data, 0, staffedData, 0, FLAG_OFFSET+TO_OFFSET+FROM_OFFSET);
        int staffedDataOffset = FLAG_OFFSET+TO_OFFSET+FROM_OFFSET;
        for (byte i = 0; i < DATA_SIZE; i++, staffedDataOffset++) {
            if (data[DATA_OFFSET + i] == EMPTY_BYTE) break;
            if (data[DATA_OFFSET + i] == FLAG_BYTE) {
                doStuff(FLAG_CONFIRMATION, staffedDataOffset++, staffedData);
            } else if (data[DATA_OFFSET + i] == ESCAPE_BYTE) {
                doStuff(ESCAPE_CONFIRMATION, staffedDataOffset++, staffedData);
            } else {
                staffedData[staffedDataOffset] = data[DATA_OFFSET + i];
            }
        }
        staffedData[(FCS_OFFSET*2)+1] = data[FCS_OFFSET];
        return new Package(staffedData);
    }

    private void doStuff(Byte confirmation, int staffedDataOffset, byte[] staffedData) {
        staffedData[staffedDataOffset] = ESCAPE_BYTE;
        staffedData[++staffedDataOffset] = confirmation;
    }

    public Package byteDestaff(Package packageData) {
        byte[] destaffedData = new byte[PACKAGE_SIZE];
        byte[] data = packageData.getData();
        Arrays.fill(destaffedData, EMPTY_BYTE);
        System.arraycopy(data, 0, destaffedData, 0, FLAG_OFFSET+TO_OFFSET+FROM_OFFSET);
        int destaffedDataOffset = FLAG_OFFSET+TO_OFFSET+FROM_OFFSET;
        for (int i = 0; i < DATA_SIZE; i++, destaffedDataOffset++) {
            if (data[DATA_OFFSET + i] == EMPTY_BYTE) break;
            if (i + 1 == DATA_SIZE || data[DATA_OFFSET + i] != ESCAPE_BYTE) {
                destaffedData[destaffedDataOffset] = data[DATA_OFFSET + i];
            } else {
                byte symbol = (data[DATA_OFFSET + i + 1] == ESCAPE_CONFIRMATION) ? ESCAPE_BYTE :FLAG_BYTE;
                destaffedData[destaffedDataOffset] = symbol;
                i++;
            }
        }
        destaffedData[FCS_OFFSET] = data[(FCS_OFFSET*2)+1];
        return new Package(destaffedData);
    }
}
