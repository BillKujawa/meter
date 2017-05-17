/* //<>//
 Default meter example. No hardware.
 About as simple as it gets.
 */
import meter.*;

Meter m;

void setup() {
  size(600, 500);
  background(255, 255, 200);

  m = new Meter(this, 50, 25);
  // Display meter with zero input sensor value.
  // Can ommit this in setup().
  m.updateMeter(0);
}

void draw() {
  int sensorValue = 128;
  delay(700);
  //   sensorValue = (int)random(minIn, maxIn);  // Input for testing
  m.updateMeter(sensorValue); // Update the sensor value to the meter.
}