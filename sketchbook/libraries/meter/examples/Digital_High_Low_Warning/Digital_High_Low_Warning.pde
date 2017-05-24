/* //<>//
 Display the digital meter value and High and Low Warnings.
 Non-Hardware example.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 This example code is in the public domain.
 */

import meter.*;

Meter m;

void setup() {
  size(600, 400);
  background(255, 255, 200);

  m = new Meter(this, 125, 25); // Instantiate a meter class.
  m.setDisplayDigitalMeterValue(true);
}

void draw() {

  // Simulate sensor data.
  int newSensorReading;
  // Use the default min and max input signal values for testing.
  newSensorReading = (int)random(0, 255);
  // Set a warning if sensor value is too low.
  m.setLowSensorWarningActive(true);
  m.setLowSensorWarningValue((float)1.0);
  // Set a warning if sensor value is too high.
  m.setHighSensorWarningActive(true);
  m.setHighSensorWarningValue((float)4.0);
  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}