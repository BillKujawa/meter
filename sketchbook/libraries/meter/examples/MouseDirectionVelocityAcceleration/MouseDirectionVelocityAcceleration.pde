// Display mouse direction, velocity, and acceleration.

/*
  The meter turns clockwise whild the Polar coordinates 
 are counter clockwise. You will notice the changes to the
 Meter to accomodate this. Just do not expect to correctly
 display the digitalMeterValue correctly.
 The dot in the center of the window and the horizontal
 and vertical line passing through it are for the
 Direction meter.
 */

import meter.*;
Meter m, av;

// A Mover object
Mover mover;
float heading;

void setup() {
  size(640, 640);
  // change size to 1000, 1000 and measure your processing window
  // with a ruller. This will tell you the approximate DotsPerCM.
  // Mine turned out to be approximately 255, which is used for
  // the calculations.
  // size(1000, 1000);

  m = new Meter(this, 10, 10, true);
  m.setMeterWidth(280);
  int mx = m.getMeterX();
  int my = m.getMeterY();
  int mw = m.getMeterWidth();
  m.setMeterTitle("Direction");
  m.setMinScaleValue(0.0f);
  m.setMaxScaleValue(360.0f);
  m.setMaxInputSignal(360);
  m.setDisplayWarningMessagesToOutput(false);
  m.setArcMaxDegrees(360);
  m.setArcMinDegrees(0);
  m.setDisplayLastScaleLabel(false);
  String[] scaleLabels = {"0", "330", "300", "270", "240", "210", 
    "180", "150", "120", "90", "60", "30", "360"};
  m.setScaleLabels(scaleLabels);
  int ticMarkPosition = m.getMeterScaleOffsetFromPivotPoint();
  m.setTicMarkOffsetFromPivotPoint(20);
  m.setLongTicMarkLength(ticMarkPosition);
  m.setShortTicsBetweenLongTics(0);
  m.setNeedleLength(180);

  fill(color(255, 0, 0));
  ellipse(width/2, height/2, 8, 8);
  line(width/2, 5, width/2, height - 5);
  line(5, height/2, width - 5, height/2);

  av = new Meter(this, width/2 + 20, 10);
  av.setMeterWidth(280);
  av.setMeterTitle("Average Velocity - CM / Sec");

  mover = new Mover();
}

void draw() {
  // background(0);

  // Update the location
  mover.update();
  // Display the Mover
  mover.display();
}

/*
 * Mover class and PVector ideas borrowed from
 * Daniel Shiffman.  
 */

class Mover {

  // The Mover tracks location, velocity, and acceleration 
  PVector center;
  PVector mouse;
  PVector previous;

  Mover() {
    // Start in the center
    center = new PVector(width/2, height/2);
    //    System.out.println("center.x: " + center.x + "  center.y: " + center.y);
    // Initialize for comparing
    //    previous = new PVector();
    //    previous = center.copy();
  }

  void update() {

    // Compute a vector that points from center to mouse
    PVector mouse = new PVector(mouseX, mouseY);
    //    System.out.println("mouseX: " + mouseX + "   mouseY: " + mouseY);
    //    System.out.println("center.x: " + center.x + "  center.y: " + center.y);
    // Set mouse position relative to the center.
    mouse.x = mouse.x - center.x;
    mouse.y = center.y - mouse.y;
    //    System.out.println("mouse.x: " + mouse.x + "  mouse.y: " + mouse.y);
    heading = degrees(mouse.heading());
    if (heading < 0) {
      heading += 360.0;
    }
    //    System.out.println("heading: " + heading);
    //    System.out.println();
  }

  void display() {
    // Compensate for Meter and Polar graphs in different directions.
    m.updateMeter(360 - (int)heading);
    av.updateMeter(20);
    //    delay(2000);
  }
}