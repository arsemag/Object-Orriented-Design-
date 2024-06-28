package calculator;

/**
 *  A computing machine that allows for different math problems to be solved.
 */
public interface Calculator {


  /**
   *  Takes an input of the user to be put into the calculator to be solved.
   * @param input The character that the user decides to put in the information
   * @return A Calculator that is processing the input.
   */
  public Calculator input(char input);


  /**
   * The current result displayed on the Calculator.
   * @return A String that shows the current result.
   */
  public String getResult();

}
