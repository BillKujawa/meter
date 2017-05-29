// Display mouse direction, velocity, and acceleration.

/*
  The meter turns clockwise while the Polar coordinates 
 are counter clockwise. You will notice the changes to the
 Meter to accomodate this. Just do not expect to 
 display the digitalMeterValue correctly.
 The dot in the center of the window and the horizontal
 and vertical line passing through it are for the
 Direction meter.
 
 ToDoList:
 Display the max velocity when movement stops.
 Do not save values if the mouse distance is zero.
 When the mouse stops moving, for more than 10?? times
 in a row, assume actions are finished and reset "i".
 Limit the total number of values recorded to 1000??
 and what to do in that case, reset "i" or just stop
 recording values? How to notify the user?
 */

import meter.*;
Meter m, av, aa;

// A Mover object
Mover mover;
float heading;

PVector[] pts = new PVector[1000];
int[] times = new int[1000];
float[] tdiff = new float[1000];
float[] dist = new float[1000];
int avgVelocity;
int avgAcceleration = 0;
int i = 0;
int maxVelocity = 0;
float dotsPerCM = 255;

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
  av.setMaxInputSignal(1000);
  av.setShortTicsBetweenLongTics(0);
  av.setMeterTitle("Average Velocity - CM / Sec");
  String[] scaleLabelsAV = {"0", "0.5", "1.0", 
    "1.5", "2.0", "2.5", "3.0"};
  av.setScaleLabels(scaleLabelsAV);
  av.setMaxScaleValue(3.0);
  av.setDisplayMaximumMeterValue(true);
  int avx = av.getMeterX();
  int avy = av.getMeterY();
  int avh = av.getMeterHeight();
  
  aa = new Meter(this, avx, avy + avh + 20);
  aa.setMeterWidth(280);
  aa.setMinInputSignal(-100);
  aa.setMaxInputSignal(100);
//  aa.setShortTicsBetweenLongTics(0);
  aa.setMeterTitle("Average Acceleration CM / Sec / Sec");
  String[] scaleLabelsAA = {"-3.0", "-2.0", "-1.0", 
    "0.0", "1.0", "2.2", "3.0"};
  aa.setScaleLabels(scaleLabelsAA);
  aa.setMinScaleValue(-3.0);
  aa.setMaxScaleValue(3.0);
 
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
    pts[i] = new PVector();
    pts[i] = mouse.copy();
    times[i] = millis();
    if (i > 0) {
      // Change milliseconds to seconds
      tdiff[i] = (times[i] - times[i-1]) * .001;
      // Change pixels to CM
      dist[i] = PVector.dist(pts[i], pts[i-1]) / dotsPerCM;
      // Change float to int for meter input
      avgVelocity = (int)((dist[i] / tdiff[i]) * 10);
      if (avgVelocity > maxVelocity) {
        maxVelocity = avgVelocity;
      }
      System.out.println("i: " + i + "  tdiff[i]: " + tdiff[i] + "  dist[i]: " + dist[i] + 
          "  avgVelocity: " + avgVelocity + "  max: " + maxVelocity);
      if (dist[i] > 0.0) {
        i++;
      }
    }
    if (i == 0) {
      i++;
    }

    //   System.out.println("i: " + i + "  diff[i]: " + diff[i] + "  dist[i]: " + dist[i]);

    // System.out.println("center.x: " + center.x + "  center.y: " + center.y);
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
    av.updateMeter(avgVelocity);
    aa.updateMeter(avgAcceleration);
    //   delay(200);
  }
}