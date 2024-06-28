package calculator;

/**
 * A Calculator that only supports three arithmetic operations:
 * addition, subtraction, and multiplication. It only allows one at
 * a time character input while displaying the current state of the
 * equation.
 */
public class SimpleCalculator extends ACalculator {


  /**
   * Constructions a Simple Calculator with no inputs.
   */
  public SimpleCalculator() {
    super();
  }

  private SimpleCalculator(StringBuilder makeEquation, long leftSideNumber, long rightSideNumber,
                           boolean isThisOperators, char operator, boolean hasEqualSign) {

    super(makeEquation, leftSideNumber, rightSideNumber, isThisOperators, operator, hasEqualSign);
  }


  @Override
  protected Calculator createInstance(StringBuilder makeEquation, long leftSideNumber,
                                      long rightSideNumber, boolean isThisOperators, char operator,
                                      boolean hasEqualSign) {

    return new SimpleCalculator(makeEquation, leftSideNumber, rightSideNumber,
            isThisOperators, operator, hasEqualSign);
  }

  @Override
  public Calculator input(char input) {
    if (input == 'C') {
      return this.clearEverything();
    } else if (validOperator(input) && (this.makeEquation.length() == 0
            || makeEquation.charAt(this.makeEquation.length() - 1) == '-'
            || makeEquation.charAt(this.makeEquation.length() - 1) == '+'
            || makeEquation.charAt(this.makeEquation.length() - 1) == '*')) {
      throw new IllegalArgumentException("Must put in a number before an operator");
    } else if (input == '=' && this.operator == '=') {
      return this;
    } else {
      return handleAllInputs(input);
    }
  }


  @Override
  protected Calculator onlyNumber(char input) {
    if (this.operator == '=') {
      return handleSpecialCase(input);
    } else if (!isThisOperators) {
      return handleLeftSideNumber(input);
    } else {
      return handleRightSideNumber(input);
    }

  }


  @Override
  protected Calculator onlyOperator(char input) {
    if (isThisOperators) {
      return handleOperatorRightSide(input);
    } else {
      return handleLastOperatorCase(input);
    }
  }


  @Override
  protected Calculator equalSignInput(char input) {
    return handleWhenEqualSign(input);
  }

  @Override
  protected Calculator handleWhenEqualSign(char input) {
    StringBuilder newEquation;
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber = this.rightSideNumber;
    boolean newIsThisOperators;
    char newOperator = this.operator;


    String calculateResult = calculateResult(newOperator, newLeftSideNumber, newRightSideNumber);
    newEquation = new StringBuilder();
    newEquation.append(calculateResult);
    newLeftSideNumber = Integer.parseInt(calculateResult);
    newRightSideNumber = 0;
    newIsThisOperators = false;
    newOperator = '=';


    return new SimpleCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator, this.hasEqualSign);
  }


}
