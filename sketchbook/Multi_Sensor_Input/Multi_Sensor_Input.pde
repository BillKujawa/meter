/* //<>// //<>//
 Display multiple meters for multiple sensors.
 Voltage, temperature, and humidity.
 See example Arduino code at end of file.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 from example code by Tom Igoe.
 
 This example code is in the public domain.
 */

import meter.*;
import processing.serial.*;

Meter mV, mC, mF, mH;

// Set to true if testing with hardware
 boolean usingMicroprocessor = true;
// boolean usingMicroprocessor = false;

boolean deBug = false;

final int voltageSensor = 1;
final int minInV = 0;
final int maxInV = 255;
final int temperatureSensorC = 2;  // 0-50 ℃ Centegrade
final int minInC = 0;
final int maxInC = 50;
final int humiditySensor = 3;      // 20-90%RH
final int minInH = 20;
final int maxInH = 90;
final int temperatureSensorF = 4;  // Convert to Farenheight
final float minInF = 0.0;
final float maxInF = 122.0;
final int sensorCount = 4;         // Number of sensors being polled

// calculation variables
boolean dataReceived = false;
int sensorNumber = 1; // Expected by microprocessor.
int sensorValue = 0; // Default for starting.
int currentSensor = 1;


Serial port1;

void setup() {
  size(1000, 700);
  background(255, 255, 200);

  // Open the port that the board is connected to and use the same speed.
  // println(Serial.list()); // Uncomment to find port name for your computer.
  if (usingMicroprocessor == true) {
    port1 = new Serial(this, "/dev/ttyACM0", 9600);
    // Set condition to read bytes into a buffer until a newline is received.
    port1.bufferUntil('\n');
  }

  mV = new Meter(this, 15, 5); // Voltage Meter
  // String[] scaleLabelsV = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0"};
  // mV.setScaleLabels(scaleLabelsV);
  mV.setDisplayDigitalSensorValues(true);

  mC = new Meter(this, 15, mV.getMeterHeight() + 15); // Centegrade Temperature Meter
  String[] scaleLabelsC = {"0.0", "10.0", "20.0", "30.0", "40.0", "50.0"};
  mC.setScaleLabels(scaleLabelsC);
  mC.setMeterTitle("Centegrade" + "\u00B0");  // Added the degree symbol
  mC.setInformationAreaText("Temperature = %.2f\u00B0");
  mC.setDisplayDigitalSensorValues(true);
  mC.setDisplayOnlySensorValue(true);

  mF = new Meter(this, mV.getMeterX() + mV.getMeterWidth() +25, 10, true); // Farenheight Temperature Meter
  mF.setMinArcDegrees(90.0); // (bottom)
  mF.setMaxArcDegrees(360.0); // (top)
  String[] scaleLabelsF = {"-10", "0", "10", "20", "30", "40", "50", "60", "70", 
    "80", "90", "100", "110", "120", "130"};
  mF.setScaleLabels(scaleLabelsF);
  mF.setMeterTitle("Farenheight" + "\u00B0");
  mF.setLowSensorWarningActive(true);
  mF.setLowSensorWarningValue((float)65.0);
  mF.setHighSensorWarningActive(true);
  mF.setHighSensorWarningValue((float)75.0);

  mH = new Meter(this, mF.getMeterX(), mF.getMeterY() + mF.getMeterHeight() + 10); // Humity Meter
  mH.setMeterWidth(340);
  mH.setMeterTitle("Humidity %");
  String[] scaleLabelsH = {"20", "30", "40", "50", "60", "70", "80", "90"};
  mH.setScaleLabels(scaleLabelsH);
}

void draw() {

  // Request new sensor data from microprocessor if previous data has been processed
  if (usingMicroprocessor == true && dataReceived == true) {
    port1.write('1');  // Send request for sensorNumber data
    dataReceived = false;  // Waiting for new data
  } else {
    switch (sensorNumber) {
    case voltageSensor: 
      {
        if (usingMicroprocessor == false) {
          sensorValue = 128;
          delay(200);
          sensorValue = (int)random(minInV, maxInV);  // Input for testing
        }
        mV.updateMeter(sensorValue);
      }
    case temperatureSensorC:
      {
        if (usingMicroprocessor == false) {
          sensorValue = 22;
          delay(200);
          sensorValue = (int)random(minInC, maxInC);  // Input for testing
        }
        mC.updateMeter(sensorValue);
      }
    case humiditySensor: 
      {
        if (usingMicroprocessor == false) {
          sensorValue = 128;
          delay(200);
          sensorValue = (int)random(minInH, maxInH);  // Input for testing
        }
        mH.updateMeter(sensorValue);
      }
    case temperatureSensorF:
      {
        if (usingMicroprocessor == false) {
          sensorValue = 22;
          delay(200);
          sensorValue = (int)random(minInF, maxInF);  // Input for testing
        }
        mF.updateMeter(sensorValue);
      }
    }
  }
}

void serialEvent(Serial port1) {
  // The microprocessor should send initial zero value data to begin contact.
  // The draw() loop will then request data from the microprocessor.
  if (deBug == true) {
    println("serialEvent entered");
  }
  String serialIn = port1.readStringUntil('\n');

  // Remove anything but data and commas
  serialIn = trim(serialIn);
  if (deBug == true) {
    println("serialIn: " + serialIn);
  }
  // Process data from the microprocessor
  // Split the line at the commas and convert the data to integers
  int sensorData[] = int(split(serialIn, ','));
  sensorNumber = sensorData[0];
  sensorValue = sensorData[1];
  if (deBug == true) {
    int sensorDataArrayLength = sensorData.length;
    println("sensorDataArrayLength: " + sensorDataArrayLength);
    for (int i = 0; i < sensorDataArrayLength; i += 2) {
      println("Sensor: " + sensorData[i] + "  SensorValue: " + sensorData[i+1]);
    }
  }
  dataReceived = true;
}


// ----------------------------------------------------------
/*
  Arduino code to send sensor data to Processing.
 Analog input, analog output, serial output.
 
 Reads an analog input pin, maps the result to a range from 0 to 255.
 
 The circuit:
 5K potentiometer, center pin, connected to analog pin 0.
 Side pins of the potentiometer go to +5V and ground.
 White LED connected from digital pin 7 thru resistor to ground.
 Red LED connected from digital pin 10 thru resistor to ground.
 Green LED connected from digital pin 11 thru resistor to ground.
 Note: use appropriate resistor values to limit current. Try 220 ohms.
 
 Note: Try restarting the Processing code if not receiving initial
 data from the microprocessor.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 from example code by Tom Igoe.
 
 This example code is in the public domain.
 */