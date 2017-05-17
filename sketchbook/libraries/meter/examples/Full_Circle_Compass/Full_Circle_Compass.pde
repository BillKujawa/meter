/* //<>//
 Meter as a full circle compass.
 Note that the circle starts at zero degrees (3:00 OClock) and
 moves clockwise. The scale labels have to be in this order.
 Note: the internal calculations depend upon digital scale labels to correctly
 calculate the label positions. Use the alternate scale labels to display
 different text. ** The number of scale labels must match the number of
 alternate scale labels. And again we will not display the last one to
 prevent dispaying it twice.
 
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

  String[] scaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "E"};
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