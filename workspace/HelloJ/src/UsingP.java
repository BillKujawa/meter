import processing.core.PApplet;
import meter.Meter;


public class UsingP extends PApplet {
	Meter m, m2, m3;

	public static void main(String[] args) {
		PApplet.main("UsingP");

	}

	public void settings() {
		size(900, 600);
	//	size(1200, 650);

	}

	public void setup() {
		fill(120, 50, 0);
		m = new Meter(this, 10, 25);
//		m = new Meter(this, 20, 25, true);
//		m.setMeterWidth(340);
		m.setMeterFrameThickness(40);
		int mx = m.getMeterX();
		int my = m.getMeterY();
		int mw = m.getMeterWidth();
		int mh = m.getMeterHeight();
		line(5, my, width - 5, my);
		line(mx, 5, mx, height - 5);
		line(mx + mw, 5, mx + mw, height - 5);
		line(5, my + mh, width - 5, my + mh);
	//	System.out.println("Title: " + m.getMeterTitle());
		m2 = new Meter(this, mx, my + mh);
	//	m2.setMeterTitle("Test");
	//	m = new Meter(this, 325, 25, true);
	//	m.setDisplayArc(false);
		m.setNeedleThickness(2);
		m3 = new Meter(this, mx + mw, 25, true);
		m3.setMeterFrameThickness(40);

		m3.setArcMinDegrees(90.0); // PI (left side)
		m3.setArcMaxDegrees(360.0); // TWO_PI (right side)
//		m.setDisplayLastScaleLabel(false);
		m.setDisplayDigitalMeterValue(true);
		m2.setDisplayDigitalMeterValue(true);
		m3.setDisplayDigitalMeterValue(true);
	//	m.setMeterWidth((int)random(50, 700));
	//	m2 = new Meter(this, 625, 70);
	//  m.setMeterTitleColor(color(0,255,0));
	//	String[] scaleLabels = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0"};
	//	String[] scaleLabels = {"-10", "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110"};
	//	m.setScaleLabels(scaleLabels);
	//	m.setDisplayAlternateScaleLabels(true);;
	//	String[] alternateScaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "E"};
	//	m.setAlternateScaleLabels(alternateScaleLabels);

	//	m.updateMeter(0);
		
	//	m2.updateMeter(75);

		System.out.println("Meter Height: " + m.getMeterHeight());
		System.out.println("MinArcDegrees: " + m.getArcMinDegrees() + "  MaxArcDegrees: " + m.getArcMaxDegrees());
		//System.out.println("Meter Pivot Point Offset: " + m.getMeterPivotPointYOffset());
	//	System.out.println("Pivot Point:" + m.getMeterPivotPointX() + ", " + m.getMeterPivotPointY());
/*
		String fonts[]
		        = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for (int i = 0; i < fonts.length; i++) {
		    System.out.println(fonts[i]);
		}
		System.out.println("Font name: " + m.getInformationAreaFontName());
*/
		// noLoop();
	}

	public void draw() {
	//	m.setMeterWidth((int)random(50, 700));
	//	m.setDisplayDigitalSensorValues(true);
		int newSensorReading = 120;
 		newSensorReading = (int)random(0, 255);
	//	m.setDisplayArc(false);
	//	stroke(m.getMeterFrameColor());
	//	ellipse(width - 20, height - 20, 25, 25);
		m.setLowSensorWarningActive(true);
	//	m.setLowSensorWarningValue((float)60.0);
		m.setLowSensorWarningValue((float)1.0);
		m.setLowSensorWarningArcColor(color(50,50,200));
		m.setMidSensorWarningArcColor(color(50,200,50));
		m.setHighSensorWarningArcColor(color(200,50,50));
		m.setHighSensorWarningActive(true);
	//	m.setHighSensorWarningValue((float)80.0);
		m.setHighSensorWarningActive(true);
		m.setHighSensorWarningValue((float)4.0);
		m.updateMeter(newSensorReading);
		m2.updateMeter(newSensorReading);
		m3.updateMeter(newSensorReading);
		delay(1000);
	}

}
