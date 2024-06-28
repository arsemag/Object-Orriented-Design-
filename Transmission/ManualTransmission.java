package vehicle;

/**
 * Represents the manual actions a transmission can do when operating a car.
 *
 */

public interface ManualTransmission {
  /**
   * Gets the status that the current manual transmission is in.
   * The status can be one of the following:
   * - "OK: everything is OK.": this is the status if the speed was changed successfully without
   *        changing gears, or the gear was changed successfully without changing speed.
   * - "OK: you may increase the gear.": this is the status if the speed was increased successfully,
   *        but it is now within the range of the next gear
   *        (remember that adjacent gear ranges may overlap)
   * - "OK: you may decrease the gear.": this is the status if the speed was decreased successfully,
   *        but it is now within the range of the previous gear
   *        (remember that adjacent gear ranges may overlap)
   * - "Cannot increase speed, increase gear first.": this is the status if the speed cannot be
   *        increased more unless the gear is increased first. This implies that the intended
   *        speed is too high for the current gear.
   * - "Cannot decrease speed, decrease gear first.": this is the status if the speed cannot be
   *        decreased more unless the gear is decreased first. This implies that the intended speed
   *        is too low for the current gear.
   * - "Cannot increase gear, increase speed first.": this is the status if the gear cannot be
   *        increased more unless the speed is increased first. This implies that the current speed
   *        will be too low for the next gear.
   * - "Cannot decrease gear, decrease speed first.": this is the status if the gear cannot be
   *        decreased more unless the speed is decreased first. This implies that the current speed
   *        will be too high for the previous gear.
   * - "Cannot increase speed. Reached maximum speed.": this is the status if the speed cannot be
   *        increased as it will go beyond the speed limit of the vehicle
   * - "Cannot decrease speed. Reached minimum speed.": this is the status if the speed cannot
   *        be decreased as it is already 0
   * - "Cannot increase gear. Reached maximum gear.": this is the status if the gear cannot be
   *        increased as it is already in gear 5
   * - "Cannot decrease gear. Reached minimum gear.": this is the status if the gear cannot be
   *        decreased as it is already in gear 1
   *
   * @return Returns the status of this transmission as a String.
   */
  String getStatus();

  /**
   *  Gets the current speed of the vehicle.
   * @return The current speed of the vehicle as a whole number.
   */
  int getSpeed();

  /**
   *  Gets the current gear of the vehicle.
   * @return The current gear of the vehicle as a whole number.
   */
  int getGear();

  /**
   * Increases the speed without changing gears.
   * @return If the speed cannot be increase will return the current object
   *         with the same speed.
   */
  ManualTransmission increaseSpeed();


  /**
   * Decreases the speed without changing gears.
   * @return The decrease gear amount without changing the speed.
   *         If the speed cannot be decrease will return the current object
   *         with the same speed.
   */
  ManualTransmission decreaseSpeed();

  /**
   *  Increases the gear without changing the speed.
   * @return The increase gear amount without changing the speed.
   *         If the gear cannot be increase will return the current object
   *         with the same gear.
   */
  ManualTransmission increaseGear();

  /**
   *  Decreases the gear without changing the speed.
   * @return The decrease gear amount without changing the speed.
   *         If the gear cannot be decrease will return the current object
   *         with the same gear.
   */
  ManualTransmission decreaseGear();
}
