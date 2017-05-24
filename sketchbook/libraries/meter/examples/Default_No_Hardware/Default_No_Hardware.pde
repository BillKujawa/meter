/* //<>//
 Default meter example. No hardware.
 About as simple as it gets.
 */
import meter.*;

Meter m;
int sensorValue;
int minIn, maxIn;

void setup() {
  size(600, 500);
  background(255, 255, 200);

  m = new Meter(this, 50, 25);
  // Use the default values for testing, 0 - 255.
  minIn = m.getMinInputSignal();
  maxIn = m.getMaxInputSignal();
}

void draw() {
  // Input for testing.
  sensorValue = (int)random(minIn, maxIn);
  // Update the sensor value to the meter.
  m.updateMeter(sensorValue);
  // Use a delay to see the changes.
  delay(700);
}