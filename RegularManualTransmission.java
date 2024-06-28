package vehicle;

/**
 * Represents a vehicle that allows the driver to change the speed and the gears.
 */
public class RegularManualTransmission implements ManualTransmission {

  private static final String OK_EVERYTHING_IS_OK = "OK: everything is OK.";
  private static final String OK_INCREASE_GEAR = "OK: you may increase the gear.";
  private static final String OK_DECREASE_GEAR = "OK: you may decrease the gear.";
  private static final String NO_INCREASE_SPEED = "Cannot increase speed, increase gear first.";
  private static final String NO_DECREASE_SPEED = "Cannot decrease speed, decrease gear first.";
  private static final String NO_INCREASE_GEAR = "Cannot increase gear, increase speed first.";
  private static final String NO_DECREASE_GEAR = "Cannot decrease gear, decrease speed first.";
  private static final String NOINCREASE_SPEEDMAX = "Cannot increase speed. Reached maximum speed.";
  private static final String NODECREASE_SPEEDMIN = "Cannot decrease speed. Reached minimum speed.";
  private static final String NO_INCREASE_GEAR_MAX = "Cannot increase gear. Reached maximum gear.";
  private static final String NO_DECREASE_GEAR_MIN = "Cannot decrease gear. Reached minimum gear.";

  private final int l1;
  private final int l2;
  private final int l3;
  private final int l4;
  private final int l5;
  private final int h1;
  private final int h2;
  private final int h3;
  private final int h4;
  private final int h5;

  private int gear;
  private int currentSpeed;
  private final int[] lowerSpeed;
  private final int[] higherSpeed;
  private String currentMessage;

  /**
   * Constructs a Regular Manual Transmission with the higher and lower bound
   * overlaying speed ranges for the gears.
   *
   * @param l1 lower speed bound for gear 1.
   * @param h1 higher speed bound for gear 1.
   * @param l2 lower speed bound for gear 2.
   * @param h2 higher speed bound for gear 2.
   * @param l3 lower speed bound for gear 3.
   * @param h3 higher speed bound for gear 3.
   * @param l4 lower speed bound for gear 4.
   * @param h4 higher speed bound for gear 4.
   * @param l5 lower speed bound for gear 5.
   * @param h5 higher speed bound for gear 5.
   * @throws IllegalArgumentException When the speed ranges are not overlapping,
   *                                  the speed does not start at zero, the gear,
   *                                  is lower than 1 or higher than 1, and if the
   *                                  lower speed in the range is gear than the higher
   *                                  speed in the range or the higher speed is less than
   *                                  the lower speed in the same gear range.
   */
  public RegularManualTransmission(int l1, int h1, int l2, int h2, int l3,
                                   int h3, int l4, int h4, int l5, int h5)
          throws IllegalArgumentException {

    this.l1 = l1;
    this.l2 = l2;
    this.l3 = l3;
    this.l4 = l4;
    this.l5 = l5;

    this.h1 = h1;
    this.h2 = h2;
    this.h3 = h3;
    this.h4 = h4;
    this.h5 = h5;



    this.lowerSpeed = new int[]{l1, l2, l3, l4, l5};
    this.higherSpeed = new int[]{h1, h2, h3, h4, h5};


    this.validateSpeedRanges();


    this.gear = 1;
    this.currentMessage = "OK: everything is OK.";
    this.currentSpeed = 0;

  }



  private void validateSpeedRanges() throws IllegalArgumentException {
    validateSpeed(new int[]{l1, l2, l3, l4, l5}, new int[]{h1, h2, h3, h4, h5});
  }


  private void validateSpeed(int[] lowerspeed, int[] highspeed) {
    for (int i = 0; i < lowerspeed.length - 1; i++) {
      if (lowerspeed[i + 1] > highspeed[i]) {
        throw new IllegalArgumentException("speed must be overlapping in adjacent gears");
      }
    }
    for (int i = 0; i < highspeed.length; i++) {
      if (highspeed[i] > highspeed[4]) {
        throw new IllegalArgumentException("high speed cannot be greater than maximum speed");
      }
    }

    if (lowerspeed[0] != 0) {
      throw new IllegalArgumentException("the first lower speed must start with zero");
    }
  }


