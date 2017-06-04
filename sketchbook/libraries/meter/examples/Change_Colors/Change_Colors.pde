/* //<>// //<>//
 Meter as a partial circle.
 Change a few colors.
 Note that the circle starts at 90.0 degrees (6:00 OClock) and
 moves clockwise. The scale labels have to be in this order.

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

  m.setFrameColor(color(100, 0, 0));
  m.setTitleFontColor(color(0, 200, 0));
  m.setPivotPointColor(color(255, 0, 0));
  m.setArcColor(color(0, 0, 200));
  m.setScaleFontColor(color(200, 100, 0));
  m.setTicMarkColor(color(217, 22, 247));
  // Define where the scale labele will appear
  m.setArcMinDegrees(90.0); // (start)
  m.setArcMaxDegrees(360.0); // ( end)
  // Set the meter value correspond to the scale labels.
  m.setMinScaleValue(0.0);
  m.setMaxScaleValue(80.0);
  m.setInputSignalOutOfRangeFontColor(color(0, 255, 0));

  String[] scaleLabels = {"0", "10", "20", "30", "40", "50", "60", "70", "80"};
  m.setScaleLabels(scaleLabels);

  // Change the title from the default "Voltage" to a more meaningful label.
  m.setTitle("Rainbow");

  // Display the digital meter value.
  m.setDisplayDigitalMeterValue(true);
}

void draw() {

  // Simulate sensor data.
  int newSensorReading;
  // Force inputSignalOutOfRange of 0 - 255.
  newSensorReading = (int)random(-10, 265);
 
  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}