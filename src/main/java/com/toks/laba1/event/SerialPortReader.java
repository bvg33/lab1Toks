package com.toks.laba1.event;

import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;

public class SerialPortReader implements SerialPortEventListener {
    private SerialPort port;
    private javafx.scene.control.TextArea textView;

    public SerialPortReader(SerialPort port, TextArea textView) {
        this.port = port;
        this.textView = textView;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {
            try {
                byte [] dataByteFormat = port.readBytes(serialPortEvent.getEventValue());
                String outputData = new String(dataByteFormat, StandardCharsets.UTF_8);
                textView.appendText(port.getPortName() + " receive data :" + outputData +'\n');
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
}