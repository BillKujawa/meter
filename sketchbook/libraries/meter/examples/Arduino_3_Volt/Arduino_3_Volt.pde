/* //<>//
 Default meter example. Ardunio and 3 volt sensor.
 Start the microprocessor running, then start this sketch.
 See example Arduino code at end of file:
  "AnalogInOutSerialProcessing".
  
  The source code can be found at:
  https://github.com/BillKujawa/meter/tree/master/Arduino
 
  created April 19, 2017
  by Bill (Papa) Kujawa.

  from example code by Tom Igoe.

  This example code is in the public domain.
 */
 
import meter.*;
import processing.serial.*;

Meter m;

boolean deBug = false;

// calculation variables
int i = 0;
boolean dataReceived = false;
int sensorNumber = 1; // Expected by microprocessor.
int sensorValue = 0; // Default for starting.

Serial port1;

void setup() {
  size(600, 400);
  background(255, 255, 200);

  // Open the port that the board is connected to and use the same speed.
  // println(Serial.list()); // Uncomment to find port name for your computer.

  port1 = new Serial(this, "/dev/ttyACM0", 9600);
  // port1 = new Serial(this, "COM5", 9600);
  // Set condition to read bytes into a buffer until a newline is received.
  port1.bufferUntil('\n');

  m = new Meter(this, 125, 25); // Instantiate a meter class.
  // Change scale labels to coinside with 0.0 to 3.0 volt sensor range.
  String[] scaleLabels = {"0.0", "1.0", "2.0", "3.0"};
  m.setScaleLabels(scaleLabels);
  m.setDisplayDigitalMeterValue(true);
  m.setMaxScaleValue(3.0f);
  m.setShortTicsBetweenLongTics(9);
}

void draw() {

  // Request new sensor data from microprocessor if previous data has been processed
  if (dataReceived == true) {
    port1.write('1');  // Send request for sensorNumber data
    dataReceived = false;  // Waiting for new data

    m.updateMeter(sensorValue);
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
  int sensorDataArrayLength = sensorData.length;
  sensorNumber = sensorData[i];
  sensorValue = sensorData[i+1];
  if (deBug == true) {
    println("sensorDataArrayLength: " + sensorDataArrayLength);
    for (int i = 0; i < sensorDataArrayLength; i += 2) {
      println("Sensor: " + sensorData[i] + "  SensorValue: " + sensorData[i+1]);
    }
  }
  dataReceived = true;
}


// ----------------------------------------------------------
/*
  AnalogInOutSerialToProcessing

  Arduino code to send sensor data to Processing.
  Analog input, analog output, serial output.
  
  The source code can be found at:
  https://github.com/BillKujawa/meter/tree/master/Arduino

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

/*
// These constants won't change.  They're used to give names
// to the pins used:
const int analogInPin = A0;  // Analog input pin that the potentiometer is attached to
const int whiteLed = 7;
const int redLed = 10;
const int greenLed = 11;

int sensorValue = 0;        // value read from the port

// Label each sensor to determine which data to return to Processing.
int sensorNumber = 0;

// Setup data buffer.
String inString;
int inByte = 0; // Incoming serial data byte.
int outputValue; // Sensor data to send to Processing.
boolean firstContact = false; // No valid data in buffer.
int i;

void setup() {
  pinMode(whiteLed, OUTPUT);  // valid sensor request received
  pinMode(redLed, OUTPUT);    // invalid sensor request
  pinMode(greenLed, OUTPUT);  // valid sensor request, sent sensor data

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
  while (Serial.available() <= 0) // No data received.
  {
    Serial.println("0,0"); // Initial hello to Processing.
    Serial.flush();
    delay(500);
  }
  digitalWrite(redLed, LOW); // Error if lit.
}

void loop()
{
  if (Serial.available() > 0)
  {
    digitalWrite(greenLed, LOW);
    // Read serial data
    inByte = Serial.read();
    if (inByte > 0 && isDigit(inByte)) // Expecting a "1" from Processing.
    {
      inString = (char)inByte;
      sensorNumber = inString.toInt();
      digitalWrite(whiteLed, HIGH);  // Sensor data request received.

      // read the analog voltage
      sensorValue = analogRead(analogInPin);
      // map it to the range of the analog out:
      outputValue = map(sensorValue, 0, 1023, 0, 255);
      // Send back the requested results
      Serial.print("1,");
      Serial.println(outputValue);
      digitalWrite(greenLed, HIGH);
      digitalWrite(whiteLed, LOW);
    }
    else
    {
      // Error handling statements
      digitalWrite(redLed, HIGH);
    }
  }
  // wait 2 milliseconds before the next loop
  // for the analog-to-digital converter to settle
  // after the last reading:
  delay(20);
}
*/