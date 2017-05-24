/* //<>//
 Meter as a full circle compass.
 Note that the circle starts at zero degrees (3:00 OClock) and
 moves clockwise. The scale labels have to be in this order.
 The Meter defaults to high and low Meter values of 0.0 and 5.0.
 Set the Min and max Scale Values that correspond with the
 scale labels being displayed. We will not display the last 
 scale label to prevent dispaying it twice.
 
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
  // Define the minimum and maximum meter reading.
  m.setMinScaleValue(0.0f);
  m.setMaxScaleValue(255.0f);
  // In this case, this is the sensor input value.
  m.setDisplayDigitalMeterValue(true);

  // Even though the last scale label is not being displayed
  // it must be included in the array. It can be any text value.
  String[] scaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "X"};
  m.setScaleLabels(scaleLabels);
  m.setDisplayLastScaleLabel(false);

  // Change the title from the default "Voltage" to a more meaningful label.
  m.setMeterTitle("Direction");
}

void draw() {

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}