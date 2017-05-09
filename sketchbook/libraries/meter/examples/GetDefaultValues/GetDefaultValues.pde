/* //<>//
 Default meter example. No hardware.
 Get and print all default values.
 
 How to display color in RGB format when printing?
 get & set min/maxArcDegrees --> arcMin/MaxDegrees in Meter code.
 Correctly print scaleLabels.
 Are sensorWarning colors the arc colors? If so, say so.
 Change Size to Thickness where appropriate, or use Processing termanology.
 */
import meter.*;

Meter m;

void setup() {
  size(600, 500);
  background(255, 255, 200);

  m = new Meter(this, 50, 25);
 // m.updateMeter(0); // Display meter with zero input sensor value.
  
  noLoop();
  
  System.out.println("meterX: " + m.getMeterX());
  System.out.println("meterY: " + m.getMeterY());
  System.out.println("meterWidth: " + m.getMeterWidth());
  System.out.println("meterHeight: " + m.getMeterHeight());
  System.out.println("fullCircle: " + m.getFullCircle());
  System.out.println("meterFrameThickness: " + m.getMeterFrameThickness());
  System.out.println("meterFrameColor: #" + hex(m.getMeterFrameColor(), 6));
  System.out.println("informationAreaFontSize: " + m.getInformationAreaFontSize());
  System.out.println("informationAreaText: \"" + m.getInformationAreaText() + "\"");
  System.out.println("informationAreaTextYOffset: " + m.getInformationAreaTextYOffset());
  System.out.println("informationAreaFontName: " + m.getInformationAreaFontName());
  System.out.println("informationAreaFontColor: #" + hex(m.getInformationAreaFontColor(), 6));
  System.out.println("displayDigitalSensorValues: " + m.getDisplayDigitalSensorValues());
  System.out.println("displayOnlySensorValue: " + m.getDisplayOnlySensorValue());
  System.out.println("meterTitleFontName: " + m.getMeterTitleFontName());
  System.out.println("meterTitleFontSize: " + m.getMeterTitleFontSize());
  System.out.println("meterTitleFontColor: #" + hex(m.getMeterTitleFontColor(), 6));
//  System.out.println("meterTitle: " + m.getMeterTitle());
  System.out.println("meterTitleYOffset: " + m.getMeterTitleYOffset());
  System.out.println("meterPivotPointSize: " + m.getMeterPivotPointSize());
  System.out.println("meterPivotPointColor: #" + hex(m.getMeterPivotPointColor(), 6));
  System.out.println("minInputSignal: " + m.getMinInputSignal());
  System.out.println("maxInputSignal: " + m.getMaxInputSignal());
  System.out.println("arcMinDegrees: " + m.getMinArcDegrees());
  System.out.println("arcMaxDegrees: " + m.getMaxArcDegrees());
  System.out.println("shortTicsBetweenLongTics: " + m.getShortTicsBetweenLongTics());
  System.out.println("displayArc: " + m.getDisplayArc());
  System.out.println("arcPositionOffset: " + m.getArcPositionOffset());
  System.out.println("arcColor: #" + hex(m.getArcColor(), 6));
  System.out.println("arcThickness: " + m.getArcThickness());
  System.out.println("meterScaleFontName: " + m.getMeterScaleFontName());
  System.out.println("meterScaleFontColor: #" + hex(m.getMeterScaleFontColor(), 6));
  System.out.println("meterScaleFontSize: " + m.getMeterScaleFontSize());
  System.out.println("meterScaleOffsetFromPivotPoint: " + m.getMeterScaleOffsetFromPivotPoint());
  System.out.println("displayLastScaleLabel: " + m.getDisplayLastScaleLabel());
  // Display the String array as a string
  System.out.println("scaleLabels: \"" + join(m.getScaleLabels(), "\", \"") + "\"");
//  System.out.println("displayAlternateScaleLabels: " + m.getDisplayAlternateScaleLabels());
  System.out.println("alternateScaleLabels: " + m.getAlternateScaleLabels());
  System.out.println("longTicMarkLength: " + m.getLongTicMarkLength());
  System.out.println("shortTicMarkLength: " + m.getShortTicMarkLength());
  System.out.println("ticMarkSetbackFromArc: " + m.getTicMarkSetbackFromArc());
  System.out.println("ticMarkOffsetFromPivotPoint: " + m.getTicMarkOffsetFromPivotPoint());
  System.out.println("ticMarkThickness: " + m.getTicMarkThickness());
  System.out.println("ticMarkColor: #" + hex(m.getTicMarkColor(), 6));
  System.out.println("lowSensorWarningActive: " + m.getLowSensorWarningActive());
  System.out.println("highSensorWarningActive: " + m.getHighSensorWarningActive());
  System.out.println("lowSensorWarningValue: " + m.getLowSensorWarningValue());
  System.out.println("highSensorWarningValue: " + m.getHighSensorWarningValue());
  System.out.println("lowSensorWarningColor: #" + hex(m.getLowSensorWarningColor(), 6));
  System.out.println("midSensorWarningColor: #" + hex(m.getMidSensorWarningColor(), 6));
  System.out.println("highSensorWarningColor: #" + hex(m.getHighSensorWarningColor(), 6));
  System.out.println("sensorWarningFontName: " + m.getSensorWarningFontName());
  System.out.println("lowSensorWarningFontColor: #" + hex(m.getLowSensorWarningFontColor(), 6));
  System.out.println("highSensorWarningFontColor: #" + hex(m.getHighSensorWarningFontColor(), 6));
  System.out.println("sensorWarningFontSize: " + m.getSensorWarningFontSize());
  System.out.println("sensorWarningTextYOffset: " + m.getSensorWarningTextYOffset());
  // Display the embedded new line, if it exists
  System.out.println("sensorWarningLowText: \"" + m.getSensorWarningLowText().replace("\n", "\\n") + "\"");
  System.out.println("sensorWarningHighText: \"" + m.getSensorWarningHighText().replace("\n", "\\n") + "\"");
  System.out.println("needleLength: " + m.getNeedleLength());
  System.out.println("needleColor: #" + hex(m.getNeedleColor(), 6));
  System.out.println("needleSize: " + m.getNeedleSize());
  
}

void draw() {
  int sensorValue = 128;
  delay(700);
  //   sensorValue = (int)random(minIn, maxIn);  // Input for testing
  m.updateMeter(sensorValue); // Update the sensor value to the meter.
}