import processing.core.PApplet;
import processing.core.PFont;
import meter.Meter;

public class UsingP extends PApplet {
	Meter m, m2, m3;
	int i = 0;
	int bx, by, bw, bh;

	public static void main(String[] args) {
		PApplet.main("UsingP");

	}
	

	public void settings() {
		size(500, 400);
		// size(1200, 650);
	
	}

	public void setup() {
		
	  m = new Meter(this, 10, 25);
	// m.setUp(10, 200, 1.0f, 4.0f, 190.0f, 350.0f);
	//  m.setMeterWidth(280);
	//  m.updateMeter(100);
	  // *** The Reset button messes up the Meter fonts and positions. ***
	  // Reset button
//	  bx = 40;
//	  by = height - 80;
//	  bw = 200;
//	  bh = 50;
//	  PFont resetFont;
//	  rect(bx, by, bw, bh);
//	  setTitleFontName("Liberation Sans Bold");
//	  setTitleFontSize(24);
//	  resetFont = createFont("Georgia", 52);
//	  resetFont = createFont("DejaVu Sans Bold", 46);
//	  textFont(resetFont);
//	  textAlign(CENTER, CENTER);
//	  fill(0);
//	  text("Reset", bx + bw / 2, by + bh / 2);
	  m.updateMeter(200);
		  
		fill(120, 50, 0);
//		m = new Meter(this, 10, 25);
	//	m = new Meter(this, 20, 25, true);
	//	m.setMeterWidth(180);
	//	m.setDisplayMaximumValue(true);
	//	m.setDisplayMaximumNeedle(true);
	//	m.setDisplayDigitalMeterValue(true);
	//	m.setInformationAreaText(" % .2f");
	//	m.setMaximumValue(0.0f);
	//	m.setDisplayMinimumValue(true);
	//	m.setDisplayMinimumNeedle(true);
		// m.setFrameThickness(40);
		// m.setFrameStyle(2);
		// m.setArcPositionOffset(140);
		// m.setScaleOffsetFromPivotPoint(160);
		// m.setTicMarkOffsetFromPivotPoint(106);
		// m.setInformationAreaFontColor(color(255, 255, 255));
	//	 m.setLowSensorWarningActive(true);
	//	 m.setLowSensorWarningValue((float)1.0);
		// m.setLowSensorWarningArcColor(color(50,50,200));
		// m.setMidSensorWarningArcColor(color(50,200,50));
		// m.setHighSensorWarningArcColor(color(200,50,50));
	//	 m.setHighSensorWarningActive(true);
		// m.setHighSensorWarningValue((float)80.0);
	//	 m.setHighSensorWarningValue((float)4.0);
	//	m.setEnableInputSignalOutOfRange(true);
	//	m.setDisplayWarningMessagesToOutput(false);
	//	int mx = m.getMeterX();
		// int my = m.getMeterY();
	//	int mw = m.getMeterWidth();
		// int mh = m.getMeterHeight();
		// line(5, my, width - 5, my);
		// line(mx, 5, mx, height - 5);
		// line(mx + mw, 5, mx + mw, height - 5);
		// line(5, my + mh, width - 5, my + mh);
		// System.out.println("Title: " + m.getMeterTitle());
		// m2 = new Meter(this, mx, my + mh);
	//	m.setTitle("Direction");
		// m = new Meter(this, 325, 25, true);
		// m.setDisplayArc(false);
		// m.setNeedleThickness(2);
	//	m3 = new Meter(this, mx + mw, 25, true);
		// m3.setFrameThickness(40);
	//	m.setMinScaleValue(0.0f);
	//	m.setMaxScaleValue(360.0f);
	//	m.setMinInputSignal(0);
	//	m.setMaxInputSignal(255);
	//	m3.setMinScaleValue(0);
	//	m3.setMaxScaleValue(360);
	//	m.setShortTicsBetweenLongTics(0);

	//	m.setArcMinDegrees(0.0); // PI (left side)
	//	m.setArcMaxDegrees(400.0); // TWO_PI (right side)
	//	m3.setLowSensorWarningActive(true);
	//	m3.setLowSensorWarningValue((float)60);
		// m.setLowSensorWarningValue((float)1.0);
		// m.setLowSensorWarningArcColor(color(50,50,200));
		// m.setMidSensorWarningArcColor(color(50,200,50));
		// m.setHighSensorWarningArcColor(color(200,50,50));
	//	m3.setHighSensorWarningActive(true);
	//	m3.setHighSensorWarningValue((float)200);
		// m.setHighSensorWarningValue((float)4.0);
	//	 m.setDisplayLastScaleLabel(false);
//		m.setDisplayDigitalMeterValue(true);
		// m2.setDisplayDigitalMeterValue(true);
	//	m3.setDisplayDigitalMeterValue(true);
	//	m3.setDisplayLastScaleLabel(false);
		// m.setMeterWidth((int)random(50, 700));
		// m2 = new Meter(this, 625, 70);
		// m.setTitleColor(color(0,255,0));
	//	 String[] scaleLabels = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0"};
	//	String[] scaleLabels = {"0", "30", "60", "90", "120", 
	//			"150", "180", "210", "240", "270", "300", "330", "360"};
	//	String[] scaleLabels = {"0", "330", "300", "270", "240", "210", 
	//			"180", "150", "120", "90", "60", "30", "360"};
	//	 m.setScaleLabels(scaleLabels);
		// String[] scaleLabels = {"-10", "0", "10", "20", "30", "40", "50",
		// "60", "70", "80", "90", "100", "110"};
	//	String[] scaleLabels2= { "E", "SE", "S", "SW", "W", "NW", "N", "NE", "E" };
	//	m3.setScaleLabels(scaleLabels2);
	//	m.setMinimumIgnoreCount(1);
	//	 m.updateMeter(0);

		// m2.updateMeter(75);

		// System.out.println("Meter1 Height: " + m.getMeterHeight());
		// System.out.println("Meter2 Height: " + m2.getMeterHeight());
		// System.out.println("Frame Style: " + m.getFrameStyle());
		// System.out.println("MinArcDegrees: " + m.getArcMinDegrees() + "
		// MaxArcDegrees: " + m.getArcMaxDegrees());
		// //System.out.println("Meter Pivot Point Offset: " +
		// m.getPivotPointYOffset());
		// System.out.println("Pivot Point:" + m.getPivotPointX() + ", " +
		// m.getPivotPointY());
		/*
		 * String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().
		 * getAvailableFontFamilyNames();
		 * 
		 * for (int i = 0; i < fonts.length; i++) {
		 * System.out.println(fonts[i]); } System.out.println("Font name: " +
		 * m.getInformationAreaFontName());
		 */
		 noLoop();
	}

	public void draw() {
		// m.setMeterWidth((int)random(50, 700));
//		int newSensorReading = 100;
//		newSensorReading = (int) random(0, 255);
//		newSensorReading = 360 - newSensorReading;
		// m.setDisplayArc(false);
		// stroke(m.getFrameColor());
		// ellipse(width - 20, height - 20, 25, 25);
/*		if (++i == 4) {
			m.setDisplayLastScaleLabel(true);
		}
		if (i >= 8) {
			m.setDisplayLastScaleLabel(false);
			i = 0;
		}
*/	
//		m.updateMeter(newSensorReading);
		// m2.updateMeter(newSensorReading);
	//	m3.updateMeter(newSensorReading);
		delay(1000);
	}

}
