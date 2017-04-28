#include <dht.h>

/*
  Analog input, analog output, serial output

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

*/

// These constants won't change.  They're used to give names
// to the pins used:
const int analogInPin = A0;  // Analog input pin that the potentiometer is attached to
const int DHT11_PIN = 5;  // Temperature and humidity sensor
const int whiteLed = 7;  // Sensor request received
const int redLed = 10;  // Oops
const int greenLed = 11;  // Sensor data sent to Processing

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
            //     analogWrite(analogOutPin, outputValue);

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
            float Temp;
            // Serial.print("Temperature = ");
            int chk = DHT.read11(DHT11_PIN);
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
            Serial.print(humiditySensor);
            Serial.print(",");
            Serial.println(DHT.humidity);
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
