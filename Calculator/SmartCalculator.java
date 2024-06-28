package calculator;

/**
 * A Calculator that only supports three arithmetic operations:
 * addition, subtraction, and multiplication. It only allows one at
 * a time character input while displaying the current state of the
 * equation. Additionally, the final operator and operand will be
 * applied again when multiple equal signs are input. Furthermore, the
 * addition operation can be entered first and there can be
 * multiple operations in the equation.
 */

public class SmartCalculator extends ACalculator {
  private boolean hasEqualSign;


  /**
   * Constructions a Smart Calculator with no inputs.
   */
  public SmartCalculator() {
    super();
  }

  private SmartCalculator(StringBuilder makeEquation, long leftSideNumber, long rightSideNumber,
                          boolean isThisOperators, char operator, boolean hasEqualSign) {

    super(makeEquation, leftSideNumber, rightSideNumber, isThisOperators, operator, hasEqualSign);

    this.hasEqualSign = hasEqualSign;

  }

  @Override
  protected Calculator createInstance(StringBuilder makeEquation, long leftSideNumber,
                                      long rightSideNumber, boolean isThisOperators, char operator,
                                      boolean hasEqualSign) {
    return new SmartCalculator(makeEquation, leftSideNumber, rightSideNumber, isThisOperators,
            operator, hasEqualSign);
  }


  @Override
  public Calculator input(char input) {
    if (input == 'C') {
      return this.clearEverything();
    } else if (this.makeEquation.length() == 0 && (input == '-' || input == '*' || input == '=')) {
      throw new IllegalArgumentException("Must put in a number before an operator");
    } else {
      return handleAllInputs(input);
    }

  }

  @Override
  protected Calculator onlyNumber(char input) {
    if (this.hasEqualSign && input != '=') {
      return handleSpecialCase(input);
    } else if (!this.isThisOperators) {
      return handleLeftSideNumber(input);
    } else {
      return handleRightSideNumber(input);
    }
  }


  @Override
  protected Calculator onlyOperator(char input) {
    if (makeEquation.length() == 0 && input == '+') {
      return handleAdditionSignAtTheBeginning(input);
    } else if (hasEqualSign) {
      return handleNewEquationAfterEqualSign(input);
    } else if (isThisOperators) {
      return handleOperatorRightSide(input);
    } else {
      return handleLastOperatorCase(input);
    }

  }

  @Override
  protected Calculator equalSignInput(char input) {
    if (!isThisOperators) {
      return handleEqualSignAfterNumber();
    } else if (this.makeEquation.charAt(makeEquation.length() - 1) == '-'
            || this.makeEquation.charAt(makeEquation.length() - 1) == '+'
            || this.makeEquation.charAt(makeEquation.length() - 1) == '*') {

      return handleEqualSignAfterOperator(input);
    } else {
      return handleWhenEqualSign(input);
    }

  }

  @Override
  protected Calculator handleWhenEqualSign(char input) {
    StringBuilder newEquation;
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber = this.rightSideNumber;
    boolean newIsThisOperators;
    char newOperator = this.operator;
    boolean newHasEqualSign;

    String calculateResult = calculateResult(newOperator, newLeftSideNumber, newRightSideNumber);
    newEquation = new StringBuilder();
    newEquation.append(calculateResult);
    newLeftSideNumber = Integer.parseInt(calculateResult);
    newIsThisOperators = true;
    newHasEqualSign = true;

    return new SmartCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator, newHasEqualSign);
  }


  private Calculator handleNewEquationAfterEqualSign(char input) {
    boolean newIsThisOperators = true;
    char newOperator;
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber;
    StringBuilder newEquation;
    boolean newHasEqualSign;


    newEquation = new StringBuilder();
    newOperator = input;
    newEquation.append(newLeftSideNumber);
    newRightSideNumber = 0;
    newEquation.append(input);
    newHasEqualSign = false;

    return new SmartCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator, newHasEqualSign);
  }

  private Calculator handleAdditionSignAtTheBeginning(char input) {
    boolean newIsThisOperators = true;
    char newOperator;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());
    boolean newHasEqualSign = this.hasEqualSign;

    newOperator = input;

    return new SmartCalculator(newEquation, this.leftSideNumber,
            this.rightSideNumber, newIsThisOperators, newOperator, newHasEqualSign);
  }


  private Calculator handleEqualSignAfterNumber() {
    StringBuilder newEquation;
    long newLeftSideNumber = this.leftSideNumber;
    boolean newHasEqualSign;

    newEquation = new StringBuilder();
    newEquation.append(newLeftSideNumber);
    newHasEqualSign = true;

    return new SmartCalculator(newEquation, newLeftSideNumber,
            this.rightSideNumber, this.isThisOperators, this.operator, newHasEqualSign);
  }


  private Calculator handleEqualSignAfterOperator(char input) {
    StringBuilder newEquation = new StringBuilder();
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber;
    boolean newIsThisOperators;
    char newOperator;
    boolean newHasEqualSign;

    newRightSideNumber = newLeftSideNumber;
    String calculateResult = calculateResult(this.operator, newLeftSideNumber, newLeftSideNumber);
    newOperator = this.operator;
    newEquation.append(calculateResult);
    newLeftSideNumber = Integer.parseInt(calculateResult);
    newIsThisOperators = true;
    newHasEqualSign = true;

    return new SmartCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator, newHasEqualSign);
  }


}
