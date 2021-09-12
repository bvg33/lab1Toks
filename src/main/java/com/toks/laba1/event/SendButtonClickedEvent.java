package com.toks.laba1.event;

import com.toks.laba1.initializer.SerialPortInitializer;
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
            port.writeBytes(message);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
