/* //<>// //<>//
 Display multiple meters for multiple sensors.
 Voltage, temperature, and humidity.
 See example Arduino code at end of file.
 
  The source code can be found at:
  https://github.com/BillKujawa/meter/tree/master/Arduino
 
 Breathe on the temperature/humidity sensor to test.
 
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

// boolean deBug = false;
boolean deBug = true;

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
final int minInF = 0;
final int maxInF = 122;
final int sensorCount = 4;         // Number of sensors being polled
String[] sensorList = {"1", "2", "3", "4"};
int i = 0;  // sensor list index

// calculation variables
boolean dataReceived = false;
int sensorNumber = 1; // Expected by microprocessor.
int sensorValue = 0; // Default for starting.


Serial port1;

void setup() {
  size(1000, 700);
  background(255, 255, 200);

  // Open the port that the board is connected to and use the same speed.
  // println(Serial.list()); // Uncomment to find port name for your computer.
  if (usingMicroprocessor == true) {
    port1 = new Serial(this, "/dev/ttyACM0", 9600);
    // port1 = new Serial(this, "COM5", 9600);
    // Set condition to read bytes into a buffer until a newline is received.
    port1.bufferUntil('\n');
  }

  // Voltage Meter
  mV = new Meter(this, 15, 5);
  mV.setDisplayDigitalMeterValue(true);
  mV.updateMeter(0);

  // Centegrade Temperature Meter
  mC = new Meter(this, 15, mV.getMeterHeight() + 15); 
  String[] scaleLabelsC = {"0.0", "10.0", "20.0", "30.0", "40.0", "50.0"};
  mC.setScaleLabels(scaleLabelsC);
  mC.setUp(minInC, maxInC, 0.0f, 50.0f, 180.0f, 360.0f);
  mC.setTitle("Centegrade" + "\u00B0");  // Added the degree symbol
  mC.setInformationAreaText("Temperature = %.2f\u00B0");
  mC.setDisplayDigitalMeterValue(true);
  mC.updateMeter(20);

  // Farenheight Temperature Meter
  mF = new Meter(this, mV.getMeterX() + mV.getMeterWidth() +25, 10, true);
  mF.setUp(minInF, maxInF, 0.0f, 122.0f, 90.0f, 360.0f);
  String[] scaleLabelsF = {"-10", "0", "10", "20", "30", "40", "50", "60", "70", 
    "80", "90", "100", "110", "120", "130"};
  mF.setScaleLabels(scaleLabelsF);
  mF.setTitle("Farenheight" + "\u00B0");
  mF.setLowSensorWarningActive(true);
  mF.setLowSensorWarningValue((float)65.0);
  mF.setHighSensorWarningActive(true);
  mF.setHighSensorWarningValue((float)85.0);
  mF.setDisplayDigitalMeterValue(true);
  mF.updateMeter(70);

  // Humity Meter
  mH = new Meter(this, mF.getMeterX(), mF.getMeterY() + mF.getMeterHeight() + 10); 
  mH.setMeterWidth(340);
  mH.setUp(minInH, maxInH, 20.0f, 90.0f, 180.0f, 360.0f);
  mH.setTitle("Humidity %");
  String[] scaleLabelsH = {"20", "30", "40", "50", "60", "70", "80", "90"};
  mH.setScaleLabels(scaleLabelsH);
  mH.setDisplayDigitalMeterValue(true);
  mH.updateMeter(50);
}

void draw() {

  // Process current sensor data from serial event
  switch (sensorNumber) {
  case voltageSensor: 
    {
      if (usingMicroprocessor == false) {
        // Input for testing
        sensorValue = (int)random(minInV, maxInV);
      }
      mV.updateMeter(sensorValue);
      break;
    }

  case temperatureSensorC:
    {
      if (usingMicroprocessor == false) {
        // Input for testing
        sensorValue = (int)random(minInC, maxInC);
      }
      mC.updateMeter(sensorValue);
      break;
    }
  case humiditySensor: 
    {
      if (usingMicroprocessor == false) {
        // Input for testing
        sensorValue = (int)random(minInH, maxInH);
      }
      mH.updateMeter(sensorValue);
      break;
    }
  case temperatureSensorF:
    {
      if (usingMicroprocessor == false) {
        // Input for testing
        sensorValue = (int)random(minInF, maxInF);
      }
      mF.updateMeter(sensorValue);
      break;
    }
  }

  // Request new sensor data from microprocessor if previous data has been processed
  if (usingMicroprocessor == true && dataReceived == true) {
    // Send request for sensorNumber data
    port1.write(sensorList[i]);
    dataReceived = false;  // Waiting for new data
  }
  // Must wait at least two seconds between temp/humidity requests or Arduino sketch
  // will give an error and disable the serialEvent().
  if (++i >= sensorCount) {
    i = 0;
  }
  sensorNumber = i - 1;
  delay(300);
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
    println("Sensor: " + sensorData[0] + "  SensorValue: " + sensorData[1]);
  }
  dataReceived = true;
}


// ----------------------------------------------------------
/*
  Multi-SensorToProcessing
 
 #include <dht.h>
 
 
 Analog input, analog output, serial output
 Used with Processing sketch: Multi_Sensor_Input.
 
 Reads an analog input pin, maps the result to a range from 0 to 255
 and uses the result to set the pulsewidth modulation (PWM) of an output pin.
 This varies the brightness of a LED, as determined by the analog input
 from the potentiometer.
 Also prints the results to the serial monitor.
 
 The circuit:
 potentiometer connected to analog pin 0.
 Center pin of the potentiometer goes to the analog pin.
 side pins of the potentiometer go to +5V and ground
 LED connected from digital pin 9 to ground.
 
 See the following for the DHT sensor:
 and which pins to connect where.
 http://playground.arduino.cc/Main/DHTLib
 
 created April 20, 2017
 by Bill (Papa) Kujawa
 
 Serial communication inspired by Tom Igoe
 
 This example code is in the public domain.
 
 Basic testing using the Serial Monitor:
 Download the sketch.
 In the serial moditor, enter 1. The voltage value will be output to the
 serial monitor (0 - 255). The white LED should flash once. The green LED
 will be ON. The voltage LED will indicate the value of the potentiometer
 (dark to bright). Change the potentiometer setting, then enter 1 in the
 serial monotor to change the voltage output and LED brightness.
 Enter 2 in the serial monitor. The output will be the temperature in
 Celcius, room temperature is around 25.
 Enter 3 in the serial monitor. The output will be the relative humidity,
 which was 43 percent today in Colorado.
 Enter 4 in the serial monitor. The output will be the temperature in
 Farenheight, about 75 for the room temperature.
 If the red LED is ON, there is an input error, a value other than 1 - 4.
 
 If this sketch is connected to Processing, instesd of the Arduino IDE,
 the values can be monitored via the Multi_Sensor_Input sketch using
 the Meter library.
 
 
 
 // These constants won't change.  They're used to give names
 // to the pins used:
 const int analogInPin = A0;  // Analog input pin that the potentiometer is attached to
 const int DHT11_PIN = 5;  // Temperature and humidity sensor
 // The following LEDs are connected to ground through a 220 or larger ohm resistor
 const int whiteLed = 7;  // Sensor request received
 const int redLed = 10;  // Oops
 const int greenLed = 11;  // Sensor data sent to Processing
 const int voltageLed = 9; // Voltage level indicator
 
 dht DHT;
 
 int sensorValue = 0;        // value read from the port
 int outputValue = 0;        // value output to the PWM (analog out)
 
 // Label each sensor to determine which data to return to Processing.
 int sensorNumber = 0;
 const int voltageSensor = 1;
 const int temperatureSensorC = 2;  // 0-50 ℃ Centegrade
 const int humiditySensor = 3;      // 20-90%RH
 const int temperatureSensorF = 4;  // Convert to Farenheight
 
 // Setup data buffer.
 String inString;
 int inByte = 0; // Incoming serial data byte
 boolean firstContact = false; // No valid data in buffer
 int i;
 
 void setup() {
 pinMode(whiteLed, OUTPUT);  // valid sensor request received
 pinMode(redLed, OUTPUT);    // invalid sensor request
 pinMode(greenLed, OUTPUT);  // valid sensor request, sent sensor data
 pinMode(voltageLed, OUTPUT); // Use the builtin LED to indicate voltage setting
 
 // initialize serial communications at 9600 bps:
 Serial.begin(9600);
 // Wait for serial port to connect. Needed for native USB port only.
 while (!Serial)
 {
 ; // Do nothing until connection established.
 }
 
 // Establish contact by sending initial data to Processing.
 // Loop on this until Processing responds by sending a request for sensor data.
 // Then start the loop().
 while (Serial.available() <= 0) // no data received
 {
 Serial.println("0,0");  // Initial data expected by processing Meter
 Serial.flush();
 delay(500);
 }
 digitalWrite(redLed, LOW);
 }
 
 void loop()
 {
 if (Serial.available() > 0)
 {
 digitalWrite(greenLed, LOW);
 // Read serial data
 inByte = Serial.read();
 if (inByte > 0 && isDigit(inByte))
 {
 // Serial.print("isDigit: ");
 inString = (char)inByte;
 sensorNumber = inString.toInt();
 // Serial.print("sensorNumber: ");
 // Serial.println(sensorNumber);
 digitalWrite(whiteLed, HIGH);
 }
 else
 {
 sensorNumber = 0;
 }
 
 // Read and interpret serial input data and respond with requested sensor data.
 if (sensorNumber > 0)
 {
 switch (sensorNumber)
 {
 case voltageSensor:
 {
 // read the analog voltage
 sensorValue = analogRead(analogInPin);
 // map it to the range of the analog out:
 outputValue = map(sensorValue, 0, 1023, 0, 255);
 // change the analog out value:
 analogWrite(voltageLed, outputValue);
 
 // Send back the requested results
 Serial.print(voltageSensor);
 Serial.print(",");
 Serial.println(outputValue);
 digitalWrite(greenLed, HIGH);
 digitalWrite(whiteLed, LOW);
 break;
 }
 case temperatureSensorC:
 case temperatureSensorF:
 {
 int Temp;
 // Serial.print("Temperature = ");
 int chk = DHT.read11(DHT11_PIN);
 if (chk != 0) {
 Serial.print("DTT11 error: ");
 Serial.println(chk);
 }
 if (sensorNumber == temperatureSensorC) {
 Serial.print(temperatureSensorC);
 Temp = DHT.temperature;
 }
 else {
 Serial.print(temperatureSensorF);
 Temp = (DHT.temperature * 9.0) / 5.0 + 32.0;  // Convert from C to F.
 }
 Serial.print(",");
 Serial.println(Temp);
 digitalWrite(greenLed, HIGH);
 digitalWrite(whiteLed, LOW);
 break;
 }
 case humiditySensor:
 {
 // Serial.print("Humidity = ");
 int chk = DHT.read11(DHT11_PIN);
 if (chk != 0) {
 Serial.print("DTT11 error: ");
 Serial.println(chk);
 }
 Serial.print(humiditySensor);
 Serial.print(",");
 Serial.println((int)DHT.humidity);
 digitalWrite(greenLed, HIGH);
 digitalWrite(whiteLed, LOW);
 break;
 }
 default:
 {
 // Error handling statements
 digitalWrite(redLed, HIGH);
 break;
 }
 }
 }
 // wait 2 milliseconds before the next loop
 // for the analog-to-digital converter to settle
 // after the last reading:
 delay(20);
 }
 }
 */