  private boolean inRange(int gearNum, int speed) {
    gearNum = this.getGear();
    for (int i = 0; i < lowerSpeed.length; i++) {
      if (gear != 5 && lowerSpeed[gearNum] <= speed && speed <= higherSpeed[gearNum - 1]) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getStatus() {
    return currentMessage;
  }


  @Override
  public int getSpeed() {
    return currentSpeed;
  }

  @Override
  public int getGear() {
    return gear;
  }


  @Override
  public ManualTransmission increaseSpeed() {
    int newSpeed = this.currentSpeed + 1;
    this.gear = this.getGear();

    if (currentSpeed > higherSpeed[gear - 1]) {
      return this;
    } else if (newSpeed > higherSpeed[4] && gear == 5) {
      this.currentMessage = RegularManualTransmission.NOINCREASE_SPEEDMAX;
      return this;
    } else if (newSpeed > higherSpeed[gear - 1] && gear < 5) {
      this.currentMessage = RegularManualTransmission.NO_INCREASE_SPEED;
      return this;
    } else if (this.inRange(gear - 1, newSpeed) && this.inRange(gear, newSpeed)) {
      this.currentMessage = OK_INCREASE_GEAR;
    } else if (newSpeed >= lowerSpeed[gear - 1] && newSpeed <= higherSpeed[gear - 1]) {
      this.currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
    }
    this.currentSpeed = newSpeed;

    return this;
  }


  @Override
  public ManualTransmission decreaseSpeed() {
    int newSpeed = this.currentSpeed - 1;
    this.gear = this.getGear();

    if (currentSpeed < lowerSpeed[gear - 1]) {
      currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
      return this;
    } else if (newSpeed < lowerSpeed[0] && gear == 1) {
      this.currentMessage = RegularManualTransmission.NODECREASE_SPEEDMIN;
      return this;
    } else if (newSpeed < lowerSpeed[gear - 1] && gear != 1) {
      this.currentMessage = RegularManualTransmission.NO_DECREASE_SPEED;
      return this;
    } else if (gear > 1 && newSpeed <= higherSpeed[gear - 2]) {
      this.currentMessage =  RegularManualTransmission.OK_DECREASE_GEAR;
      this.currentSpeed = newSpeed;
      return this;
    } else if (newSpeed >= lowerSpeed[gear - 1] && newSpeed <= higherSpeed[gear - 1]) {
      currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
      this.currentSpeed = newSpeed;
      return this;
    }
    currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
    this.currentSpeed = newSpeed;
    return this;
  }

  @Override
  public ManualTransmission increaseGear() {
    this.gear = this.gear + 1;

    if (gear < 5 && currentSpeed >= lowerSpeed[gear]) {
      this.currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
      return this;
    } else if (this.gear == 6) {
      this.currentMessage = RegularManualTransmission.NO_INCREASE_GEAR_MAX;
      this.gear--;
      return this;
      // This might be the issues because it adjust back its orginal gear
    } else if (this.currentSpeed < lowerSpeed[gear - 1]) {
      this.currentMessage = RegularManualTransmission.NO_INCREASE_GEAR;
      this.gear--;
      return this;
    }
    this.currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
    return this;
  }

  @Override
  public ManualTransmission decreaseGear() {
    this.gear = this.gear - 1;

    if (gear > 1 && currentSpeed <= higherSpeed[gear - 1]) {
      this.currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
      return this;
    } else if (gear == 0) {
      this.currentMessage = RegularManualTransmission.NO_DECREASE_GEAR_MIN;
      this.gear++;
      return this;
    } else if (this.currentSpeed > higherSpeed[gear - 1]) {
      this.currentMessage = RegularManualTransmission.NO_DECREASE_GEAR;
      this.gear++;
      return this;
    }

    this.currentMessage = RegularManualTransmission.OK_EVERYTHING_IS_OK;
    return this;
  }

}


