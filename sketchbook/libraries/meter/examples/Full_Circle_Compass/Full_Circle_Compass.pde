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
  m.setMinArcDegrees(0.0); // Zero (right side start)
  m.setMaxArcDegrees(360.0); // TWO_PI (right side end)

  // The scaleLabels array size MUST match the alternateScaleLabels array length.
  String[] scaleLabels = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0"};
  m.setScaleLabels(scaleLabels);
  String[] alternateScaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "E"};
  m.setAlternateScaleLabels(alternateScaleLabels);
  m.setDisplayAlternateScaleLabels(true);
  m.setDisplayLastScaleLabel(false);
  
  // Change the title from the default "Voltage" to a more meaningful label.
  m.setMeterTitle("Direction");

  // Display digital sensor values. While this will correctly indicate the input sensor value,
  // it will try to show a voltage value. Change the informationAreaText appropriately.
  m.setInformationAreaText("Sensor: % 4d");
  m.setDisplayDigitalSensorValues(true);
}

void draw() {

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}