package com.toks.laba1.event;

import com.toks.laba1.codding.HammingCoder;
import com.toks.laba1.packaging.Package;
import com.toks.laba1.packaging.PackageService;
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
                byte[] packageData = port.readBytes(serialPortEvent.getEventValue());
                String outputData = retrieveData(packageData);
                textView.appendText(port.getPortName() + " receive data :" + outputData + '\n');
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private String retrieveData(byte[] packageData) {
        Package receivedPackage = new Package(packageData);
        String stringToReturn;
        PackageService service = new PackageService();
        receivedPackage = service.byteDestaff(receivedPackage);
        HammingCoder coder = new HammingCoder();
        byte[] decodingMessage = coder.decode(receivedPackage.getMessage());
        if(!receivedPackage.isFCSValid()){
            stringToReturn = "Data was corrupted";
        } else {
            stringToReturn = new String(decodingMessage, StandardCharsets.UTF_8);
        }
        return stringToReturn;
    }
}