/* //<>//
 Default meter example. No hardware.
 Get and print all default values.
 
 get & set min/maxArcDegrees --> arcMin/MaxDegrees in Meter code.
 Are sensorWarning colors the arc colors? If so, say so.
 Change Size to Thickness where appropriate, or use Processing terminology.
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
  System.out.println("meterFrameColor: " + (int)red(m.getMeterFrameColor()) + ", " + 
      (int)green(m.getMeterFrameColor()) + ", " + (int)blue(m.getMeterFrameColor()));
  System.out.println("meterFrameStyle: " + m.getMeterFrameStyle());
  System.out.println("informationAreaFontSize: " + m.getInformationAreaFontSize());
  System.out.println("informationAreaTextYOffset: " + m.getInformationAreaTextYOffset());
  System.out.println("informationAreaFontName: " + m.getInformationAreaFontName());
  System.out.println("informationAreaFontColor: " + (int)red(m.getInformationAreaFontColor()) + ", " + 
      (int)green(m.getInformationAreaFontColor()) + ", " + (int)blue(m.getInformationAreaFontColor()));
  System.out.println("displayDigitalMeterValue: " + m.getDisplayDigitalMeterValue());
  System.out.println("currentMeterValue: " + m.getCurrentMeterValue());
  System.out.println("meterTitle: \"" + m.getMeterTitle() + "\"");
  System.out.println("meterTitleFontName: " + m.getMeterTitleFontName());
  System.out.println("meterTitleFontSize: " + m.getMeterTitleFontSize());
  System.out.println("meterTitleFontColor: " + (int)red(m.getMeterTitleFontColor()) + ", " + 
      (int)green(m.getMeterTitleFontColor()) + ", " + (int)blue(m.getMeterTitleFontColor()));
  System.out.println("meterTitle: " + m.getMeterTitle());
  System.out.println("meterTitleYOffset: " + m.getMeterTitleYOffset());
  System.out.println("meterPivotPointSize: " + m.getMeterPivotPointSize());
  System.out.println("meterPivotPointColor: " + (int)red(m.getMeterPivotPointColor()) + ", " + 
      (int)green(m.getMeterPivotPointColor()) + ", " + (int)blue(m.getMeterPivotPointColor()));
  System.out.println("minInputSignal: " + m.getMinInputSignal());
  System.out.println("maxInputSignal: " + m.getMaxInputSignal());
  System.out.println("arcMinDegrees: " + m.getArcMinDegrees());
  System.out.println("arcMaxDegrees: " + m.getArcMaxDegrees());
  System.out.println("shortTicsBetweenLongTics: " + m.getShortTicsBetweenLongTics());
  System.out.println("displayArc: " + m.getDisplayArc());
  System.out.println("arcPositionOffset: " + m.getArcPositionOffset());
  System.out.println("arcColor: " + (int)red(m.getArcColor()) + ", " + 
      (int)green(m.getArcColor()) + ", " + (int)blue(m.getArcColor()));
  System.out.println("arcThickness: " + m.getArcThickness());
  System.out.println("meterScaleFontName: " + m.getMeterScaleFontName());
  System.out.println("meterScaleFontColor: " + (int)red(m.getMeterScaleFontColor()) + ", " + 
      (int)green(m.getMeterScaleFontColor()) + ", " + (int)blue(m.getMeterScaleFontColor()));
  System.out.println("meterScaleFontSize: " + m.getMeterScaleFontSize());
  System.out.println("meterScaleOffsetFromPivotPoint: " + m.getMeterScaleOffsetFromPivotPoint());
  System.out.println("displayLastScaleLabel: " + m.getDisplayLastScaleLabel());
  // Display the String array as a string
  System.out.println("scaleLabels: \"" + join(m.getScaleLabels(), "\", \"") + "\"");
//  System.out.println("displayAlternateScaleLabels: " + m.getDisplayAlternateScaleLabels());
  System.out.println("alternateScaleLabels: " + m.getAlternateScaleLabels());
  System.out.println("longTicMarkLength: " + m.getLongTicMarkLength());
  System.out.println("shortTicMarkLength: " + m.getShortTicMarkLength());
  System.out.println("ticMarkOffsetFromPivotPoint: " + m.getTicMarkOffsetFromPivotPoint());
  System.out.println("ticMarkThickness: " + m.getTicMarkThickness());
  System.out.println("ticMarkColor: " + (int)red(m.getTicMarkColor()) + ", " + 
      (int)green(m.getTicMarkColor()) + ", " + (int)blue(m.getTicMarkColor()));
  System.out.println("lowSensorWarningActive: " + m.getLowSensorWarningActive());
  System.out.println("highSensorWarningActive: " + m.getHighSensorWarningActive());
  System.out.println("lowSensorWarningValue: " + m.getLowSensorWarningValue());
  System.out.println("highSensorWarningValue: " + m.getHighSensorWarningValue());
  System.out.println("lowSensorWarningColor: " + (int)red(m.getLowSensorWarningArcColor()) + ", " + 
      (int)green(m.getLowSensorWarningArcColor()) + ", " + (int)blue(m.getLowSensorWarningArcColor()));
  System.out.println("midSensorWarningColor: " + (int)red(m.getMidSensorWarningArcColor()) + ", " + 
      (int)green(m.getMidSensorWarningArcColor()) + ", " + (int)blue(m.getMidSensorWarningArcColor()));
  System.out.println("highSensorWarningColor: " + (int)red(m.getHighSensorWarningArcColor()) + ", " + 
      (int)green(m.getHighSensorWarningArcColor()) + ", " + (int)blue(m.getHighSensorWarningArcColor()));
  System.out.println("sensorWarningFontName: " + m.getSensorWarningFontName());
  System.out.println("lowSensorWarningFontColor: " + (int)red(m.getLowSensorWarningFontColor()) + ", " + 
      (int)green(m.getLowSensorWarningFontColor()) + ", " + (int)blue(m.getLowSensorWarningFontColor()));
  System.out.println("highSensorWarningFontColor: " + (int)red(m.getHighSensorWarningFontColor()) + ", " + 
      (int)green(m.getHighSensorWarningFontColor()) + ", " + (int)blue(m.getHighSensorWarningFontColor()));
  System.out.println("sensorWarningFontSize: " + m.getSensorWarningFontSize());
  System.out.println("sensorWarningTextYOffset: " + m.getSensorWarningTextYOffset());
  // Display the embedded new line, if it exists
  System.out.println("sensorWarningLowText: \"" + m.getSensorWarningLowText().replace("\n", "\\n") + "\"");
  System.out.println("sensorWarningHighText: \"" + m.getSensorWarningHighText().replace("\n", "\\n") + "\"");
  System.out.println("needleLength: " + m.getNeedleLength());
  System.out.println("needleColor: " + (int)red(m.getNeedleColor()) + ", " + 
      (int)green(m.getNeedleColor()) + ", " + (int)blue(m.getNeedleColor()));
  System.out.println("needleThickness: " + m.getNeedleThickness());
  
}

void draw() {
  int sensorValue = 128;
  delay(700);
  //   sensorValue = (int)random(minIn, maxIn);  // Input for testing
  m.updateMeter(sensorValue); // Update the sensor value to the meter.
  System.out.println("currentMeterValue: " + m.getCurrentMeterValue());
}