/* //<>//
 Meter as a full circle.
 Note that the circle starts at 0 degrees (3:00 OClock) and
 moves clockwise. The scale labels have to be in this order.
 Demonstrate not displaying the last scale label,
 typically used with a 360 degree meter to prevent the
 last scale label from over writing the first one.
 
 Non-Hardware example.
 
 created May 17, 2017
 by Bill (Papa) Kujawa.
 
 This example code is in the public domain.
 */

import meter.*;

Meter m;

int i = 0;

void setup() {
  size(700, 600);
  background(255, 255, 200);

  // Display a full circle meter frame.
  m = new Meter(this, 125, 25, true); // Instantiate a full circle meter class.
  // Setting the meter width should be the first change.
  m.setMeterWidth(400);

  // Input signal range
  m.setMinInputSignal(0);
  m.setMaxInputSignal(255);

  // Arc settings
  // Define where the scale labels will appear
  m.setArcMinDegrees(00.0); // (start)
  m.setArcMaxDegrees(360.0); // ( end)
  m.setMinScaleValue(0);
  m.setMaxScaleValue(90);
  m.setTitle("Last Scale Label");

  // Meter Scale
  String[] scaleLabels = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
  m.setScaleLabels(scaleLabels);
  m.setDisplayLastScaleLabel(false);
}

void draw() {

  // Play
  if (i++ == 3) {
    m.setDisplayLastScaleLabel(true);
  }
  if (i >= 6) {
    m.setDisplayLastScaleLabel(false);
    i = 0;
  }

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  // Allow time to see the change.
  delay(1000);
}