// Display mouse direction, velocity, and acceleration.

/*
  The meter turns clockwise while the Polar coordinates 
 are counter clockwise. You will notice the changes to the
 Meter to accomodate this. Just do not expect to 
 display the digitalMeterValue correctly.
 The dot in the center of the window and the horizontal
 and vertical line passing through it are for the
 Direction meter.
 
 Move the mouse pointer about the center of the display
 to have the meters resister the movement.
 
 Please excuse any physics mistakes as this is just an
 example of non-microprocessor Meter use, 
 and it is just for fun.
 
 * Mover class and PVector ideas borrowed from
 * Daniel Shiffman.  
 */

import meter.*;
Meter m, av, aa;

// A Mover object
Mover mover;
float heading;

// Set array sizes.
int iMax = 500;
PVector[] pts = new PVector[iMax];
int[] times = new int[iMax];
float[] tdiff = new float[iMax];
float[] dist = new float[iMax];
int[] avgVelocity = new int[iMax];
int velocity = 0;
int avgAcceleration = 0;
int i = 0;
int maxVelocity = 0;
int maxAcceleration = 0;
float dotsPerCM = 255;
int bx, by, bw, bh;
boolean reset = false;

PFont resetFont;


void setup() {
  size(1000, 1000);
  // With the window size at 1000 by 1000, measure your processing window
  // with a ruller. This will tell you the approximate DotsPerCM.
  // Mine turned out to be approximately 255, which is used for
  // the calculations.

  m = new Meter(this, 10, 10, true);
  //  m.setMeterWidth(280);
  int mx = m.getMeterX();
  int my = m.getMeterY();
  int mw = m.getMeterWidth();
  m.setTitle("Direction");

  // Example of using "setUp" instead of the individual calls.
  m.setUp(0, 360, 0.0f, 360.0f, 0.0f, 360.0f);
  // m.setMinInputSignal(0);
  //  m.setMaxInputSignal(360);
  //  m.setMinScaleValue(0.0f);
  //  m.setMaxScaleValue(360.0f);
  //  m.setArcMaxDegrees(360);
  //  m.setArcMinDegrees(0);

  m.setDisplayWarningMessagesToOutput(false);
  m.setDisplayLastScaleLabel(false);
  String[] scaleLabels = {"0", "330", "300", "270", "240", "210", 
    "180", "150", "120", "90", "60", "30", "360"};
  m.setScaleLabels(scaleLabels);

  m.setTicMarkOffsetFromPivotPoint(20);
  m.setLongTicMarkLength(120);
  m.setShortTicsBetweenLongTics(0);
  m.setNeedleLength(180);

  fill(color(255, 0, 0));
  ellipse(width/2, height/2, 8, 8);
  line(width/2, 5, width/2, height - 5);
  line(5, height/2, width - 5, height/2);

  av = new Meter(this, width/2 + 20, 10);
  av.setMeterWidth(380);
  av.setMaxInputSignal(1000);
  av.setShortTicsBetweenLongTics(0);
  av.setTitle("Average Velocity - CM / Sec");
  String[] scaleLabelsAV = {"0", "0.5", "1.0", 
    "1.5", "2.0", "2.5", "3.0"};
  av.setScaleLabels(scaleLabelsAV);
  av.setMaxScaleValue(3.0);
  av.setDisplayMaximumValue(true);
  av.setDisplayMaximumNeedle(true);
  int avx = av.getMeterX();
  int avy = av.getMeterY();
  int avh = av.getMeterHeight();

  aa = new Meter(this, avx, avy + avh + 20);
  aa.setMeterWidth(380);
  aa.setMinInputSignal(-200);
  aa.setMaxInputSignal(200);
  //  aa.setShortTicsBetweenLongTics(0);
  aa.setTitle("Average Acceleration CM / Sec / Sec");
  String[] scaleLabelsAA = {"-3.0", "-2.0", "-1.0", 
    "0.0", "1.0", "2.2", "3.0"};
  aa.setScaleLabels(scaleLabelsAA);
  aa.setMinScaleValue(-3.0);
  aa.setMaxScaleValue(3.0);
  aa.setDisplayMaximumValue(true);
  aa.setDisplayMaximumNeedle(true);
  aa.setDisplayMinimumNeedle(true);


  // Note: This strange initial value will fool the meter
  // into thinking that the new maximumMeterValue is 0.0
  // when the first updateMeter is executed. The maximumMeterValue
  // is zero but the maximumNeedlePosition is set to the lowest
  // scale position, which is 180 degrees and not the positin of 0.0
  // for this example, which is 270 degrees.
  // Life is interesting at times.
  aa.setMaximumValue(-0.1f);

  mover = new Mover();

  // Reset button
  bx = 40;
  by = height/2 + 40;
  bw = 200;
  bh = 50;
  rect(bx, by, bw, bh);

  // Uncomment the following two lines to see the available fonts 
  //String[] fontList = PFont.list();
  //printArray(fontList);

  resetFont = createFont("Georgia", 32);
  textFont(resetFont);
  textAlign(CENTER, CENTER);
  fill(0);
  text("Reset", bx + bw / 2, by + bh / 2);

  // Without this, the Meter font changes, Bug?
  resetFont = createFont("Georgia", 16);
  textFont(resetFont);
}

void draw() {
  // background(0);

  // Check if Reset selected.
  if (mousePressed) {
    if (mouseX > bx && mouseX < bx + bw &&
      mouseY > by && mouseY < by + bh) {
      reset = true;
    }
  }

  // Update the location
  mover.update();
  // Display the Mover
  mover.display();
}

class Mover {

  // The Mover tracks location, velocity, and acceleration 
  PVector center;
  PVector mouse;
  PVector previous;

  Mover() {
    // Start in the center
    center = new PVector(width/2, height/2);
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
      avgVelocity[i] = (int)((dist[i] / tdiff[i]) * 10);
      velocity = avgVelocity[i];
      if (avgVelocity[i] > maxVelocity) {
        maxVelocity = avgVelocity[i];
      }
      if (i > 1) {
        avgAcceleration = (int)((avgVelocity[i] - avgVelocity[i-1]) * 10) / (times[i] - times[i-1]);
        if (avgAcceleration > maxAcceleration) {
          maxAcceleration = avgAcceleration;
        }
      }
      // If you wish to see the calculation values.
      //      System.out.println("i: " + i + "  tdiff[i]: " + tdiff[i] + "  dist[i]: " + dist[i] + 
      //        "  avgVelocity: " + avgVelocity[i] + "  max: " + maxVelocity + 
      //        "  avgAcceleleration: " + avgAcceleration + "  maxAcceleration: " + maxAcceleration);

      // Ignore any data when the mouse is not moving by not incrementing the counter.
      if (dist[i] > 0.0) {
        i++;
      }
    }
    // Ensure there are at least two values before calculating values.
    if (i == 0) {
      i++;
    }

    // Set mouse position relative to the center.
    // Compensate for the difference in coordinate systems.
    mouse.x = mouse.x - center.x;
    mouse.y = center.y - mouse.y;
    heading = degrees(mouse.heading());
    if (heading < 0) {
      heading += 360.0;
    }
  }

  void display() {
    // Reset the maximum values for the Meters.
    if (reset == true) {
      av.setMaximumValue(-0.1f);
      aa.setMaximumValue(-0.-1f);
      aa.setMinimumValue(0.1f);
      reset = false;
    }
    // Compensate for Meter and Polar graphs in different directions.
    m.updateMeter(360 - (int)heading);
    av.updateMeter(velocity);
    aa.updateMeter(avgAcceleration);
  }
}