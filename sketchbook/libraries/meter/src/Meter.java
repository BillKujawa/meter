package meter;

import java.util.Formatter;

import processing.core.*;

/**
 * Display Arduino or other sensor readings in an analog meter. 
 * Defaults to an analog voltage meter of 0 - 5 volt range. 
 * A sensor reading is mapped to a meter value.
 * Defaults to a half circle meter. 
 * The upper left corner of the meter is specified. The
 * height is calculated as needed. 
 * An optional parameter specifies a full circle meter. 
 * Most of the meter parts can be changed by setting variables,
 * use the GET methods to display default/current value of variables.
 * Note: when changing the width value, do so immediately after 
 * instantiating a meter object. If changing the width in 
 * the Draw() loop, the old meter is not removed.
 * 
 * Some methods will generate non-fatal warning messages depending
 * upon what is changed first.
 * 
 * May be used without microprocessor hardware to display software values.
 * 
 * @author Bill (Papa) Kujawa
 *
 **/
public class Meter {
	PApplet p; // The parent PApplet.

	/**
	 * The upper left hand corner of the meter rectangle.
	 */
	private int meterX;
	/**
	 * The upper left hand corner of the meter rectangle.
	 */
	private int meterY;
	/**
	 * The meter width
	 */
	private int meterWidth;
	/**
	 * The height is defined proportionally to the width + bottom text area
	 */
	private int meterHeight;
	// The default is a half circle.
	private boolean fullCircle;
	// Meter needle pivot point
	private int pivotPointX;
	private int pivotPointY;
	// Flag to redraw everything the first time or when anything changes,
	// else just use images of previously drawn meter parts.
	private boolean meterChanged = true;
	// Flag to control the display of warning messages.
	private boolean displayWarningMessagesToOutput;

	// The meter frame.
	private int frameThickness;
	private int frameColor;
	private int frameStyle;

	// Information area at bottom of meter for displaying sensor values
	private int informationAreaFontSize;
	// Adjust the text spacing from bottom of meter.
	private int informationAreaTextYOffset;
	private String informationAreaFontName;
	private PFont informationAreaFont;
	private int informationAreaFontColor;
	private String informationAreaText;

	// Display the meter values if displayMaximumMeterValue is false.
	private boolean displayDigitalMeterValue;

	// Display a title at the top of the meter.
	private String titleFontName;
	private int titleFontSize;
	private PFont titleFont;
	private int titleFontColor;
	private String title;
	// Adjust title distance from meter Y originNote: change the InformationAreaText after this is selected.
	private int titleYOffset;

	// Define the meter needle pivot point.
	private int pivotPointSize;
	private int pivotPointColor;

	// Define the sensor input values and relate them to the meter scale.
	private int minInputSignal;
	private int maxInputSignal;
	private double arcMinDegrees;
	private double arcMaxDegrees;
	private float minScaleValue;
	private float maxScaleValue;
	
	// Notify the user if the input signal is out of range.
	private boolean enableInputSignalOutOfRange;
	private String inputSignalOutOfRangeFontName;
	private PFont inputSignalOutOfRangeFont;
	private int inputSignalOutOfRangeFontColor;
	private int inputSignalOutOfRangeFontSize;
	private String inputSignalOutOfRangeText;
	private int inputSignalOutOfRangeTextFromPivotPoint;

	// An arc drawn inside scale values
	// Distance set from Pivot Point
	private boolean displayArc;
	private int arcPositionOffset;
	private int arcColor;
	private int arcThickness;

	// The meter scale values.
	private String scaleFontName;
	private PFont scaleFont;
	private int scaleFontColor;
	private int scaleFontSize;
	private int scaleOffsetFromPivotPoint;
	// Set to false to prevent last label overwriting first label
	private boolean displayLastScaleLabel;
	private String[] scaleLabels;

	// The tic marks for indicating the values.
	private int shortTicsBetweenLongTics;
	private int longTicMarkLength;
	private int shortTicMarkLength;
	private int ticMarkOffsetFromPivotPoint;
	private int ticMarkThickness;
	private int ticMarkColor;

	// Map the sensor reading to meter value and needle position.
	// currentMeterValue = newSensorValue to make more intuitive for users.
	private int newSensorReading;
	private float newSensorValue;
	private float newMeterPosition;
	
	// Keep track of the maximum meter value and
	// display it if displayDigitalMeterValue is false.
	private boolean displayMaximumValue;
	private boolean displayMaximumNeedle;
	private float maximumValue;
	private float maximumNeedlePosition;
	private int maximumIgnoreCount;
	private int maximumNeedleLength;
	private int maximumNeedleColor;
	private int maximumNeedleThickness;
	private String maximumValueText;
	
	// Keep track of the minimum meter value and
	// display it if displayDigitalMeterValue is false.
	// minimumValueIgnore used to ignore the initial value.
	private boolean displayMinimumValue;
	private boolean displayMinimumNeedle;
	private float minimumValue;
	private float minimumNeedlePosition;
	private int minimumIgnoreCount;
	private int minimumNeedleLength;
	private int minimumNeedleColor;
	private int minimumNeedleThickness;
	private String minimumValueText;
	
	
	// Set optional warning values
	private boolean lowSensorWarningActive;
	private boolean highSensorWarningActive;
	private float lowSensorWarningValue;
	private float highSensorWarningValue;
	private int lowSensorWarningArcColor;
	private int midSensorWarningArcColor;
	private int highSensorWarningArcColor;
	private String sensorWarningFontName;
	private PFont sensorWarningFont;
	private int lowSensorWarningFontColor;
	private int highSensorWarningFontColor;
	private int sensorWarningFontSize;
	private int sensorWarningTextYOffset;
	private String sensorWarningLowText;
	private String sensorWarningHighText;

	// Needle definitions.
	private int needleLength;
	private int needleColor;
	// Thickness
	private int needleThickness;

	// Width until changed
	private static int DEFAULTWIDTH = 440;
	// Meter frame thickness until changed
	private static int DEFAULTFRAMETHICKNESS = 4;
	// Sets half circle height
	private static float HEIGHTTOWIDTHRATIOHALFCIRCLE = 0.58f;
	// Sets full circle height
	private static float HEIGHTTOWIDTHRATIOFULLCIRCLE = 1.0f;
	// Sets position of pivot point from meter Y
	private static float PIVOTPOINTRATIO = 0.50f;
	// Used to resize everything
	private float scaleFactor = 1.0f;

	PGraphics mFrame;
	PGraphics mTitle;
	PGraphics mPivot;
	PGraphics mArc;
	PGraphics mLabels;
	PGraphics mTics;
	PGraphics mNeedle;

	/**
	 * Constructors
	 * Default settings for a simple voltage meter,
	 * sensor values of 0 - 255 correspond to 0.0 - 5.0 volts
	 * 
	 * @param parent
	 * @param x
	 * @param y
	 */
	public Meter(PApplet parent, int x, int y) {
		// Default is HALFCIRCLE.
		this(parent, x, y, false);
	}

