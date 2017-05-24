/* //<>//
 Meter as a full circle.
 Note that the circle starts at zero degrees (3:00 OClock) and
 moves clockwise to 360 degrees (again 3:00 OClock).
 The scale labels are set from 0.0 to 5.0. This calculates the correct
 tic mark locations. This also means that 0.0 and 5.0 are at the same place
 on the circle. Thus we will not display the last scale value.
 Non-Hardware example.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 This example code is in the public domain.
 */

import meter.*;

Meter m;

void setup() {
  size(700, 600);
  background(255, 255, 200);

  // Display a full circle meter.
  m = new Meter(this, 125, 25, true); // Instantiate a full circle meter class.

  // Define where the scale labele will appear
  m.setArcMinDegrees(0.0); // Zero (right side start)
  m.setArcMaxDegrees(360.0); // TWO_PI (right side end)
  m.setDisplayLastScaleLabel(false);
  // Display digital meter value.
  m.setDisplayDigitalMeterValue(true);
  // Set a warning if sensor value is too low.
  m.setLowSensorWarningActive(true);
  m.setLowSensorWarningValue((float)1.0);
  // Set a warning if sensor value is too high.
  m.setHighSensorWarningActive(true);
  m.setHighSensorWarningValue((float)4.0);
}

void draw() {

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);
  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  // Allow time to see the change.
  delay(1000);
}