package com.toks.laba1.initializer;

import com.toks.laba1.event.SerialPortReader;
import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortInitializer {
    public void initSerialPort(SerialPort port,String baudrate) throws SerialPortException {
        port.setParams(Integer.parseInt(baudrate),SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
        port.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                SerialPort.FLOWCONTROL_RTSCTS_OUT);
    }
}