	/**
	 * Declare a full circle meter
	 * @param parent
	 * @param x
	 * @param y
	 * @param fullCircle
	 */
	public Meter(PApplet parent, int x, int y, boolean fullCircle) {
		p = parent;
		meterX = x;
		meterY = y;
		setFullCircle(fullCircle);
		setFrameThickness(DEFAULTFRAMETHICKNESS);
		setMeterWidth(DEFAULTWIDTH);
	}
	
	// Reset the default values when the meterWidth is changed
	// to be able to recalculate using the new scaleFactor.
	// Note: changing the order can cause interesting and unwanted results.
	private void initializeDefaultValues() {
		setDisplayWarningMessagesToOutput(true);
		setFrameColor(p.color(0, 0, 0));
		setFrameStyle(PConstants.BEVEL);

		setInformationAreaFontSize(20);
		setInformationAreaTextYOffset(10);
		setInformationAreaFontName("DejaVu Sans Mono bold");
		setInformationAreaFontColor(p.color(0, 0, 255));
		setDisplayDigitalMeterValue(false);
		setInformationAreaText(" % .2f");

		setTitleFontSize(24);
		setTitleFontName("Liberation Sans Bold");
		setTitleFontColor(p.color(0, 0, 0));
		setTitle("Voltage");
		setTitleYOffset(24);

		setPivotPointSize(10);
		setPivotPointColor(p.color(0, 0, 0));

		setMinInputSignal(0);
		setMaxInputSignal(255);
		
		setEnableInputSignalOutOfRange(true);
		setInputSignalOutOfRangeFontSize(16);
		setInputSignalOutOfRangeFontName("Liberation Sans Bold");
		setInputSignalOutOfRangeFontColor(p.color(255, 0, 0));
		setInputSignalOutOfRangeText("InputSignal\n  Out-Of-Range");
		setInputSignalOutOfRangeTextFromPivotPoint(60);

		setDisplayArc(true);
		setArcPositionOffset(150);
		setArcColor(p.color(0, 0, 0));
		setArcThickness(6);
		setArcMinDegrees(180.0); // PI (left side)
		setArcMaxDegrees(360.0); // TWO_PI (right side)

		setScaleFontSize(16);
		setScaleFontName("DejaVu Sans Bold");
		setScaleFontColor(p.color(0, 0, 0));
		setScaleOffsetFromPivotPoint(170);
		setDisplayLastScaleLabel(true);
		String[] scaleLabels = { "0.0", "1.0", "2.0", "3.0", "4.0", "5.0" };
		setScaleLabels(scaleLabels);
		setMinScaleValue(0.0f);
		setMaxScaleValue(5.0f);

		setLongTicMarkLength(25);
		setShortTicMarkLength(14);
		setShortTicsBetweenLongTics(4);
		setTicMarkOffsetFromPivotPoint(116);
		setTicMarkThickness(2);
		setTicMarkColor(p.color(0, 0, 0));

		setNeedleLength(145);
		setNeedleColor(p.color(255, 0, 0));
		setNeedleThickness(1);
		
		setDisplayMaximumValue(false);
		setDisplayMaximumNeedle(false);
		setMaximumValue(getMinScaleValue());
		setMaximumIgnoreCount(0);
		setMaximumNeedlePosition(PApplet.radians((float) arcMinDegrees));
		setMaximumNeedleLength(135);
		setMaximumNeedleColor(p.color(230, 30, 230));
		setMaximumNeedleThickness(1);
		setMaximumValueText("Max Value: % .2f");
		
		setDisplayMinimumValue(false);
		setDisplayMinimumNeedle(false);
		setMinimumValue(getMaxScaleValue());
		setMinimumIgnoreCount(0);
		setMinimumNeedlePosition(PApplet.radians((float) arcMaxDegrees));
		setMinimumNeedleLength(135);
		setMinimumNeedleColor(p.color(100, 170, 230));
		setMinimumNeedleThickness(1);
		setMinimumValueText("Min Value: % .2f");

		setLowSensorWarningActive(false);
		setHighSensorWarningActive(false);
		// Set default values just outside of scale value range.
		setLowSensorWarningValue(Float.parseFloat(scaleLabels[0]) - (float) 0.1);
		setHighSensorWarningValue(Float.parseFloat(scaleLabels[scaleLabels.length - 1]) + (float) 0.1);
		setSensorWarningFontSize(16);
		setSensorWarningFontName("Arial Black");
		setLowSensorWarningFontColor(p.color(0, 0, 255));
		setHighSensorWarningFontColor(p.color(255, 70, 0));
		setSensorWarningTextYOffset(6);
		setSensorWarningLowText("Warning:\n Low Value");
		setSensorWarningHighText("Warning:    \nHigh Value ");
		setLowSensorWarningArcColor(p.color(0, 0, 255));
		setMidSensorWarningArcColor(p.color(0, 255, 0));
		setHighSensorWarningArcColor(p.color(255, 70, 0));
	}

