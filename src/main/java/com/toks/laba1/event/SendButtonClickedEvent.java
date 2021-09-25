package com.toks.laba1.event;

import com.toks.laba1.initializer.SerialPortInitializer;
import com.toks.laba1.packaging.Package;
import com.toks.laba1.packaging.PackageService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.nio.charset.StandardCharsets;

public class SendButtonClickedEvent {
    private ComboBox<String> speedButton;

    private TextField textField;

    private SerialPort port;

    public SendButtonClickedEvent(ComboBox<String> speedButton, TextField textField, SerialPort port) {
        this.speedButton = speedButton;
        this.textField = textField;
        this.port = port;
    }

    public void handleEvent() {
        try {
            SerialPortInitializer initializer = new SerialPortInitializer();
            String baudrate = speedButton.getValue();
            initializer.initSerialPort(port, baudrate);
            byte[] message = textField.getText().getBytes(StandardCharsets.UTF_8);
            Package packageData = createPackage(message);
            port.writeBytes(packageData.getData());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private Package createPackage(byte[] message) {
        String from = (port.getPortName().equals("COM1")) ? "1" : "2";
        String to = (from.equals("1")) ? "2" : "1";
        Package packageData = new Package(message, from, to);
        return new PackageService().byteStaff(packageData);
    }
}
