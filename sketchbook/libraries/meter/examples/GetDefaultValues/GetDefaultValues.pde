/* //<>//
 Default meter example. No hardware.
 Get and print all default values.
 Change the input value once to demonstrate the
 difference between currentMeterValue and maximumMeterValue.
 */
import meter.*;

Meter m;

void setup() {
  size(600, 500);
  background(255, 255, 200);

  m = new Meter(this, 50, 25);
  m.updateMeter(200); // Display meter with zero input sensor value.

  noLoop();

  System.out.println("meterX: " + m.getMeterX());
  System.out.println("meterY: " + m.getMeterY());
  System.out.println("meterWidth: " + m.getMeterWidth());
  System.out.println("meterHeight: " + m.getMeterHeight());
  System.out.println("fullCircle: " + m.getFullCircle());
  System.out.println("frameThickness: " + m.getFrameThickness());
  System.out.println("frameColor: " + (int)red(m.getFrameColor()) + ", " + 
    (int)green(m.getFrameColor()) + ", " + (int)blue(m.getFrameColor()));
  System.out.println("meterFrameStyle: " + m.getFrameStyle());
  System.out.println("informationAreaFontSize: " + m.getInformationAreaFontSize());
  System.out.println("informationAreaTextYOffset: " + m.getInformationAreaTextYOffset());
  System.out.println("informationAreaFontName: " + m.getInformationAreaFontName());
  System.out.println("informationAreaFontColor: " + (int)red(m.getInformationAreaFontColor()) + ", " + 
    (int)green(m.getInformationAreaFontColor()) + ", " + (int)blue(m.getInformationAreaFontColor()));
  System.out.println("informationAreaText: \"" + m.getInformationAreaText() + "\"");
  System.out.println("displayDigitalMeterValue: " + m.getDisplayDigitalMeterValue());
  System.out.println("currentMeterValue: " + m.getCurrentMeterValue());
  System.out.println("title: \"" + m.getTitle() + "\"");
  System.out.println("titleFontName: " + m.getTitleFontName());
  System.out.println("titleFontSize: " + m.getTitleFontSize());
  System.out.println("titleFontColor: " + (int)red(m.getTitleFontColor()) + ", " + 
    (int)green(m.getTitleFontColor()) + ", " + (int)blue(m.getTitleFontColor()));
  System.out.println("titleYOffset: " + m.getTitleYOffset());
  System.out.println("pivotPointX: " + m.getPivotPointX());
  System.out.println("pivotPointY: " + m.getPivotPointY());
  System.out.println("pivotPointSize: " + m.getPivotPointSize());
  System.out.println("pivotPointColor: " + (int)red(m.getPivotPointColor()) + ", " + 
    (int)green(m.getPivotPointColor()) + ", " + (int)blue(m.getPivotPointColor()));
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
  System.out.println("scaleFontName: " + m.getScaleFontName());
  System.out.println("scaleFontColor: " + (int)red(m.getScaleFontColor()) + ", " + 
    (int)green(m.getScaleFontColor()) + ", " + (int)blue(m.getScaleFontColor()));
  System.out.println("scaleFontSize: " + m.getScaleFontSize());
  System.out.println("scaleOffsetFromPivotPoint: " + m.getScaleOffsetFromPivotPoint());
  System.out.println("displayLastScaleLabel: " + m.getDisplayLastScaleLabel());
  // Display the String array as a string
  System.out.println("scaleLabels: \"" + join(m.getScaleLabels(), "\", \"") + "\"");
  System.out.println("minScaleValue: " + m.getMinScaleValue());
  System.out.println("maxScaleValue: " + m.getMaxScaleValue());
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
  System.out.println("displayWarningMessagesToOutput: " + m.getDisplayWarningMessagesToOutput());
  System.out.println("enableInputSignalOutOfRange: " + m.getEnableInputSignalOutOfRange());
  System.out.println("inputSignalOutOfRangeFontColor: " + (int)red(m.getInputSignalOutOfRangeFontColor()) + ", " + 
    (int)green(m.getInputSignalOutOfRangeFontColor()) + ", " + (int)blue(m.getInputSignalOutOfRangeFontColor()));
  System.out.println("inputSignalOutOfRangeFontName: " + m.getInputSignalOutOfRangeFontName());
  System.out.println("inputSignalOutOfRangeFontSize: " + m.getInputSignalOutOfRangeFontSize());
  System.out.println("inputSignalOutOfRangeText: \"" + m.getInputSignalOutOfRangeText().replace("\n", "\\n") + "\"");
  System.out.println("inputSignalOutOfRangeTextFromPivotPoint: " + m.getInputSignalOutOfRangeTextFromPivotPoint());
  System.out.println("displayMaximumValue: " + m.getDisplayMaximumValue());
  System.out.println("displayMinimumValue: " + m.getDisplayMinimumValue());
  System.out.println("maximumNeedleColor: " + (int)red(m.getMaximumNeedleColor()) + ", " + 
    (int)green(m.getMaximumNeedleColor()) + ", " + (int)blue(m.getMaximumNeedleColor()));
  System.out.println("mainmumNeedleColor: " + (int)red(m.getMinimumNeedleColor()) + ", " + 
    (int)green(m.getMinimumNeedleColor()) + ", " + (int)blue(m.getMinimumNeedleColor()));
  System.out.println("maximumNeedleLength: " + m.getMaximumNeedleLength());
  System.out.println("minimumNeedleLength: " + m.getMinimumNeedleLength());
  System.out.println("maximumNeedleThickness: " + m.getMaximumNeedleThickness());
  System.out.println("minimumNeedleThickness: " + m.getMinimumNeedleThickness());
  System.out.println("displayMaximumNeedle: " + m.getDisplayMaximumNeedle());
  System.out.println("displayMinimumNeedle: " + m.getDisplayMinimumNeedle());
 
  System.out.println("maximumIgnoreCount: " + m.getMaximumIgnoreCount());
  System.out.println("minimumIgnoreCount: " + m.getMinimumIgnoreCount());
}

void draw() {
  int sensorValue = 128;
  delay(700); 
  //   sensorValue = (int)random(minIn, maxIn);  // Input for testing
  m.updateMeter(sensorValue); // Update the sensor value to the meter.
  System.out.println("currentMeterValue: " + m.getCurrentMeterValue());
  System.out.println("maximumValue: " + m.getMaximumValue());
  System.out.println("minimumValue: " + m.getMinimumValue());
}