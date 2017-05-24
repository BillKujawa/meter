/* //<>//
 Meter as a partial circle.
 Change all default values.
 Some values are changed in draw().
 Note that the circle starts at 90 degrees (6:00 OClock) and
 moves clockwise. The scale labels have to be in this order.
 
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
  String[] fontList = PFont.list();
  printArray(fontList);

  // Display a full circle meter frame.
  m = new Meter(this, 125, 25, true);

  m.setMeterWidth(400);
  m.setMeterFrameThickness(10);
  m.setMeterFrameColor(color(245, 10, 14));
  m.setMeterFrameStyle(PConstants.MITER);

  m.setMeterTitleFontSize(34);
  m.setMeterTitleFontName("Arial Bold Italic");
  m.setMeterTitleFontColor(color(0, 200, 0));
  m.setMeterTitle("Change");
  // Move title down
  m.setMeterTitleYOffset(18);  // default is 12 pixels

  // Input signal range
  m.setMinInputSignal(0);
  m.setMaxInputSignal(255);

  // Arc settings
  m.setArcPositionOffset(140);
  m.setArcColor(color(141, 113, 178));
  m.setArcThickness(10);
  // Define where the scale labels will appear
  m.setArcMinDegrees(90.0); // (start)
  m.setArcMaxDegrees(360.0); // ( end)
  m.setMinScaleValue(0);
  m.setMaxScaleValue(80);

  // Meter Scale
  String[] scaleLabels = {"0", "10", "20", "30", "40", "50", "60", "70", "80"};
  m.setScaleLabels(scaleLabels);
  m.setMeterScaleFontSize(30);
  m.setMeterScaleFontName("Times New Roman Bold Italic");
  m.setMeterScaleFontColor(color(232, 33, 73));
  m.setMeterScaleOffsetFromPivotPoint(165);

  // Tic Marks
  m.setLongTicMarkLength(60);
  m.setShortTicMarkLength(10);
  m.setShortTicsBetweenLongTics(9);
  m.setTicMarkColor(color(250, 150, 20));
  m.setTicMarkThickness(4);
  m.setTicMarkOffsetFromPivotPoint(130);

  // Display the digital meter value in the Information Area.
  m.setDisplayDigitalMeterValue(true);
  m.setInformationAreaFontSize(26);
  m.setInformationAreaTextYOffset(15);
  m.setInformationAreaFontName("Verdana Italic");
  m.setInformationAreaFontColor(color(32, 173, 227));

  // Pivot Point
  // Note that meterPivotPointX and meterPivotPointY are not settable.
  m.setMeterPivotPointSize(20);
  m.setMeterPivotPointColor(color(62, 211, 140));

  // Needle
  m.setNeedleLength(160);
  m.setNeedleColor(color(82, 115, 232));
  m.setNeedleThickness(4);

  m.setLowSensorWarningActive(true);
  m.setLowSensorWarningValue(23.0);
  m.setHighSensorWarningActive(true);
  m.setHighSensorWarningValue(55.0);
  m.setSensorWarningFontSize(19);
  m.setSensorWarningFontName("URW Palladio L Bold Italic");
  m.setLowSensorWarningFontColor(color(0, 200, 0));
  m.setHighSensorWarningFontColor(color(0, 0, 200));
  m.setSensorWarningTextYOffset(25);
  m.setSensorWarningLowText(" Cold\n  Today");
  m.setSensorWarningHighText("HOT        \nTomorrow");
  m.setLowSensorWarningArcColor(color(250, 255, 3));
  m.setMidSensorWarningArcColor(color(240, 240, 240));
  m.setHighSensorWarningArcColor(color(45, 187, 237));

  // Display warning messages to output
  m.setDisplayWarningMessagesToOutput(true);

  // Warn if the input signal to the meter is out-of-range.
  m.setEnableInputSignalOutOfRange(true);
  m.setInputSignalOutOfRangeFontName("URW Palladio L Italic");
  m.setInputSignalOutOfRangeFontColor(color(175, 80, 80));
  m.setInputSignalOutOfRangeFontSize(30);
  m.setInputSignalOutOfRangeText("Woops\nMy Bad");
  m.setInputSignalOutOfRangeTextFromPivotPoint(-40);
}

void draw() {

  // Play
  if (i++ == 5) {
    m.setDisplayArc(false);
    m.setDisplayLastScaleLabel(false);
    m.setLowSensorWarningActive(false);
    m.setEnableInputSignalOutOfRange(false);
  }
  if (i==8) {
    m.setHighSensorWarningActive(false);
    m.setDisplayWarningMessagesToOutput(false);
  }
  if (i == 10) {
    m.setDisplayArc(true);
  }
  if (i==12) {
    m.setLowSensorWarningActive(true);
  }
  if (i==14) {
    m.setHighSensorWarningActive(true);
    m.setEnableInputSignalOutOfRange(true);
  }
  if (i == 16) {
    m.setDisplayLastScaleLabel(true);
    m.setDisplayWarningMessagesToOutput(true);
  }
  if (i > 20) {
    i = 0;
  }

  // Simulate sensor data.
  int newSensorReading;
  newSensorReading = (int)random(-10, 265);

  // Display the new sensor value.
  m.updateMeter(newSensorReading);
  delay(1000); // Allow time to see the change.
}