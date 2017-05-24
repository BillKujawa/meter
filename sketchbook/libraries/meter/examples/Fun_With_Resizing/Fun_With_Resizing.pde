/* //<>//
 Change the meter size by changing the width.
 The meter will randomly resize itself each time this 
 sketch is executed.  Just rerun this sketch.
 Note: Change the size as the first change after creating
 the meter. Changing the size depends upon the default 
 meter settings. Make any other change after this.
 
 Non-Hardware example.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 This example code is in the public domain.
 */

import meter.*;

Meter m;

void setup() {
  size(1200, 800);
  background(255, 255, 200);
}

void draw() {
  m = new Meter(this, 125, 25); // Instantiate a meter class.

  // Set a new meter width. Refun the sketch to show different sizes.
  m.setMeterWidth((int)random(50, 700));
  // Display digital meter value.
  m.setDisplayDigitalMeterValue(true);
  // Set a warning if sensor value is too low.
  m.setLowSensorWarningActive(true);
  m.setLowSensorWarningValue((float)1.0);
  // Set a warning if sensor value is too high.
  m.setHighSensorWarningActive(true);
  m.setHighSensorWarningValue((float)4.0);

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);
  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}