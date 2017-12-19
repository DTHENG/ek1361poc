package com.dtheng.ek1361poc;

import com.pi4j.gpio.extension.ads.ADS1115GpioProvider;
import com.pi4j.gpio.extension.ads.ADS1115Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.i2c.I2CBus;
import lombok.extern.slf4j.Slf4j;

/**
 * EK1361 Soil Moisture Sensor Proof Of Concept
 *
 * @author Daniel Thengvall <fender5289@gmail.com>
 */
@Slf4j
public class EK1361POC {

    /**
     * Connects with the EK1361 Soil Moisture Sensor and establishes
     * a analog connection to the sensor which is then converted to a
     * digital I2C connection via a ADS1115 chip. Once the connection
     * has been established a listener is added and the changes in
     * soil moisture will be logged by the example
     *
     * For more info look at the README.md in the root directory
     */
    public static void main(String args[]) throws Exception {

        log.info("Starting...");

        final GpioController gpio = GpioFactory.getInstance();

        // create custom ADS1115 GPIO provider
        final ADS1115GpioProvider gpioProvider = new ADS1115GpioProvider(I2CBus.BUS_1, ADS1115GpioProvider.ADS1115_ADDRESS_0x48);

        // provision gpio analog input pins from ADS1115
        final GpioPinAnalogInput analogInput = gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A1);

        analogInput.addListener(new GpioPinListenerAnalog() {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event) {
                log.info("event.getValue() {}", event.getValue());
            }
        });
    }
}