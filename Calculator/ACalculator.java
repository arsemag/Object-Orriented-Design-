package calculator;


/**
 * An abstract class for Simple and Smart Calculator implementation.
 * This class provides common functionality for handling various
 * inputs and performing basic arithmetic operations.
 */
public abstract class ACalculator implements Calculator {
  protected final StringBuilder makeEquation;
  protected final long rightSideNumber;
  protected final long leftSideNumber;
  protected final boolean isThisOperators;
  protected final char operator;
  protected final boolean hasEqualSign;



  protected ACalculator() {
    this.makeEquation = new StringBuilder();

    this.rightSideNumber = 0;
    this.leftSideNumber = 0;
    this.isThisOperators = false;
    this.operator = '0';
    this.hasEqualSign = true;
  }

  protected ACalculator(StringBuilder makeEquation, long leftSideNumber, long rightSideNumber,
                        boolean isThisOperators, char operator, boolean hasEqualSign) {

    this.makeEquation = makeEquation;
    this.rightSideNumber = rightSideNumber;
    this.leftSideNumber = leftSideNumber;
    this.isThisOperators = isThisOperators;
    this.operator = operator;
    this.hasEqualSign = hasEqualSign;
  }

  public abstract Calculator input(char input);

  protected abstract Calculator onlyOperator(char operator);

  protected abstract Calculator onlyNumber(char operator);

  protected abstract Calculator equalSignInput(char operator);

  protected abstract Calculator handleWhenEqualSign(char input);

  protected abstract Calculator createInstance(StringBuilder makeEquation, long leftSideNumber,
                                               long rightSideNumber, boolean isThisOperators,
                                               char operator, boolean hasEqualSign);

  @Override
  public String getResult() {

    return this.makeEquation.toString();
  }


  protected boolean isOverflow(long result) {
    return Integer.MAX_VALUE < result || result < Integer.MIN_VALUE;
  }


  protected String calculateResult(char operator, long leftSideNumber, long rightSideNumber) {
    long result = 0;
    switch (operator) {
      case '+':
        result = leftSideNumber + rightSideNumber;
        break;
      case '-':
        result = leftSideNumber - rightSideNumber;
        break;
      case '*':
        result = leftSideNumber * rightSideNumber;
        break;
      case '=':
        long numberResult = result;
        return Long.toString(numberResult);
      default:
        throw new IllegalArgumentException("Invalid operator");
    }
    if (isOverflow(result)) {
      result = 0;
    }
    return Long.toString(result);
  }


  protected Calculator clearEverything() {
    return createInstance(new StringBuilder(), 0,
            0, false, '0', false);
  }


  protected Calculator handleLeftSideNumber(char input) {
    long charNum = Character.getNumericValue(input);
    long newLeftSideNumber;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());
    boolean newHasEqualSign = false;

    newEquation.append(input);
    newLeftSideNumber = this.leftSideNumber * 10 + charNum;
    if (isOverflow(newLeftSideNumber)) {
      throw new IllegalArgumentException("OVERFLOW!! Too much inputs were entered");
    }
    return createInstance(newEquation, newLeftSideNumber, this.rightSideNumber,
            isThisOperators, this.operator, newHasEqualSign);

  }

  protected Calculator handleRightSideNumber(char input) {
    long charNum = Character.getNumericValue(input);
    long newRightSideNumber;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());


    newEquation.append(input);
    newRightSideNumber = this.rightSideNumber * 10 + charNum;
    if (isOverflow(newRightSideNumber)) {
      throw new IllegalArgumentException("OVERFLOW!! Too much inputs were entered");
    }

    return createInstance(newEquation, this.leftSideNumber, newRightSideNumber,
            this.isThisOperators, this.operator, this.hasEqualSign);
  }

  protected Calculator handleOperatorRightSide(char input) {
    boolean newIsThisOperators = true;
    char newOperator;
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber = this.rightSideNumber;
    StringBuilder newEquation;

    String calculateResult = calculateResult(this.operator, newLeftSideNumber, newRightSideNumber);
    newEquation = new StringBuilder();
    newOperator = input;
    newLeftSideNumber = Integer.parseInt(calculateResult);
    newRightSideNumber = 0;
    newEquation.append(calculateResult).append(input);

    return createInstance(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator, this.hasEqualSign);

  }

  protected boolean validOperator(char input) {
    return (input == '*' || input == '-' || input == '+');
  }

  protected boolean validNumber(char input) {
    return (input >= '0' && input <= '9');
  }

  protected Calculator handleOverFlowInput(char input) {
    return createInstance(makeEquation, leftSideNumber, rightSideNumber,
            isThisOperators, input, hasEqualSign);
  }


  protected Calculator handleAllInputs(char input) {
    if (isOverflow(rightSideNumber) || isOverflow(leftSideNumber)) {
      return handleOverFlowInput(input);
    } else if (validNumber(input)) {
      return onlyNumber(input);
    } else if (validOperator(input)) {
      return onlyOperator(input);
    } else if (input == '=') {
      return equalSignInput(input);
    } else {
      throw new IllegalArgumentException("Invalid operator");
    }
  }

  protected Calculator handleLastOperatorCase(char input) {
    boolean newIsThisOperators = true;
    char newOperator;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());

    newEquation.append(input);
    newOperator = input;

    return createInstance(newEquation, this.leftSideNumber,
            this.rightSideNumber, newIsThisOperators, newOperator, this.hasEqualSign);
  }


  protected Calculator handleSpecialCase(char input) {
    long charNum = Character.getNumericValue(input);
    long newLeftSideNumber;
    long newRightSideNumber;
    StringBuilder newEquation;
    char newOperator;


    newEquation = new StringBuilder();
    newOperator = this.operator;
    newEquation.append(input);
    newLeftSideNumber = charNum;
    newRightSideNumber = 0;

    return createInstance(newEquation, newLeftSideNumber, newRightSideNumber,
            this.isThisOperators, newOperator, this.hasEqualSign);

  }


}
