import processing.core.PApplet;
import meter.Meter;
/*
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsEnvironment;
*/
public class UsingP extends PApplet {
	Meter m, m2;

	public static void main(String[] args) {
		PApplet.main("UsingP");

	}

	public void settings() {
		size(500, 500);
	//	size(1200, 650);

	}

	public void setup() {
		fill(120, 50, 0);
//		m = new Meter(this, 120, 25);
		m = new Meter(this, 20, 25, true);
//		m.setMeterWidth(340);
	//	m.setMeterFrameThickness(8);
		int mx = m.getMeterX();
		int my = m.getMeterY();
		int mw = m.getMeterWidth();
		int mh = m.getMeterHeight();
		line(5, my, width - 5, my);
		line(mx, 5, mx, height - 5);
		line(mx + mw, 5, mx + mw, height - 5);
		line(5, my + mh, width - 5, my + mh);
	//	m2 = new Meter(this, 30, my + mh);
	//	m2.setMeterTitle("Test");
	//	m = new Meter(this, 325, 25, true);
	//	m.setDisplayArc(false);

		m.setMinArcDegrees(90.0); // PI (left side)
		m.setMaxArcDegrees(360.0); // TWO_PI (right side)
//		m.setDisplayLastScaleLabel(false);
//		m.setDisplayDigitalSensorValues(true);
	//	m.setMeterWidth((int)random(50, 700));
	//	m2 = new Meter(this, 625, 70);
	//  m.setMeterTitleColor(color(0,255,0));
	//	String[] scaleLabels = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0"};
		String[] scaleLabels = {"-10", "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110"};
		m.setScaleLabels(scaleLabels);
	//	m.setDisplayAlternateScaleLabels(true);;
	//	String[] alternateScaleLabels = {"E", "SE", "S", "SW", "W", "NW", "N", "NE", "E"};
	//	m.setAlternateScaleLabels(alternateScaleLabels);

	//	m.updateMeter(0);
		
	//	m2.updateMeter(75);

		System.out.println("Meter Height: " + m.getMeterHeight());
		System.out.println("MinArcDegrees: " + m.getMinArcDegrees() + "  MaxArcDegrees: " + m.getMaxArcDegrees());
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
		int newSensorReading = 20;
		newSensorReading = (int)random(0, 255);
	//	stroke(m.getMeterFrameColor());
	//	ellipse(width - 20, height - 20, 25, 25);
		m.setLowSensorWarningActive(true);
		m.setLowSensorWarningValue((float)60.0);
		m.setHighSensorWarningActive(true);
		m.setHighSensorWarningValue((float)80.0);
		m.updateMeter(newSensorReading);
	//	m2.updateMeter(newSensorReading);
		delay(1000);
	}

}
