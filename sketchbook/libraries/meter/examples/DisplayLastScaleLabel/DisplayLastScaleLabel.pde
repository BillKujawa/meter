/* //<>//
 Meter as a partial circle.
 Note that the circle starts at 90 degrees (6:00 OClock) and
 moves clockwise. The scale labels have to be in this order.
Demonstrate not displaying the last scale label,
typically used with a 360 degree meter to prevent the
last scale label from over writing the first one.

 Non-Hardware example.
 
 created April 19, 2017
 by Bill (Papa) Kujawa.
 
 This example code is in the public domain.
 */

import meter.*;

Meter m;

int i = 0;

void setup() {
  size(700, 600);
  background(255, 255, 200);

  // Display a list of available fonts from your computer.
//  String[] fontList = PFont.list();
//  printArray(fontList);

  // Display a full circle meter frame.
  m = new Meter(this, 125, 25, true); // Instantiate a full circle meter class.

  m.setMeterWidth(400);

  // Input signal range
  m.setMinInputSignal(0);
  m.setMaxInputSignal(255);

  // Arc settings
  m.setArcPositionOffset(140);
  // Define where the scale labels will appear
  m.setArcMinDegrees(90.0); // (start)
  m.setArcMaxDegrees(360.0); // ( end)
  m.setMinScaleValue(0);
  m.setMaxScaleValue(80);

  // Meter Scale
  String[] scaleLabels = {"0", "10", "20", "30", "40", "50", "60", "70", "80"};
  m.setScaleLabels(scaleLabels);
  m.setDisplayLastScaleLabel(false);
}

void draw() {

  // Play
  if (i++ == 4) {
    m.setDisplayLastScaleLabel(true);
  }
  if (i == 8) {
    m.setDisplayLastScaleLabel(false);
    i = 0;
  }

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}