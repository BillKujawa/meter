/*
  Analog input, analog output, serial output

  Reads an analog input pin, maps the result to a range from 0 to 255.

  Used by Processing Meter examples "Arduino_5_Volt" and "Arduino_3_Volt".

  The circuit:
   5K potentiometer, center pin, connected to analog pin 0.
   Side pins of the potentiometer go to +5V and ground
   White LED connected from digital pin 7 thru resistor to ground.
   Red LED connected from digital pin 10 thru resistor to ground.
   Green LED connected from digital pin 11 thru resistor to ground.
   Note: use appropriate resistor values to limit current. Try 220 ohms.

  created April 19, 2017
  by Bill (Papa) Kujawa.

  from example code by Tom Igoe.

  This example code is in the public domain.

*/

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

