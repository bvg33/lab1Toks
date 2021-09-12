package com.toks.laba1.controller;

import com.toks.laba1.Main;
import com.toks.laba1.creator.SerialPortCreator;
import com.toks.laba1.event.SendButtonClickedEvent;
import com.toks.laba1.event.SerialPortReader;
import com.toks.laba1.initializer.SerialPortInitializer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class SceneController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea textView;

    @FXML
    private ComboBox<String> speedButton;

   private final static String[] speeds = { "110", "300", "600", "1200", "4800",
            "9600", "14400","19200", "38400", "57600",  "115200", "128000", "256000" };

    @FXML
    void initialize() throws SerialPortException {
        speedButton.setValue("9600");

        SerialPortCreator creator = new SerialPortCreator();
        SerialPort port = creator.createSerialPort(textView);

        speedButton.getItems().addAll(speeds);
        sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> new SendButtonClickedEvent(speedButton,textField,port)
                .handleEvent());
    }

}
