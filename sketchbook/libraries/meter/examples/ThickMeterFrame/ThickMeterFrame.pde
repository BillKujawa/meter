/* //<>//
 Thick meter frame. No hardware.
 Adjust the position of the arc offset, meter scale offset,
 and the tic mark offset to have them fit in the smaller
 meter interior.
 With the thick frame, the information area happens to be 
 part of the frame, change the font color if necessary.
 Change the style of the frame corners:
 PConstants.MITER (8), PConstants.BEVEL (32), PConstants.ROUND (2).
 
 Note: the same offset adjustments could be used to accomodate
 longer scale labels.
 */
import meter.*;

Meter m, m2;

void setup() {
  size(500, 650);
  background(255, 255, 200);

  fill(120, 50, 0);
  m = new Meter(this, 10, 25);
  m.setFrameThickness(40);
  m.setFrameStyle(PConstants.ROUND);
  // Reduce the offsets
  m.setArcPositionOffset(140);
  m.setScaleOffsetFromPivotPoint(160);
  m.setTicMarkOffsetFromPivotPoint(106);
  // Adjust font color of meter value
  m.setDisplayDigitalMeterValue(true);
  m.setInformationAreaFontColor(color(255, 255, 255));
  // Draw a few lines around the meter frame to check position.
  int mx = m.getMeterX();
  int my = m.getMeterY();
  int mw = m.getMeterWidth();
  int mh = m.getMeterHeight();
  line(5, my, width - 5, my);
  line(mx, 5, mx, height - 5);
  line(mx + mw, 5, mx + mw, height - 5);
  line(5, my + mh, width - 5, my + mh);
  
  m.setLowSensorWarningActive(true);
  m.setLowSensorWarningValue((float)1.0);
  m.setHighSensorWarningActive(true);
  m.setHighSensorWarningValue((float)4.0);

  // A second meter for reference
  m2 = new Meter(this, mx, my + mh);
  m2.setDisplayDigitalMeterValue(true);
  // Display the difference in meter heights because of frame thickness.
  System.out.println("Meter1 Height: " + m.getMeterHeight());
  System.out.println("Meter2 Height: " + m2.getMeterHeight());
  System.out.println("Frame Style: " + m.getFrameStyle());
}

public void draw() {
  int newSensorReading = 120;
  newSensorReading = (int)random(-10, 265);

  m.updateMeter(newSensorReading);
  m2.updateMeter(newSensorReading);
  delay(1000);
}