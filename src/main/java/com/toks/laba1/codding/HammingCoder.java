package com.toks.laba1.codding;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.toks.laba1.codding.HammingConstants.BLOCK_SIZE;
import static com.toks.laba1.codding.HammingConstants.BlOCK_WITH_CONTROL_BIT_SIZE;
import static com.toks.laba1.packaging.PackageConstants.DATA_OFFSET;
import static com.toks.laba1.packaging.PackageConstants.EMPTY_BYTE;

public class HammingCoder {

    public byte[] code(byte[] message) {
        List<StringBuilder> blocks = createBlocks(message, BLOCK_SIZE);
        List<Integer> controlBitsPositions = calculateControlBitsPosition();
        blocks.forEach(block -> controlBitsPositions.forEach(
                position -> {
                    if (block.length() >= position) {
                        block.insert(position - 1, 0);
                    }
                }));
        calculateControlBits(blocks, controlBitsPositions);
        String resultMessage = "";
        for (StringBuilder block : blocks) {
            resultMessage = resultMessage.concat(block.toString());
        }
        return resultMessage.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] decode(byte[] message) {
        String bits = new String(message, StandardCharsets.UTF_8);
        List<Integer> controlBitsPositions = calculateControlBitsPosition();
        List<StringBuilder> blocks = createBlocks(bits, BlOCK_WITH_CONTROL_BIT_SIZE);
        List<String> messages = new ArrayList<>();
        for (int val = 0; val < blocks.size(); val++) {
            List<Character> controlBitsValues = getControlBitsValue(blocks.get(val), controlBitsPositions);
            blocks.forEach(block -> controlBitsPositions.forEach(
                    position -> block.replace(position - 1, position, "0")));
            calculateControlBits(blocks, controlBitsPositions);
            char[] blockBits = blocks.get(val).toString().toCharArray();
            int counter = 0;
            int errorPosition = 0;
            for (int position : controlBitsPositions) {
                if (controlBitsValues.get(counter) != blockBits[position - 1]) {
                    errorPosition += position;
                }
                counter++;
            }
            if (errorPosition != 0) {
                errorPosition -= 1;
                char errorBit = blockBits[errorPosition];
                if (errorBit == '0') {
                    blockBits[errorPosition] = '1';
                } else {
                    blockBits[errorPosition] = '0';
                }
            }
            char[] blockWithoutControlBits = new char[BLOCK_SIZE];
            counter = 0;
            for (int i = 0, j = 0; i < blockBits.length; i++) {
                if (counter < controlBitsPositions.size() && i == controlBitsPositions.get(counter) - 1) {
                    counter++;
                    continue;
                } else {
                    blockWithoutControlBits[j++] = blockBits[i];
                }
            }
            messages.add(new String(blockWithoutControlBits));
        }
        String resultMessage = "";
        for (String mess : messages) {
            resultMessage = resultMessage.concat(mess);
        }
        return getBytesFromBitString(resultMessage);
    }

    private List<Character> getControlBitsValue(StringBuilder block, List<Integer> controlBitsPositions) {
        List<Character> controlBitsValues = new ArrayList<>();
        char[] bitsValues = block.toString().toCharArray();
        controlBitsPositions.forEach(position -> controlBitsValues.add(bitsValues[position - 1]));
        return controlBitsValues;
    }

    private void calculateControlBits(List<StringBuilder> blocks, List<Integer> controlBitsPositions) {
        blocks.forEach(block -> controlBitsPositions.forEach(
                position -> {
                    if (block.length() >= position - 1) {
                        block.replace(position - 1, position, Integer.toString(calculateControlBitsValue(block, position)));
                    }
                }));
    }

    private List<Integer> calculateControlBitsPosition() {
        List<Integer> positions = new ArrayList<>();
        for (int position = 1; position <= BLOCK_SIZE; position *= 2) {
            positions.add(position);
        }
        return positions;
    }

    private int calculateControlBitsValue(StringBuilder block, int position) {
        int value = 0;
        int defaultPositionValue = position;
        for (position -= 1; position < block.length(); position += defaultPositionValue) {
            int i = position;
            for (; i < block.length() && i < defaultPositionValue + position; i++) {
                char[] array = block.toString().toCharArray();
                if (array[i] == '1') {
                    value += 1;
                }
            }
            position = i;
        }
        return value % 2;
    }

    private List<StringBuilder> createBlocks(byte[] message, int blockSize) {
        String bits = getBitsFromBytes(message);
        return createBlocks(bits, blockSize);
    }

    private List<StringBuilder> createBlocks(String bits, int blockSize) {
        List<StringBuilder> blocks = new ArrayList<>();
        int position = 0;
        while (position < bits.length()) {
            if (position + blockSize - 1 > bits.length()) {
                String block = bits.substring(position);
                blocks.add(new StringBuilder(block));
                break;
            } else {
                String block = bits.substring(position, position + blockSize);
                if (block.getBytes(StandardCharsets.UTF_8)[0] == EMPTY_BYTE) {
                    break;
                }
                blocks.add(new StringBuilder(block));
                position += blockSize;
            }
        }
        return blocks;
    }

    private String getBitsFromBytes(byte[] bytes) {
        StringBuilder bits = new StringBuilder(bytes.length * 8);
        int position = 0;
        for (byte byte_ : bytes) {
            String s = Integer.toBinaryString(byte_);
            bits.replace(position, position + 8, "00000000");
            bits.replace(position + (8 - s.length()), position + 8, s);
            position += 8;
        }
        return bits.toString();
    }

    private byte[] getBytesFromBitString(String bits) {
        byte[] bytes = new byte[bits.length() / 8 + 1];
        int bytesPointer = 0;
        for (int i = 0; i < bits.length(); i += 8) {
            String bit = bits.substring(i, i + 8);
            if (bit.getBytes(StandardCharsets.UTF_8)[0] == EMPTY_BYTE) {
                break;
            }
            byte b = (byte) Integer.parseInt(bit, 2);
            bytes[bytesPointer] = b;
            bytesPointer++;
        }
        return bytes;
    }
}