	/*
	// Process new sensor reading to the meter value
	// Redraw only what is necessary, typically the meter needle
	// and associated changed text or warning messages.
	 */
	public void updateMeter(int newSensorReading) {
		if (newSensorReading < minInputSignal) {
			String errorMessage = "New sensor reading (" + newSensorReading + ") < " + 
					"Min sensor reading (" + minInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		if (newSensorReading > maxInputSignal) {
			String errorMessage = "New sensor reading (" + newSensorReading + ") >" + 
					" Max sensor reading (" + maxInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		this.newSensorReading = newSensorReading;
		updateMeterReading(newSensorReading);
		drawMeterNeedle();

		/*
		// Display previously constructed images
		// if only the sensor value changes (where the meter points)
		// just redraw the things that changed
		 */
		if (meterChanged == true) {
			drawMeterFrame();
			drawMeterTitle();
			drawMeterPivotPoint();
			drawMeterArc();
			drawMeterScaleLabels();
			drawMeterTicMarks();
			meterChanged = false;
		}
		
		p.image(mFrame, 0, 0);
		p.image(mTitle, 0, 0);
		p.image(mPivot, 0, 0);
		// drawMeterArc does additional calculations
		// so we just won't display the actual arc.
		if (displayArc == true) {
			p.image(mArc, 0, 0);
		}
		p.image(mLabels, 0, 0);
		p.image(mTics, 0, 0);
		p.image(mNeedle, 0, 0);
	}
	
	/**
	 * An alternate call to using the six separate calls.
	 * 
	 * @param minInputSignal
	 * @param maxInputSignal
	 * @param minScaleValue
	 * @param maxScaleValue
	 * @param arcMinDegrees
	 * @param arcMaxDegrees
	 */
	public void setUp(int minInputSignal, int maxInputSignal,
		float minScaleValue, float maxScaleValue,
		float arcMinDegrees, float arcMaxDegrees) {
		setMinInputSignal(minInputSignal);
		setMaxInputSignal(maxInputSignal);
		setMinScaleValue(minScaleValue);
		setMaxScaleValue(maxScaleValue);
		setArcMinDegrees(arcMinDegrees);
		setArcMaxDegrees(arcMaxDegrees);
	}

	/**
	 * Enable display of Meter Value at bottom of meter.
	 * Disabled displayMaximumValue and displayMinimumValue
	 * since there is only room for one to be displayed.
	 * 
	 * @param displayMeterValue
	 */
	public void setDisplayDigitalMeterValue(boolean displayMeterValue) {
		displayDigitalMeterValue = displayMeterValue;
		if (displayMeterValue == true) {
			displayMaximumValue = false;
			displayMinimumValue = false;
		}
	}

	public boolean getDisplayDigitalMeterValue() {
		return displayDigitalMeterValue;
	}
	
	
	/**
	 * Enable display of Maximum Meter Value obtained, at bottom of meter.
	 * Disabled displayMaximumValue and displayMinimumValue
	 * since there is only room for one to be displayed.
	 * 
	 * @param displayMaximumValue
	 */
	public void setDisplayMaximumValue(boolean displayMaximumValue) {
		this.displayMaximumValue = displayMaximumValue;
		if (displayMaximumValue == true) {
			displayMinimumValue = false;
			displayDigitalMeterValue = false;				
		}
	}

	public boolean getDisplayMaximumValue() {
		return displayMaximumValue;
	}
	
	/**
	 * Enable the display of the maximum Needle. This is independent of displaying
	 * the maximum value.
	 * 
	 * @param displayNeedle
	 */
	public void setDisplayMaximumNeedle(boolean displayNeedle) {
		displayMaximumNeedle = displayNeedle;
	}
	
	public boolean getDisplayMaximumNeedle() {
		return displayMaximumNeedle;
	}
	
	/**
	 * Used to reset the maximum value or set a minimum value.
	 * 
	 * @param maximumValue
	 */
	public void setMaximumValue(float maximumValue) {
		this.maximumValue = maximumValue;
	}
	
	public float getMaximumValue() {
		return maximumValue;
	}
	
	/**
	 * Ignore the first 1, 10, or 100, etc. maximum values.
	 * Useful if the input to the Meter has to stabilize.
	 * Default is 0.
	 * 
	 * @param count
	 */
	public void setMaximumIgnoreCount(int count) {
		maximumIgnoreCount = count;
	}
	
	public int getMaximumIgnoreCount() {
		return maximumIgnoreCount;
	}
	
	/**
	 * The length of the maximum meter value needle.
	 * 	
	 * @param length
	 */
	public void setMaximumNeedleLength(int length) {
		maximumNeedleLength = scale(length);
	}

	public int getMaximumNeedleLength() {
		return maximumNeedleLength;
	}

	/**
	 * The color of the maximum meter value needle.
	 * 
	 * @param needleColor
	 */
	public void setMaximumNeedleColor(int needleColor) {
		maximumNeedleColor = needleColor;
	}

	public int getMaximumNeedleColor() {
		return maximumNeedleColor;
	}

	/**
	 * The width of the maximum meter value needle.
	 * 
	 * @param thickness
	 */
	public void setMaximumNeedleThickness(int thickness) {
		maximumNeedleThickness = scale(thickness);
	}

	public int getMaximumNeedleThickness() {
		return maximumNeedleThickness;
	}
	
	private void setMaximumNeedlePosition(float position) {
		maximumNeedlePosition = position;
	}
	
	public void setMaximumValueText(String maximumText) {
		maximumValueText = maximumText;
	}
	
	public String getMaximumValueText() {
		return maximumValueText;
	}
	
		
	/**
	 * Enable display of Minimum Meter Value obtained, at bottom of meter.
	 * Disabled displayMaximumValue and displayMinimumValue
	 * since there is only room for one to be displayed.
	 * 
	 * @param displayMinimumValue
	 */
	public void setDisplayMinimumValue(boolean displayMinimumValue) {
		this.displayMinimumValue = displayMinimumValue;
		if (displayMinimumValue == true) {
			displayMaximumValue = false;
			displayDigitalMeterValue = false;
		}
	}

	public boolean getDisplayMinimumValue() {
		return displayMinimumValue;
	}
	
	/**
	 * Enable the display of the minimum Needle. This is independent of displaying
	 * the minimum value.
	 * 
	 * @param displayNeedle
	 */
	public void setDisplayMinimumNeedle(boolean displayNeedle) {
		displayMinimumNeedle = displayNeedle;
	}
	
	public boolean getDisplayMinimumNeedle() {
		return displayMinimumNeedle;
	}
	
	/**
	 * The length of the minimum meter value needle.
	 * 	
	 * @param length
	 */
	public void setMinimumNeedleLength(int length) {
		minimumNeedleLength = scale(length);
	}

	public int getMinimumNeedleLength() {
		return minimumNeedleLength;
	}

	/**
	 * The color of the minimum meter value needle.
	 * 
	 * @param needleColor
	 */
	public void setMinimumNeedleColor(int needleColor) {
		minimumNeedleColor = needleColor;
	}

	public int getMinimumNeedleColor() {
		return minimumNeedleColor;
	}

	/**
	 * The width of the minimum meter value needle.
	 * 
	 * @param thickness
	 */
	public void setMinimumNeedleThickness(int thickness) {
		minimumNeedleThickness = scale(thickness);
	}

	public int getMinimumNeedleThickness() {
		return minimumNeedleThickness;
	}
	
	/**
	 * Used to reset the minimum value or set a maximum value.
	 * 
	 * @param minValue
	 */
	public void setMinimumValue(float minValue) {
		minimumValue = minValue;
	}
	
	public float getMinimumValue() {
		return minimumValue;
	}
	
	private void setMinimumNeedlePosition(float position) {
		minimumNeedlePosition = position;
	}
	
	public void setMinimumValueText(String minimumText) {
		minimumValueText = minimumText;
	}
	
	public String getMinimumValueText() {
		return minimumValueText;
	}
	
	/**
	 * Ignore the first 1, 10, or 100, etc. minimum values.
	 * Useful if the input to the Meter has to stabilize.
	 * Default is 0.
	 * 
	 * @param count
	 */
	public void setMinimumIgnoreCount(int count) {
		minimumIgnoreCount = count;
	}
	
	public int getMinimumIgnoreCount() {
		return minimumIgnoreCount;
	}
	
	
	/**
	 * The sensor meter value 
	 * 
	 * @return currentMeterValue
	 */
	// Internally currentMeterValue = newSensorValue
	public float getCurrentMeterValue() {
		return newSensorValue;
	}
	
	/*
	 * Standardize warning messages.
	 */
	private void displayErrorMessage(String errorMessage) {
		if (displayWarningMessagesToOutput == true) {
		String msg = "Meter error: " + errorMessage;
		System.err.println(msg);
		}
	}
	
	public void setDisplayWarningMessagesToOutput(boolean displayWarningMessages) {
		displayWarningMessagesToOutput = displayWarningMessages; 
	}
	
	public boolean getDisplayWarningMessagesToOutput() {
		return displayWarningMessagesToOutput;
	}


	private int scale(int num) {
		return Math.round((float) num * scaleFactor);
	}

	/**
	 * Allows the vertical adjustment of information text
	 * from the bottom of the meter.
	 * 
	 * @param offset
	 */
	public void setInformationAreaTextYOffset(int offset) {
		informationAreaTextYOffset = offset;
		meterChanged = true;
	}

	public int getInformationAreaTextYOffset() {
		return informationAreaTextYOffset;
	}

	/**
	 * Adjust the information area font size
	 * 
	 * @param fontSize
	 */
	public void setInformationAreaFontSize(int fontSize) {
		informationAreaFontSize = scale(fontSize);
		meterChanged = true;
	}

	public int getInformationAreaFontSize() {
		return informationAreaFontSize;
	}

	/**
	 * Set the information area font
	 * 
	 * @param fontName
	 */
	public void setInformationAreaFontName(String fontName) {
		informationAreaFontName = fontName;
		informationAreaFont = p.createFont(informationAreaFontName, 
				informationAreaFontSize);
		meterChanged = true;
	}

	public String getInformationAreaFontName() {
		return informationAreaFontName;
	}

	/**
	 * Change the information area font color
	 * 
	 * @param fontColor
	 */
	public void setInformationAreaFontColor(int fontColor) {
		informationAreaFontColor = fontColor;
		meterChanged = true;
	}

	public int getInformationAreaFontColor() {
		return informationAreaFontColor;
	}
	
	/**
	 * Text used when displaying digitalMeterValue, maximum or minimum Values.
	 * Default: " % .2f".
	 * Note: see String.Format for java formatting examples.
	 * Example: "Max Value: % .2f".
	 * 
	 * @param infoAreaText
	 */
	public void setInformationAreaText(String infoAreaText) {
		informationAreaText = infoAreaText;
		meterChanged = true;
	}
	
	public String getInformationAreaText() {
		return informationAreaText;
	}

	/**
	 * Activate low sensor warning
	 * Display a message when sensor value is below specified level.
	 * 
	 * @param sensorActive
	 */
	public void setLowSensorWarningActive(boolean sensorActive) {
		lowSensorWarningActive = sensorActive;
		meterChanged = true;
	}

	public boolean getLowSensorWarningActive() {
		return lowSensorWarningActive;
	}

	/**
	 * Activate high sensor warning
	 * Display a message when sensor value is above specified level.
	 * 
	 * @param sensorActive
	 */
	public void setHighSensorWarningActive(boolean sensorActive) {
		highSensorWarningActive = sensorActive;
		meterChanged = true;
	}

	public boolean getHighSensorWarningActive() {
		return highSensorWarningActive;
	}

	public void setLowSensorWarningValue(float sensorValue) {
		if (sensorValue > highSensorWarningValue) {
			String errorMessage = "Low sensor warning value (" + sensorValue + 
					") > High sensor warning value (" + highSensorWarningValue + ")";
			displayErrorMessage(errorMessage);
		}
		lowSensorWarningValue = sensorValue;
		meterChanged = true;
	}

	public float getLowSensorWarningValue() {
		return lowSensorWarningValue;
	}

	public void setHighSensorWarningValue(float sensorValue) {
		if (sensorValue < lowSensorWarningValue) {
			String errorMessage = "High sensor warning value (" + sensorValue + 
					") < Low sensor warning value (" + lowSensorWarningValue + ")";
			displayErrorMessage(errorMessage);
		}
		highSensorWarningValue = sensorValue;
		meterChanged = true;
	}

	public float getHighSensorWarningValue() {
		return highSensorWarningValue;
	}

	/**
	 * The area of the arc below the minimum sensor value.
	 * 
	 * @param lowWarningColor
	 */
	public void setLowSensorWarningArcColor(int lowWarningColor) {
		lowSensorWarningArcColor = lowWarningColor;
		meterChanged = true;
	}

	public int getLowSensorWarningArcColor() {
		return lowSensorWarningArcColor;
	}

	/**
	 * When low or high sensor warnings are enabled
	 * this sets the color of the arc between those settings.
	 * 
	 * @param midWarningColor
	 */
	public void setMidSensorWarningArcColor(int midWarningColor) {
		midSensorWarningArcColor = midWarningColor;
		meterChanged = true;
	}

	public int getMidSensorWarningArcColor() {
		return midSensorWarningArcColor;
	}

	/**
	 * The area of the arc above the maximum sensor value.
	 * 
	 * @param highWarningColor
	 */
	public void setHighSensorWarningArcColor(int highWarningColor) {
		highSensorWarningArcColor = highWarningColor;
		meterChanged = true;
	}

	public int getHighSensorWarningArcColor() {
		return highSensorWarningArcColor;
	}

	/**
	 * Adjust the sensor warning text below the meter Y value
	 * 
	 * @param textYOffset
	 */
	public void setSensorWarningTextYOffset(int textYOffset) {
		sensorWarningTextYOffset = scale(textYOffset);
		meterChanged = true;
	}

	public int getSensorWarningTextYOffset() {
		return sensorWarningTextYOffset;
	}

	public void setSensorWarningFontSize(int fontSize) {
		sensorWarningFontSize = scale(fontSize);
		meterChanged = true;
	}

	public int getSensorWarningFontSize() {
		return sensorWarningFontSize;
	}

	public void setSensorWarningFontName(String fontName) {
		sensorWarningFontName = fontName;
		sensorWarningFont = p.createFont(sensorWarningFontName, sensorWarningFontSize);
		meterChanged = true;
	}

	public String getSensorWarningFontName() {
		return sensorWarningFontName;
	}

	public void setLowSensorWarningFontColor(int fontColor) {
		lowSensorWarningFontColor = fontColor;
		meterChanged = true;
	}

	public int getLowSensorWarningFontColor() {
		return lowSensorWarningFontColor;
	}

	public void setHighSensorWarningFontColor(int fontColor) {
		highSensorWarningFontColor = fontColor;
		meterChanged = true;
	}

	public int getHighSensorWarningFontColor() {
		return highSensorWarningFontColor;
	}

	public void setSensorWarningLowText(String lowWarningText) {
		sensorWarningLowText = lowWarningText;
		meterChanged = true;
	}

	public String getSensorWarningLowText() {
		return sensorWarningLowText;
	}

	public void setSensorWarningHighText(String highWarningText) {
		sensorWarningHighText = highWarningText;
		meterChanged = true;
	}

	public String getSensorWarningHighText() {
		return sensorWarningHighText;
	}

	/*
	 Determine the meter height as a percentage of the width plus frame
	 thickness. This includes an area at the bottom for optional text.
	 Call initializeDefaultValues to reset them and apply the scale
	 factor.
	 Place the pivot point in the horizontal center and just above the
	 bottom for a half circle meter.
	 */
	public void setMeterWidth(int mWidth) {

		meterWidth = mWidth;
		if (fullCircle == true) {
			meterHeight = (int) (mWidth * HEIGHTTOWIDTHRATIOFULLCIRCLE) + 
					frameThickness * 2;
		} else {
			meterHeight = (int) (mWidth * HEIGHTTOWIDTHRATIOHALFCIRCLE) + 
					frameThickness * 2;
		}

		if (mWidth != DEFAULTWIDTH) {
			scaleFactor = (float) mWidth / (float) DEFAULTWIDTH;
		} else {
			scaleFactor = 1.0f;
		}
		initializeDefaultValues();

		// set needle pivot point
		pivotPointX = meterWidth / 2 + meterX;
		pivotPointY = meterY + frameThickness + (int) (mWidth * PIVOTPOINTRATIO);
		meterChanged = true;
	}

	public int getMeterX() {
		return meterX;
	}

	public int getMeterY() {
		return meterY;
	}

	public int getMeterWidth() {
		return meterWidth;
	}

	public int getMeterHeight() {
		return meterHeight;
	}

	// Note: This must be set before calling setMeterWidth
	private void setFullCircle(boolean fullCircle) {
		this.fullCircle = fullCircle;
	}

	public boolean getFullCircle() {
		return fullCircle;
	}

	public void setFrameThickness(int frameThickness) {
		this.frameThickness = scale(frameThickness);
		meterChanged = true;

		// Since meterHeight has already been calculated,
		// it needs to be adjusted to account for the new meterFrameThickness
		if (fullCircle == true) {
			meterHeight = (int) (meterWidth * HEIGHTTOWIDTHRATIOFULLCIRCLE) + 
					frameThickness * 2;
		} else {
			meterHeight = (int) (meterWidth * HEIGHTTOWIDTHRATIOHALFCIRCLE) + 
					frameThickness * 2;
		}
		// Reset needle pivot point
		pivotPointX = meterWidth / 2 + meterX;
		pivotPointY = meterY + frameThickness + (int) (meterWidth * PIVOTPOINTRATIO);		
	}

	public int getFrameThickness() {
		return frameThickness;
	}
	
	/**
	 * The integer frame corner styles can be set to:
	 * PConstants.MITER (8),
	 * PConstants.ROUND (2), or
	 * PConstants.BEVEL (32).
	 */
	public void setFrameStyle(int style) {
		frameStyle = style;
		meterChanged = true;
	}
	
	public int getFrameStyle() {
		return frameStyle;
	}

	public void setFrameColor(int frameColor) {
		this.frameColor = frameColor;
		meterChanged = true;
	}

	public int getFrameColor() {
		return frameColor;
	}

	/*
	 The frame width includes the frame thickness.
	 The center of the frame would extend on either side of
	 the meterX location. Move the frame half a thickness to
	 the right and a full frame thickness to the left to
	 account for both sides.
	 The meter height is determined by adding the frame
	 thicknesses to top and bottom. The space for the 
	 information area is allowed at the bottom when the
	 meter height is calculated.
	 */
	private void drawMeterFrame() {

		mFrame = p.createGraphics(p.width, p.height);
		mFrame.beginDraw();
		mFrame.stroke(frameColor);
		mFrame.strokeWeight(frameThickness);
		mFrame.strokeJoin(frameStyle);
		mFrame.rect(meterX + frameThickness / 2, // Left side
				meterY + frameThickness / 2, // Top
				meterWidth - frameThickness, // Right side
				meterHeight - frameThickness); // Bottom
		mFrame.endDraw();
	}

	public void setTitleFontSize(int fontSize) {
		titleFontSize = scale(fontSize);
		meterChanged = true;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontName(String fontName) {
		titleFontName = fontName;
		titleFont = p.createFont(titleFontName, titleFontSize);
		meterChanged = true;
	}

	public String getTitleFontName() {
		return titleFontName;
	}

	public void setTitleFontColor(int titleColor) {
		titleFontColor = titleColor;
		meterChanged = true;
	}

	public int getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitle(String title) {
		this.title = title;
		meterChanged = true;
	}
	
	public String getTitle() {
		return title;
	}

	/**
	 * Adjusts the position of the title from the
	 * meter Y value.
	 * 
	 * @param YOffset
	 */
	public void setTitleYOffset(int YOffset) {
		titleYOffset = scale(YOffset);
		meterChanged = true;
	}

	public int getTitleYOffset() {
		return titleYOffset;
	}

	// Position the title just below the top of the meter frame
	// and adjust for the text size.
	private void drawMeterTitle() {
		mTitle = p.createGraphics(p.width, p.height);
		mTitle.beginDraw();
		mTitle.textFont(titleFont);
		mTitle.textAlign(PConstants.CENTER);
		mTitle.fill(titleFontColor);
		mTitle.textSize(titleFontSize);
		mTitle.text(title, pivotPointX, meterY +
				frameThickness + titleYOffset);
		mTitle.endDraw();
	}

	/**
	 * Set the diameter of the meter pivot point
	 * 
	 * @param pivotSize
	 */
	public void setPivotPointSize(int pivotSize) {
		pivotPointSize = scale(pivotSize);
		meterChanged = true;
	}

	public int getPivotPointSize() {
		return pivotPointSize;
	}

	public void setPivotPointColor(int pivotColor) {
		pivotPointColor = pivotColor;
		meterChanged = true;
	}

	public int getPivotPointColor() {
		return pivotPointColor;
	}

	public int getPivotPointX() {
		return pivotPointX;
	}

	public int getPivotPointY() {
		return pivotPointY;
	}

	private void drawMeterPivotPoint() {
		mPivot = p.createGraphics(p.width, p.height);
		mPivot.beginDraw();
		mPivot.fill(pivotPointColor);
		mPivot.ellipse(pivotPointX, pivotPointY, pivotPointSize, pivotPointSize);
		mPivot.endDraw();
	}

	/**
	 * Set the width and length of the arc circle
	 * from the needle pivot point.
	 * 
	 * @param offset
	 */
	public void setArcPositionOffset(int offset) {
		// The width and length of the circle
		arcPositionOffset = scale(offset);
		meterChanged = true;
	}

	public int getArcPositionOffset() {
		return arcPositionOffset;
	}

	/**
	 * Set the minimum sensor reading (input) expected.
	 * A warning message is issued if this value is greater
	 * than the maxInputSignal expected.
	 * 
	 * @param minIn
	 */
	public void setMinInputSignal(int minIn) {
		if (minIn > maxInputSignal) {
			String errorMessage = "Min input signal (" + minIn + " > "
					+ "Max input signal (" + maxInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		minInputSignal = minIn;
		meterChanged = true;
	}

	public int getMinInputSignal() {
		return minInputSignal;
	}

	/**
	 * Set the maximum sensor reading (input) expected.
	 * A warning message is issued if this value is less
	 * than the minInputSignal expected.
	 * 
	 * @param maxIn
	 */
	public void setMaxInputSignal(int maxIn) {
		if (maxIn < minInputSignal) {
			String errorMessage = "Max input signal (" + maxIn + 
					") < Min input signal (" + minInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		maxInputSignal = maxIn;
		meterChanged = true;
	}

	public int getMaxInputSignal() {
		return maxInputSignal;
	}
	
	/**
	 * Disable the input signal out-of-range warning.
	 * 
	 * @param enableOutOfRange
	 */
	public void setEnableInputSignalOutOfRange(boolean enableOutOfRange) {
		enableInputSignalOutOfRange = enableOutOfRange;
		meterChanged = true;
	}
	
	public boolean getEnableInputSignalOutOfRange() {
		return enableInputSignalOutOfRange;
	}
	
	public void setInputSignalOutOfRangeFontName(String outOfRangeFontName) {
		inputSignalOutOfRangeFontName = outOfRangeFontName;
		inputSignalOutOfRangeFont = p.createFont(inputSignalOutOfRangeFontName, 
				inputSignalOutOfRangeFontSize);
		meterChanged = true;
	}
	
	public String getInputSignalOutOfRangeFontName() {
		return inputSignalOutOfRangeFontName;
	}
	
	public void setInputSignalOutOfRangeFontColor(int outOfRangeFontColor) {
		inputSignalOutOfRangeFontColor = outOfRangeFontColor;
		meterChanged = true;
	}
	
	public int getInputSignalOutOfRangeFontColor() {
		return inputSignalOutOfRangeFontColor;
	}
	
	public void setInputSignalOutOfRangeFontSize(int outOfRangeFontSize) {
		inputSignalOutOfRangeFontSize = scale(outOfRangeFontSize);
		meterChanged = true;
	}
	
	public int getInputSignalOutOfRangeFontSize() {
		return inputSignalOutOfRangeFontSize;
	}
	
	public void setInputSignalOutOfRangeText(String outOfRangeText) {
		inputSignalOutOfRangeText = outOfRangeText;
		meterChanged = true;
	}
	
	public String getInputSignalOutOfRangeText() {
		return inputSignalOutOfRangeText;
	}
	
	/**
	 * Adjust the position of the text from the pivot point.
	 * 
	 * @param outOfRangeTextOffset
	 */
	public void setInputSignalOutOfRangeTextFromPivotPoint(int outOfRangeTextOffset) {
		inputSignalOutOfRangeTextFromPivotPoint = scale(outOfRangeTextOffset);
		meterChanged = true;
	}
	
	public int getInputSignalOutOfRangeTextFromPivotPoint() {
		return inputSignalOutOfRangeTextFromPivotPoint;
	}

	/**
	 * This is the minimum meter reading or value.
	 * For numeric scale labels, it would be the first value.
	 * This allows for non-numeric labels.
	 * It corresponds to the minInputSignal.
	 */
	public void setMinScaleValue(float minValue) {
		minScaleValue = minValue;
		meterChanged = true;
	}
	
	public float getMinScaleValue() {
		return minScaleValue;
	}
	
	/**
	 * This is the maximum meter reading or value.
	 * For numeric scale labels, it would be the last value.
	 * This allows for non-numeric labels.
	 * It corresponds to the maxInputSignal. As an example,
	 * a 0 - 255 microprocessor input signal is matched to a
	 * 0.0 - 5.0 meter value.
	 */
	public void setMaxScaleValue(float maxValue) {
		maxScaleValue = maxValue;
		meterChanged = true;
	}
	
	public float getMaxScaleValue() {
		return maxScaleValue;
	}

	/**
	// The arc color when no low or high warnings are enabled
	// Note: will be changed to the midSensorWarningArcColor.
	 */
	public void setArcColor(int aColor) {
		arcColor = aColor;
		meterChanged = true;
	}

	public int getArcColor() {
		return arcColor;
	}
	
	/**
	 * Allows the removal of the arc from the meter.
	 * Note: the arc would be the mirror on a real analog meter.
	 * It was used to align the needle with your eye to
	 * ensure a correct reading.
	 * 
	 * @param displayArc
	 */
	public void setDisplayArc(boolean displayArc) {
		this.displayArc = displayArc;
		meterChanged = true;
	}
	
	public boolean getDisplayArc() {
		return displayArc;
	}

	public void setArcThickness(int thick) {
		arcThickness = scale(thick);
		meterChanged = true;
	}

	public int getArcThickness() {
		return arcThickness;
	}

	/**
	 * Determines where the meter arc starts.
	 * 0.0 degrees is at 3:00 o'clock and moves clockwise
	 * The default arc is 180.0 degrees at 9:00 o'clock
	 * to 360.0 degrees at 3:00 o'clock.
	 * Note: use a full circle meter for arc starts less
	 * than 180.0 degrees.
	 * 
	 * @param minDegrees
	 */
	public void setArcMinDegrees(double minDegrees) {
		if ((minDegrees > arcMaxDegrees) && (arcMaxDegrees > 0)) {
			String errorMessage = "Arc Min Degrees (" + minDegrees + 
					") > " + "Arc Max Degrees (" + arcMaxDegrees + ")";
			displayErrorMessage(errorMessage);
		}
		arcMinDegrees = minDegrees;
		meterChanged = true;
	}

	public double getArcMinDegrees() {
		return arcMinDegrees;
	}

	/**
	 * Determines where the meter arc ends.
	 * 360.0 degrees is at 3:00 o'clock. Use a full circle meter
	 * if using a larger value.
	 * 
	 * @param maxDegrees
	 */
	public void setArcMaxDegrees(double maxDegrees) {
		if (maxDegrees < arcMinDegrees) {
			String errorMessage = "Arc Max Degrees (" + maxDegrees + 
					") < " + "Arc Min Degrees (" + arcMinDegrees + ")";
			displayErrorMessage(errorMessage);
		}
		arcMaxDegrees = maxDegrees;
		meterChanged = true;
	}

	public double getArcMaxDegrees() {
		return arcMaxDegrees;
	}

	// Draw the arc with appropriate colors
	private void drawMeterArc() {
		mArc = p.createGraphics(p.width, p.height);
		mArc.beginDraw();
		mArc.strokeWeight(arcThickness);
		mArc.stroke(arcColor);
		mArc.fill(255, 0);
		if (lowSensorWarningActive == true || highSensorWarningActive == true) {
			float lowWarningValue;
			if (lowSensorWarningValue > minScaleValue) {
				lowWarningValue = lowSensorWarningValue;
			} else {
				lowWarningValue = minScaleValue;
			}
			float lowWarningPosition = PApplet.map(lowWarningValue, minScaleValue, maxScaleValue,
					PApplet.radians((float) arcMinDegrees), 
					PApplet.radians((float) arcMaxDegrees));
			mArc.stroke(lowSensorWarningArcColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2,
					PApplet.radians((float) arcMinDegrees), lowWarningPosition, PConstants.OPEN);
			float highWarningValue;
			if (highSensorWarningValue < maxScaleValue) {
				highWarningValue = highSensorWarningValue;
			} else {
				highWarningValue = maxScaleValue;
			}
			float highWarningPosition = PApplet.map(highWarningValue, minScaleValue, maxScaleValue,
					PApplet.radians((float) arcMinDegrees), 
					PApplet.radians((float) arcMaxDegrees));
			mArc.stroke(midSensorWarningArcColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2, 
					lowWarningPosition, highWarningPosition, PConstants.OPEN);
			mArc.stroke(highSensorWarningArcColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2, 
					highWarningPosition, PApplet.radians((float) arcMaxDegrees), PConstants.OPEN);
		} else {
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2,
					PApplet.radians((float) arcMinDegrees), 
					PApplet.radians((float) arcMaxDegrees), 
					PConstants.OPEN);
		}
		mArc.endDraw();
	}

	public void setScaleFontSize(int scaleSize) {
		scaleFontSize = scale(scaleSize);
		meterChanged = true;
	}

	public int getScaleFontSize() {
		return scaleFontSize;
	}

	public void setScaleFontName(String sFont) {
		scaleFontName = sFont;
		scaleFont = p.createFont(scaleFontName, scaleFontSize);
		meterChanged = true;
	}

	public String getScaleFontName() {
		return scaleFontName;
	}

	public void setScaleFontColor(int sColor) {
		scaleFontColor = sColor;
		meterChanged = true;
	}

	public int getScaleFontColor() {
		return scaleFontColor;
	}

	/**
	 * Each label will coincide with a long tic mark.
	 * The minimumMeterValue and maximumMeterValue are
	 * used to calculate the tic spacing. This allows for
	 * non-numeric scale labels.
	 * Note: for a full circle meter with 360 degree labels,
	 * include the last label even if it would overlap the
	 * first label. In that case set displayLastScaleLabel
	 * to false. The number of labels determines the
	 * correct spacing.
	 * 
	 * @param labels
	 */
	public void setScaleLabels(String[] labels) {
		scaleLabels = labels;
		meterChanged = true;
	}

	public String[] getScaleLabels() {
		return scaleLabels;
	}

	/**
	 * The arc length of the scale labels from the pivot point.
	 * 
	 * @param scaleOffset
	 */
	public void setScaleOffsetFromPivotPoint(int scaleOffset) {
		scaleOffsetFromPivotPoint = scale(scaleOffset);
		meterChanged = true;
	}

	public int getScaleOffsetFromPivotPoint() {
		return scaleOffsetFromPivotPoint;
	}

	/**
	 * Disable the display of the last scale label for a full circle
	 * meter. This prevents the last label from overwriting the first one.
	 * 
	 * @param displayLastLabel
	 */
	public void setDisplayLastScaleLabel(boolean displayLastLabel) {
		displayLastScaleLabel = displayLastLabel;
		meterChanged = true;
	}

	public boolean getDisplayLastScaleLabel() {
		return displayLastScaleLabel;
	}

	/*
	// Draw the labels and try to position them correctly.
	// Note: long labels would require the adjustment of the arc to
	// provide extra spacing.
	 */
	private void drawMeterScaleLabels() {
		float labelX;
		float labelY;
		int labelCount = scaleLabels.length;
		double currentTicRadians = PApplet.radians((float) arcMinDegrees);
		double ticSeparation = (PApplet.radians((float) arcMaxDegrees) - 
				PApplet.radians((float) arcMinDegrees))
				/ (labelCount - 1);
		mLabels = p.createGraphics(p.width, p.height);
		mLabels.beginDraw();
		mLabels.textFont(scaleFont);
		mLabels.textSize(scaleFontSize);
		mLabels.fill(scaleFontColor);
		mLabels.textAlign(PConstants.CENTER);
		for (int i = 0; i < labelCount; i++) {
			if (displayLastScaleLabel == false && i == labelCount - 1) {
				continue;
			}
			labelX = pivotPointX + (PApplet.cos((float) currentTicRadians) * 
					scaleOffsetFromPivotPoint);
			labelY = pivotPointY + PApplet.sin((float) currentTicRadians) * 
					scaleOffsetFromPivotPoint;
	// The following was removed because " textFont(A font used outside of Meter)
	// causes the textAscent and text Descent to use the outside font and not
	// the Meter font. ??? Why ???
	// How to correctly position the scale labels around the arc to be a
	// uniform distance away from the arc??????
	//				(p.textAscent() + p.textDescent()) / 2;
			mLabels.text(scaleLabels[i], labelX, labelY);
			currentTicRadians += ticSeparation;
		}
		mLabels.endDraw();
	}

	public void setLongTicMarkLength(int longTicLength) {
		if (longTicLength < shortTicMarkLength) {
			String errorMessage = "Long tic mark length )" + longTicLength + 
					" < Short tic mark length (" +
					shortTicMarkLength + ")";
			displayErrorMessage(errorMessage);
		}
		longTicMarkLength = scale(longTicLength);
		meterChanged = true;
	}

	public int getLongTicMarkLength() {
		return longTicMarkLength;
	}

	public void setShortTicMarkLength(int shortTicLength) {
		if (shortTicLength > longTicMarkLength) {
			String errorMessage = "Short tic mark length (" + shortTicLength + 
					") > Long tic mark length ("
					+ longTicMarkLength + ")";
			displayErrorMessage(errorMessage);
		}
		shortTicMarkLength = scale(shortTicLength);
		meterChanged = true;
	}

	public int getShortTicMarkLength() {
		return shortTicMarkLength;
	}

	/**
	 * Sets the number of short tic marks between
	 * the long ones.
	 * 
	 * @param numShortTics
	 */
	public void setShortTicsBetweenLongTics(int numShortTics) {
		shortTicsBetweenLongTics = numShortTics;
		meterChanged = true;
	}

	public int getShortTicsBetweenLongTics() {
		return shortTicsBetweenLongTics;
	}

	public void setTicMarkThickness(int ticMarkThickness) {
		this.ticMarkThickness = scale(ticMarkThickness);
		meterChanged = true;
	}

	public int getTicMarkThickness() {
		return ticMarkThickness;
	}

	/**
	 * Where to start drawing the tic marks. Their length
	 * determines how close to the meter arc they are drawn.
	 * 
	 * @param ticOffset
	 */
	public void setTicMarkOffsetFromPivotPoint(int ticOffset) {
		ticMarkOffsetFromPivotPoint = scale(ticOffset);
		meterChanged = true;
	}

	public int getTicMarkOffsetFromPivotPoint() {
		return ticMarkOffsetFromPivotPoint;
	}

	public void setTicMarkColor(int ticColor) {
		ticMarkColor = ticColor;
		meterChanged = true;
	}

	public int getTicMarkColor() {
		return ticMarkColor;
	}

	private void drawMeterTicMarks() {
		double ticX1, ticX2;
		double ticY1, ticY2;
		int ticLength;
		int longTicCount = scaleLabels.length;
		int totalTicCount = longTicCount + ((longTicCount - 1) * shortTicsBetweenLongTics);
		double ticSeparation = (PApplet.radians((float) arcMaxDegrees) - 
				PApplet.radians((float) arcMinDegrees)) / (totalTicCount - 1);
		// Start drawing from the left side.
		double currentTicRadians = PApplet.radians((float) arcMinDegrees);
		int ticCount = 1;
		mTics = p.createGraphics(p.width, p.height);
		mTics.beginDraw();
		mTics.strokeWeight(ticMarkThickness);
		mTics.stroke(ticMarkColor);

		for (int i = 1; i <= totalTicCount; i++) {
			if (ticCount == 1) {
				ticLength = longTicMarkLength;
			} else {
				ticLength = shortTicMarkLength;
			}
			ticX1 = pivotPointX +
					(PApplet.cos((float) currentTicRadians) * ticMarkOffsetFromPivotPoint);
			ticX2 = pivotPointX + (PApplet.cos((float) currentTicRadians) *
					(ticMarkOffsetFromPivotPoint + ticLength));
			ticY1 = pivotPointY +
					PApplet.sin((float) currentTicRadians) * ticMarkOffsetFromPivotPoint;
			ticY2 = pivotPointY + PApplet.sin((float) currentTicRadians) *
					(ticMarkOffsetFromPivotPoint + ticLength);
			
			mTics.line((float) ticX1, (float) ticY1, (float) ticX2, (float) ticY2);
			if (ticCount > shortTicsBetweenLongTics) {
				ticCount = 1;
			} else {
				ticCount += 1;
			}
			currentTicRadians += ticSeparation;
		}
		mTics.endDraw();
	}

	/**
	 * Determine meter value (needle position) relative to the sensor reading
	 * Update the maximumMeterValue.
	 * 
	 * @param newSensorReading
	 */
	private void updateMeterReading(int newSensorReading) {
		// Determine needle position relative to meter scale
		// Use the first and last values from the scaleLabels array
		newSensorValue = PApplet.map((float) newSensorReading, (float) minInputSignal, 
				(float) maxInputSignal, minScaleValue, maxScaleValue);
		newMeterPosition = PApplet.map(newSensorValue, minScaleValue, maxScaleValue, 
				PApplet.radians((float) arcMinDegrees), 
				PApplet.radians((float) arcMaxDegrees));
		
		if (newSensorValue > maximumValue) {
			if (maximumIgnoreCount <= 0) {
				setMaximumValue(newSensorValue);
				setMaximumNeedlePosition(newMeterPosition);
			}
			else {
				maximumIgnoreCount--;
			}
		}
		if (newSensorValue < minimumValue) {
			if (minimumIgnoreCount <= 0) {
				setMinimumValue(newSensorValue);
				setMinimumNeedlePosition(newMeterPosition);
			}
			else {
				minimumIgnoreCount--;
			}
		}
	}


	public void setNeedleLength(int length) {
		needleLength = scale(length);
	}

	public int getNeedleLength() {
		return needleLength;
	}

	public void setNeedleColor(int needleColor) {
		this.needleColor = needleColor;
	}

	public int getNeedleColor() {
		return needleColor;
	}

	/**
	 * The width of the needle
	 * 
	 * @param thickness
	 */
	public void setNeedleThickness(int thickness) {
		needleThickness = scale(thickness);
	}

	public int getNeedleThickness() {
		return needleThickness;
	}
	

	// Draw the needle at its new position.
	// Draw the maximum needle at its current or new position.
	// Display sensor values if enabled.
	// Display maximum meter value if enabled.
	// Display minimum meter value if enabled.
	// Display low or high sensor warning messages if enabled.
	// Display warning message if input signal is out-of-range.
	private void drawMeterNeedle() {
		float needleX = pivotPointX + (PApplet.cos(newMeterPosition) * needleLength);
		float needleY = pivotPointY + PApplet.sin(newMeterPosition) * needleLength;
		Formatter fmt = new Formatter();
		mNeedle = p.createGraphics(p.width, p.height);
		mNeedle.beginDraw();
		mNeedle.stroke(needleColor);
		mNeedle.strokeWeight(needleThickness);
		mNeedle.line(pivotPointX, pivotPointY, needleX, needleY);
		
		// Draw maximum meter value needle if enabled
		if (displayMaximumNeedle == true) {
			needleX = pivotPointX + (PApplet.cos(maximumNeedlePosition) * maximumNeedleLength);
			needleY = pivotPointY + PApplet.sin(maximumNeedlePosition) * maximumNeedleLength;
			mNeedle.stroke(maximumNeedleColor);
			mNeedle.strokeWeight(maximumNeedleThickness);
			mNeedle.line(pivotPointX, pivotPointY, needleX, needleY);
		}
		// Draw minimum meter value needle if enabled
		if (displayMinimumNeedle == true) {
			needleX = pivotPointX + (PApplet.cos(minimumNeedlePosition) * minimumNeedleLength);
			needleY = pivotPointY + PApplet.sin(minimumNeedlePosition) * minimumNeedleLength;
			mNeedle.stroke(minimumNeedleColor);
			mNeedle.strokeWeight(minimumNeedleThickness);
			mNeedle.line(pivotPointX, pivotPointY, needleX, needleY);
		}
		if (displayDigitalMeterValue == true) {
			mNeedle.textFont(informationAreaFont);
			mNeedle.fill(informationAreaFontColor);
			mNeedle.textAlign(PConstants.CENTER);
			mNeedle.textSize(informationAreaFontSize);
			fmt.format(informationAreaText, newSensorValue);
			mNeedle.text(fmt.toString(), meterX + (meterWidth / 2), 
					meterY + meterHeight - informationAreaTextYOffset);
		}
		if (displayMaximumValue == true) {
			mNeedle.textFont(informationAreaFont);
			mNeedle.fill(maximumNeedleColor);
			mNeedle.textAlign(PConstants.CENTER);
			mNeedle.textSize(informationAreaFontSize);
			fmt.format(maximumValueText, maximumValue);
			mNeedle.text(fmt.toString(), meterX + (meterWidth / 2), 
					meterY + meterHeight - informationAreaTextYOffset);
		}
		if (displayMinimumValue == true) {
			mNeedle.textFont(informationAreaFont);
			mNeedle.fill(minimumNeedleColor);
			mNeedle.textAlign(PConstants.CENTER);
			mNeedle.textSize(informationAreaFontSize);
			fmt.format(minimumValueText, minimumValue);
			mNeedle.text(fmt.toString(), meterX + (meterWidth / 2), 
					meterY + meterHeight - informationAreaTextYOffset);
		}
		if (lowSensorWarningActive == true) {
			if (lowSensorWarningValue >= newSensorValue) {
				mNeedle.textFont(sensorWarningFont);
				mNeedle.fill(lowSensorWarningFontColor);
				mNeedle.textSize(sensorWarningFontSize);
				mNeedle.textAlign(PConstants.LEFT);
				mNeedle.text("  " + sensorWarningLowText, meterX + frameThickness,
						meterY + p.textAscent() + frameThickness + 
						sensorWarningTextYOffset);
			}
		}
		if (highSensorWarningActive == true) {
			if (highSensorWarningValue <= newSensorValue) {
				mNeedle.textFont(sensorWarningFont);
				mNeedle.fill(highSensorWarningFontColor);
				mNeedle.textSize(sensorWarningFontSize);
				mNeedle.textAlign(PConstants.RIGHT);
				mNeedle.text(sensorWarningHighText + "  ", meterX + meterWidth - frameThickness,
						meterY + p.textAscent() + frameThickness + 
						sensorWarningTextYOffset);
			}
		}
		if (enableInputSignalOutOfRange == true) {
			if (newSensorReading < minInputSignal || newSensorReading > maxInputSignal) {
				mNeedle.textFont(inputSignalOutOfRangeFont);
				mNeedle.fill(inputSignalOutOfRangeFontColor);
				mNeedle.textSize(inputSignalOutOfRangeFontSize);
				mNeedle.textAlign(PConstants.CENTER);
				mNeedle.text(inputSignalOutOfRangeText + "  ", meterX + (meterWidth / 2),
					pivotPointY - inputSignalOutOfRangeTextFromPivotPoint);
			}
		}
		mNeedle.endDraw();
		// Return resources
		fmt.close();
	}

}
