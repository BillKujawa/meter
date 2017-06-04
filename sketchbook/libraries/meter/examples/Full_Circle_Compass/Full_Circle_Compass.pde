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
  // Make any changes to the meter immediately following its creation
  // since these only need to be made once.
  m = new Meter(this, 125, 25, true); // Instantiate a full circle meter class.

  // Define where the scale labels will appear
  // Note that the scale is counter clockwise. In this example
  // from zero degrees (3:00 Oclock to 360 degrees, again at 3:00 Oclock.
  m.setArcMinDegrees(0.0); // Zero (right side start)
  m.setArcMaxDegrees(360.0); // TWO_PI (right side end)
  
  // The scale labels are what the user sees on the meter.
  // Even though the last scale label is not being displayed
  // it must be included in the array. It can be any text value.
  // The number of scale labels determine the number and placement
  // of the long tic marks.
  String[] scaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "X"};
  m.setScaleLabels(scaleLabels);
  m.setDisplayLastScaleLabel(false);
  
  // Define the minimum and maximum meter reading.
  // In this example the minimum and maximum inputs are the typical
  // microprocessor outputs of 0 - 255. So setting the min and max
  // scale values from 0.0 to 255.0. You will notice that the input
  // values are integers while the meter scale values are floating point.
  m.setMinScaleValue(0.0f);
  m.setMaxScaleValue(255.0f);
  
  // Displaying the meterValue, in this case, is the sensor input value,
  // but converted to floating point.
  m.setDisplayDigitalMeterValue(true);

   // Change the title from the default "Voltage" to a more meaningful label.
  m.setTitle("Direction");
}

void draw() {

  // Simulate sensor data. If the input data is of a different range
  // change the above values to display the correct meter readings.
  int newSensorReading;
  newSensorReading = (int)random(0, 255);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}