package meter;

import java.util.Arrays;

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
 * Some methods will generate non-fatal warning messages.
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

	// The meter frame.
	private int meterFrameThickness;
	private int meterFrameColor;

	// Information area at bottom of meter for displaying sensor values
	private int informationAreaFontSize;
	// Adjust the text spacing from bottom of meter.
	private int informationAreaTextYOffset;
	private String informationAreaFontName;
	private PFont informationAreaFont;
	private int informationAreaFontColor;
	private boolean displayDigitalMeterValue;

	// Display a title at the top of the meter.
	private String meterTitleFontName;
	private int meterTitleFontSize;
	private PFont meterTitleFont;
	private int meterTitleFontColor;
	private String meterTitle;
	// Adjust title distance from meter Y origin
	private int meterTitleYOffset;

	// Define the meter needle pivot point.
	private int meterPivotPointSize;
	private int meterPivotPointColor;

	// Define the sensor input values and relate them to the meter scale.
	private int minInputSignal;
	private int maxInputSignal;
	private double arcMinDegrees;
	private double arcMaxDegrees;
	private int shortTicsBetweenLongTics;

	// An arc drawn inside scale values
	// Distance set from Pivot Point
	private boolean displayArc;
	private int arcPositionOffset;
	private int arcColor;
	private int arcThickness;

	// The meter scale values.
	private String meterScaleFontName;
	private PFont meterScaleFont;
	private int meterScaleFontColor;
	private int meterScaleFontSize;
	private int meterScaleOffsetFromPivotPoint;
	// Set to false to prevent last label overwriting first label
	private boolean displayLastScaleLabel;
	private String[] scaleLabels;
	// For displaying non-numeric scale labels
	private boolean displayAlternateScaleLabels;
	private String[] alternateScaleLabels;

	// The tic marks for indicating the values.
	private int longTicMarkLength;
	private int shortTicMarkLength;
	private int ticMarkSetbackFromArc;
	private int ticMarkOffsetFromPivotPoint;
	private int ticMarkThickness;
	private int ticMarkColor;

	// Map the sensor reading to meter value and needle position.
	private int newSensorReading;
	private float newSensorValue;
	private float newMeterPosition;

	// Set optional warning values
	private boolean lowSensorWarningActive;
	private boolean highSensorWarningActive;
	private float lowSensorWarningValue;
	private float highSensorWarningValue;
	private int lowSensorWarningColor;
	private int midSensorWarningColor;
	private int highSensorWarningColor;
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
	private int needleSize;

	// Width until changed
	private static int DEFAULTWIDTH = 440;
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
		setMeterFrameThickness(4);
		setMeterWidth(DEFAULTWIDTH);
	}
	
	// Reset the default values when the meterWidth is changed
	// to be able to recalculate using the new scaleFactor.
	// Note: changing the order can cause interesting and unwanted results.
	private void initializeDefaultValues() {
		setMeterFrameColor(p.color(0, 0, 0));

		setInformationAreaFontSize(20);
		setInformationAreaTextYOffset(10);
		setInformationAreaFontName("DejaVu Sans Mono bold");
		setInformationAreaFontColor(p.color(0, 0, 255));
		setDisplayDigitalMeterValue(false);

		setMeterTitleFontSize(24);
		setMeterTitleFontName("Liberation Sans Bold");
		setMeterTitleFontColor(p.color(0, 0, 0));
		setMeterTitle("Voltage");
		setMeterTitleYOffset(12);

		setMeterPivotPointSize(10);
		setMeterPivotPointColor(p.color(0, 0, 0));

		setMinInputSignal(0);
		setMaxInputSignal(255);

		setDisplayArc(true);
		setArcPositionOffset(150);
		setArcColor(p.color(0, 0, 0));
		setArcThickness(6);
		setArcMinDegrees(180.0); // PI (left side)
		setArcMaxDegrees(360.0); // TWO_PI (right side)

		setMeterScaleFontSize(16);
		setMeterScaleFontName("DejaVu Sans Bold");
		setMeterScaleFontColor(p.color(0, 0, 0));
		setMeterScaleOffsetFromPivotPoint(170);
		setDisplayLastScaleLabel(true);
		String[] scaleLabels = { "0.0", "1.0", "2.0", "3.0", "4.0", "5.0" };
		setScaleLabels(scaleLabels);
		setDisplayAlternateScaleLabels(false);
		String[] alternateScaleLabels = null;

		setLongTicMarkLength(25);
		setShortTicMarkLength(14);
		setShortTicsBetweenLongTics(4);
		setTicMarkSetbackFromArc(30);
		setTicMarkOffsetFromPivotPoint(146);
		setTicMarkThickness(2);
		setTicMarkColor(p.color(0, 0, 0));

		setNeedleLength(145);
		setNeedleColor(p.color(255, 0, 0));
		// Set the needle width
		setNeedleSize(1);

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
		setLowSensorWarningColor(p.color(0, 0, 255));
		setMidSensorWarningColor(p.color(0, 255, 0));
		setHighSensorWarningColor(p.color(255, 70, 0));
	}

	// Process new sensor reading to the meter value
	// Redraw only what is necessary, typically the meter needle
	// and associated changed text or warning messages.
	public void updateMeter(int newSensorReading) {
		if (newSensorReading < minInputSignal) {
			String errorMessage = "New sensor reading (" + newSensorReading + ") < Min sensor reading ("
					+ minInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		if (newSensorReading > maxInputSignal) {
			String errorMessage = "New sensor reading (" + newSensorReading + ") > Max sensor reading ("
					+ maxInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		this.newSensorReading = newSensorReading;
		updateMeterReading(newSensorReading);
		drawMeterNeedle();

		// Display previously constructed images
		// if only the sensor value changes (where the meter points)
		// just redraw the things that changed
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
	 * Enable display of Meter Value at bottom of meter.
	 * 
	 * @param displayMeterValue
	 */
	public void setDisplayDigitalMeterValue(boolean displayMeterValue) {
		displayDigitalMeterValue = displayMeterValue;
		meterChanged = true;
	}

	public boolean getDisplayDigitalMeterValue() {
		return displayDigitalMeterValue;
	}
	
	/**
	 * The sensor meter value 
	 * 
	 * @return newSensorValue
	 */
	public float getNewSensorValue() {
		return newSensorValue;
	}
	
	/*
	 * Standardize warning messages.
	 */
	private void displayErrorMessage(String errorMessage) {
		String msg = "Meter error: " + errorMessage;
		System.err.println(msg);
	}

	/*
	 * Calculate values based upon current scale factor.
	 */
	private float scale(float num) {
		return num * scaleFactor;
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
		informationAreaFont = p.createFont(informationAreaFontName, informationAreaFontSize);
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
	 * Activate low sensor warning
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
			String errorMessage = "Low sensor warning value (" + sensorValue + ") > High sensor warning value ("
					+ highSensorWarningValue + ")";
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
			String errorMessage = "High sensor warning value (" + sensorValue + ") < Low sensor warning value ("
					+ lowSensorWarningValue + ")";
			displayErrorMessage(errorMessage);
		}
		highSensorWarningValue = sensorValue;
		meterChanged = true;
	}

	public float getHighSensorWarningValue() {
		return highSensorWarningValue;
	}

	public void setLowSensorWarningColor(int lowWarningColor) {
		lowSensorWarningColor = lowWarningColor;
		meterChanged = true;
	}

	public int getLowSensorWarningColor() {
		return lowSensorWarningColor;
	}

	/**
	 * When low or high sensor warnings are enabled
	 * this sets the color of the arc between those settings.
	 * 
	 * @param midWarningColor
	 */
	public void setMidSensorWarningColor(int midWarningColor) {
		midSensorWarningColor = midWarningColor;
		meterChanged = true;
	}

	public int getMidSensorWarningColor() {
		return midSensorWarningColor;
	}

	public void setHighSensorWarningColor(int highWarningColor) {
		highSensorWarningColor = highWarningColor;
		meterChanged = true;
	}

	public int getHighSensorWarningColor() {
		return highSensorWarningColor;
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
	 bottom.
	 */
	public void setMeterWidth(int mWidth) {

		meterWidth = mWidth;
		if (fullCircle == true) {
			meterHeight = (int) (mWidth * HEIGHTTOWIDTHRATIOFULLCIRCLE) + meterFrameThickness * 2;
		} else {
			meterHeight = (int) (mWidth * HEIGHTTOWIDTHRATIOHALFCIRCLE) + meterFrameThickness * 2;
		}

		if (mWidth != DEFAULTWIDTH) {
			scaleFactor = (float) mWidth / (float) DEFAULTWIDTH;
		} else {
			scaleFactor = 1.0f;
		}
		initializeDefaultValues();

		// set needle pivot point
		pivotPointX = meterWidth / 2 + meterX;
		pivotPointY = meterY + meterFrameThickness + (int) (mWidth * PIVOTPOINTRATIO);
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

	public void setMeterFrameThickness(int frameThickness) {
		meterFrameThickness = scale(frameThickness);
		meterChanged = true;
	}

	public int getMeterFrameThickness() {
		return meterFrameThickness;
	}

	public void setMeterFrameColor(int frameColor) {
		meterFrameColor = frameColor;
		meterChanged = true;
	}

	public int getMeterFrameColor() {
		return meterFrameColor;
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
		mFrame.stroke(meterFrameColor);
		mFrame.strokeWeight(meterFrameThickness);
		mFrame.strokeJoin(PConstants.BEVEL);
		mFrame.rect(meterX + meterFrameThickness / 2, // Left side
				meterY + meterFrameThickness / 2, // Top
				meterWidth - meterFrameThickness, // Right side
				meterHeight - meterFrameThickness); // Bottom
		mFrame.endDraw();
	}

	public void setMeterTitleFontSize(int fontSize) {
		meterTitleFontSize = scale(fontSize);
		meterChanged = true;
	}

	public int getMeterTitleFontSize() {
		return meterTitleFontSize;
	}

	public void setMeterTitleFontName(String fontName) {
		meterTitleFontName = fontName;
		meterTitleFont = p.createFont(meterTitleFontName, meterTitleFontSize);
		meterChanged = true;
	}

	public String getMeterTitleFontName() {
		return meterTitleFontName;
	}

	public void setMeterTitleFontColor(int titleColor) {
		meterTitleFontColor = titleColor;
		meterChanged = true;
	}

	public int getMeterTitleFontColor() {
		return meterTitleFontColor;
	}

	public void setMeterTitle(String title) {
		meterTitle = title;
		meterChanged = true;
	}

	/**
	 * Adjusts the position of the title from the
	 * meter Y value.
	 * 
	 * @param YOffset
	 */
	public void setMeterTitleYOffset(int YOffset) {
		meterTitleYOffset = scale(YOffset);
		meterChanged = true;
	}

	public int getMeterTitleYOffset() {
		return meterTitleYOffset;
	}

	// Position the title just below the top of the meter frame
	private void drawMeterTitle() {
		mTitle = p.createGraphics(p.width, p.height);
		mTitle.beginDraw();
		mTitle.textFont(meterTitleFont);
		mTitle.textAlign(PConstants.CENTER);
		mTitle.fill(meterTitleFontColor);
		mTitle.textSize(meterTitleFontSize);
		mTitle.text(meterTitle, pivotPointX, meterY + p.textAscent() + 
				meterFrameThickness + meterTitleYOffset);
		mTitle.endDraw();
	}

	/**
	 * Set the diameter of the meter pivot point
	 * 
	 * @param pSize
	 */
	public void setMeterPivotPointSize(int pSize) {
		meterPivotPointSize = scale(pSize);
		meterChanged = true;
	}

	public int getMeterPivotPointSize() {
		return meterPivotPointSize;
	}

	public void setMeterPivotPointColor(int pColor) {
		meterPivotPointColor = pColor;
		meterChanged = true;
	}

	public int getMeterPivotPointColor() {
		return meterPivotPointColor;
	}

	public int getMeterPivotPointX() {
		return pivotPointX;
	}

	public int getMeterPivotPointY() {
		return pivotPointY;
	}

	private void drawMeterPivotPoint() {
		mPivot = p.createGraphics(p.width, p.height);
		mPivot.beginDraw();
		mPivot.fill(meterPivotPointColor);
		mPivot.ellipse(pivotPointX, pivotPointY, meterPivotPointSize, meterPivotPointSize);
		mPivot.endDraw();
	}

	/**
	 * Set the width and length of the arc circle
	 * from the needle pivot point
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
	 * Set the minimum sensor reading expected
	 * A warning message is issued if this value is greater
	 * than the maxInputSignal expected
	 * 
	 * @param minIn
	 */
	public void setMinInputSignal(int minIn) {
		if (minIn > maxInputSignal) {
			String errorMessage = "Min input signal (" + minIn + " > Max input signal (" + maxInputSignal + ")";
			displayErrorMessage(errorMessage);
		}
		minInputSignal = minIn;
		meterChanged = true;
	}

	public int getMinInputSignal() {
		return minInputSignal;
	}

	/**
	 * Set the maximum sensor reading expected
	 * A warning message is issued if this value is less
	 * than the minInputSignal expected
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

	// The arc color when no low or high warnings are enabled
	// Note: should be the middle arc color.
	public void setArcColor(int aColor) {
		arcColor = aColor;
		meterChanged = true;
	}

	public int getArcColor() {
		return arcColor;
	}
	
	/**
	 * Allows the removal of the arc from the meter
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
	 * 
	 * @param minDegrees
	 */
	public void setArcMinDegrees(double minDegrees) {
		if ((minDegrees > arcMaxDegrees) && (arcMaxDegrees > 0)) {
			String errorMessage = "Arc Min Degrees (" + minDegrees + ") > "
					+ "Arc Max Degrees (" + arcMaxDegrees + ")";
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
	 * 360.0 degrees is at 3:00 o'clock.
	 * 
	 * @param maxDegrees
	 */
	public void setArcMaxDegrees(double maxDegrees) {
		if (maxDegrees < arcMinDegrees) {
			String errorMessage = "Arc Max Degrees (" + maxDegrees + ") < "
					+ "Arc Min Degrees (" + arcMinDegrees + ")";
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
			float maxScale = Float.parseFloat(scaleLabels[scaleLabels.length - 1]);
			float minScale = Float.parseFloat(scaleLabels[0]);
			float lowWarningValue;
			if (lowSensorWarningValue > minScale) {
				lowWarningValue = lowSensorWarningValue;
			} else {
				lowWarningValue = minScale;
			}
			float lowWarningPosition = PApplet.map(lowWarningValue, minScale, maxScale,
					PApplet.radians((float) arcMinDegrees), PApplet.radians((float) arcMaxDegrees));
			mArc.stroke(lowSensorWarningColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2,
					PApplet.radians((float) arcMinDegrees), lowWarningPosition, PConstants.OPEN);
			float highWarningValue;
			if (highSensorWarningValue < maxScale) {
				highWarningValue = highSensorWarningValue;
			} else {
				highWarningValue = maxScale;
			}
			float highWarningPosition = PApplet.map(highWarningValue, minScale, maxScale,
					PApplet.radians((float) arcMinDegrees), PApplet.radians((float) arcMaxDegrees));
			mArc.stroke(midSensorWarningColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2, lowWarningPosition,
					highWarningPosition, PConstants.OPEN);
			mArc.stroke(highSensorWarningColor);
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2, highWarningPosition,
					PApplet.radians((float) arcMaxDegrees), PConstants.OPEN);
		} else {
			mArc.arc(pivotPointX, pivotPointY, arcPositionOffset * 2, arcPositionOffset * 2,
					PApplet.radians((float) arcMinDegrees), PApplet.radians((float) arcMaxDegrees), PConstants.OPEN);
		}
		mArc.endDraw();
	}

	public void setMeterScaleFontSize(int sSize) {
		meterScaleFontSize = scale(sSize);
		meterChanged = true;
	}

	public int getMeterScaleFontSize() {
		return meterScaleFontSize;
	}

	public void setMeterScaleFontName(String sFont) {
		meterScaleFontName = sFont;
		meterScaleFont = p.createFont(meterScaleFontName, meterScaleFontSize);
		meterChanged = true;
	}

	public String getMeterScaleFontName() {
		return meterScaleFontName;
	}

	public void setMeterScaleFontColor(int sColor) {
		meterScaleFontColor = sColor;
		meterChanged = true;
	}

	public int getMeterScaleFontColor() {
		return meterScaleFontColor;
	}

	/**
	 * Each label will coinside with a long tic mark
	 * These are numeric labels. The first and last of
	 * the array are used to calculate the tic spacing
	 * even when alternate labels are used.
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
	 * Display these instead of numeric labels.
	 *
	 * @param displayAlternateLabels
	 */
	public void setDisplayAlternateScaleLabels(boolean displayAlternateLabels) {
		displayAlternateScaleLabels = displayAlternateLabels;
		meterChanged = true;
	}

	public boolean getDisplayAlternateScaleLabesl() {
		return displayAlternateScaleLabels;
	}

	/**
	 * These can be any text
	 * 
	 * @param labels
	 */
	public void setAlternateScaleLabels(String[] labels) {
		if (labels.length == scaleLabels.length) {
			alternateScaleLabels = labels;
			meterChanged = true;
		} else {
			String errorMessage = "alternateScaleLabels length != scaleLabels length.";
			displayErrorMessage(errorMessage);
		}
	}

	public String[] getAlternateScaleLabels() {
		return alternateScaleLabels;
	}

	/**
	 * The arc length of the scale labels from the pivot point.
	 * 
	 * @param scaleOffset
	 */
	public void setMeterScaleOffsetFromPivotPoint(int scaleOffset) {
		meterScaleOffsetFromPivotPoint = scale(scaleOffset);
		meterChanged = true;
	}

	public int getMeterScaleOffsetFromPivotPoint() {
		return meterScaleOffsetFromPivotPoint;
	}

	/**
	 * Disable the display of the last scale label for a full circle
	 * meter to prevent the last label from overwriting the first one
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

	// Draw the labels and try to position them correctly.
	// Note: long labels would require the adjustment of the arc to
	// provide extra spacing.
	private void drawMeterScaleLabels() {
		float labelX;
		float labelY;
		String[] useLabels = null;
		double currentTicRadians = PApplet.radians((float) arcMinDegrees);
		double ticSeparation = (PApplet.radians((float) arcMaxDegrees) - 
				PApplet.radians((float) arcMinDegrees))
				/ (scaleLabels.length - 1);
		// Determine which scale array to use.
		if (displayAlternateScaleLabels == true) {
			if (alternateScaleLabels != null) {
				useLabels = (String[]) Arrays.copyOf(alternateScaleLabels, alternateScaleLabels.length);
	//			useLabels = alternateScaleLabels.clone();
			} else {
				String errorMessage = "alternateScaleLabels undefined, using scaleLabels.";
				displayErrorMessage(errorMessage);
				useLabels = (String[]) Arrays.copyOf(scaleLabels, scaleLabels.length);
	//			useLabels = scaleLabels.clone();
			}
		} else {
			useLabels = (String[]) Arrays.copyOf(scaleLabels, scaleLabels.length);
	//		useLabels = scaleLabels.clone();
		}
		int labelLength = useLabels.length;
		mLabels = p.createGraphics(p.width, p.height);
		mLabels.beginDraw();
		mLabels.textFont(meterScaleFont);
		mLabels.textSize(meterScaleFontSize);
		mLabels.fill(meterScaleFontColor);
		mLabels.textAlign(PConstants.CENTER);
		for (int i = 0; i < labelLength; i++) {
			if (displayLastScaleLabel == false && i == labelLength - 1) {
				continue;
			}
			labelX = pivotPointX + (PApplet.cos((float) currentTicRadians) * 
					meterScaleOffsetFromPivotPoint);
			labelY = pivotPointY + PApplet.sin((float) currentTicRadians) * 
					meterScaleOffsetFromPivotPoint +
					(p.textAscent() + p.textDescent()) / 2;
			mLabels.text(useLabels[i], labelX, labelY);
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

	public void setTicMarkSetbackFromArc(int ticSetback) {
		ticMarkSetbackFromArc = scale(ticSetback);
		meterChanged = true;
	}

	public int getTicMarkSetbackFromArc() {
		return ticMarkSetbackFromArc;
	}

	public void setTicMarkThickness(int ticMarkThickness) {
		this.ticMarkThickness = scale(ticMarkThickness);
		meterChanged = true;
	}

	public int getTicMarkThickness() {
		return ticMarkThickness;
	}

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
		double ticSeparation = (PApplet.radians((float) arcMaxDegrees) - PApplet.radians((float) arcMinDegrees))
				/ (totalTicCount - 1);
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
			ticX1 = pivotPointX
					+ (PApplet.cos((float) currentTicRadians) * (ticMarkOffsetFromPivotPoint - ticMarkSetbackFromArc));
			ticX2 = pivotPointX + (PApplet.cos((float) currentTicRadians)
					* (ticMarkOffsetFromPivotPoint - ticMarkSetbackFromArc + ticLength));
			ticY1 = pivotPointY
					+ PApplet.sin((float) currentTicRadians) * (ticMarkOffsetFromPivotPoint - ticMarkSetbackFromArc);
			ticY2 = pivotPointY + PApplet.sin((float) currentTicRadians)
					* (ticMarkOffsetFromPivotPoint - ticMarkSetbackFromArc + ticLength);
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
	 * 
	 * @param newSensorReading
	 */
	public void updateMeterReading(int newSensorReading) {
		// Determine needle position relative to meter scale
		// Use the first and last values from the scaleLabels array
		float maxScale = Float.parseFloat(scaleLabels[scaleLabels.length - 1]);
		float minScale = Float.parseFloat(scaleLabels[0]);
		newSensorValue = PApplet.map((float) newSensorReading, (float) minInputSignal, (float) maxInputSignal, minScale,
				maxScale);
		newMeterPosition = PApplet.map(newSensorValue, minScale, maxScale, PApplet.radians((float) arcMinDegrees),
				PApplet.radians((float) arcMaxDegrees));
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
	 * @param size
	 */
	public void setNeedleSize(int size) {
		needleSize = scale(size);
	}

	public int getNeedleSize() {
		return needleSize;
	}

	// Draw the needle at its new position.
	// Display sensor values if enabled.
	// Display warning messages if enabled.
	private void drawMeterNeedle() {
		float needleX = pivotPointX + (PApplet.cos(newMeterPosition) * needleLength);
		float needleY = pivotPointY + PApplet.sin(newMeterPosition) * needleLength;
		String informationText;
		mNeedle = p.createGraphics(p.width, p.height);
		mNeedle.beginDraw();
		mNeedle.stroke(needleColor);
		mNeedle.strokeWeight(needleSize);
		mNeedle.line(pivotPointX, pivotPointY, needleX, needleY);

		if (displayDigitalMeterValue == true) {
			mNeedle.textFont(informationAreaFont);
			mNeedle.fill(informationAreaFontColor);
			mNeedle.textAlign(PConstants.CENTER);
			mNeedle.textSize(informationAreaFontSize);
			informationText = Double.toString(Math.round(newSensorValue * 100.0) / 100.0);
			mNeedle.text(informationText, meterX + (meterWidth / 2), 
					meterY + meterHeight - informationAreaTextYOffset);
		}

		if (lowSensorWarningActive == true) {
			if (lowSensorWarningValue >= newSensorValue) {
				mNeedle.textFont(sensorWarningFont);
				mNeedle.fill(lowSensorWarningFontColor);
				mNeedle.textSize(sensorWarningFontSize);
				mNeedle.textAlign(PConstants.LEFT);
				mNeedle.text("  " + sensorWarningLowText, meterX + meterFrameThickness,
						meterY + p.textAscent() + meterFrameThickness + sensorWarningTextYOffset);
			}
		}
		if (highSensorWarningActive == true) {
			if (highSensorWarningValue <= newSensorValue) {
				mNeedle.textFont(sensorWarningFont);
				mNeedle.fill(highSensorWarningFontColor);
				mNeedle.textSize(sensorWarningFontSize);
				mNeedle.textAlign(PConstants.RIGHT);
				mNeedle.text(sensorWarningHighText + "  ", meterX + meterWidth - meterFrameThickness,
						meterY + p.textAscent() + meterFrameThickness + sensorWarningTextYOffset);
			}
		}
		mNeedle.endDraw();
	}

}
