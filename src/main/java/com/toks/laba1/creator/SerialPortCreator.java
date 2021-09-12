package com.toks.laba1.creator;

import com.toks.laba1.Main;
import com.toks.laba1.event.SerialPortReader;
import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortCreator {
    public SerialPort createSerialPort(TextArea textView) throws SerialPortException {
        String serialPortName = Main.getStageTitle();
        SerialPort port = new SerialPort(serialPortName);
        port.openPort();
        port.addEventListener(new SerialPortReader(port,textView), SerialPort.MASK_RXCHAR);
        return port;
    }
}
