
package calculator;

/**
 * A Calculator that only supports three arithmetic operations:
 * addition, subtraction, and multiplication. It only allows one at
 * a time character input while displaying the current state of the
 * equation.
 */
public class SimpleCalculator implements Calculator {
  private final StringBuilder makeEquation;
  private final long rightSideNumber;
  private final long leftSideNumber;
  private final boolean isThisOperators;
  private final char operator;


  /**
   * Constructions a Simple Calculator with no inputs.
   */
  public SimpleCalculator() {
    this.makeEquation = new StringBuilder();

    this.rightSideNumber = 0;
    this.leftSideNumber = 0;
    this.isThisOperators = false;
    this.operator = '0';
  }

  private SimpleCalculator(StringBuilder makeEquation, long leftSideNumber, long rightSideNumber,
                           boolean isThisOperators, char operator) {

    this.makeEquation = makeEquation;
    this.rightSideNumber = rightSideNumber;
    this.leftSideNumber = leftSideNumber;
    this.isThisOperators = isThisOperators;
    this.operator = operator;
  }

  private Calculator onlyNumber(char input) {
    long charNum = Character.getNumericValue(input);
    long newLeftSideNumber = leftSideNumber;
    long newRightSideNumber = rightSideNumber;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());
    char newOperator = operator;


    if (this.operator == '=') {
      newEquation = new StringBuilder();
      newOperator = operator;
      newEquation.append(input);
      newLeftSideNumber = charNum;
    } else if (!isThisOperators) {
      newEquation.append(input);
      newLeftSideNumber = this.leftSideNumber * 10 + charNum;
      if (isOverflow(newLeftSideNumber)) {
        throw new IllegalArgumentException("OVERFLOW!! Too much inputs were entered");
      }
    } else {
      newEquation.append(input);
      newRightSideNumber = this.rightSideNumber * 10 + charNum;
      if (isOverflow(newRightSideNumber)) {
        throw new IllegalArgumentException("OVERFLOW!! Too much inputs were entered");
      }
    }
    return new SimpleCalculator(newEquation, newLeftSideNumber, newRightSideNumber,
            isThisOperators, newOperator);
  }


  private Calculator onlyOperator(char input) {
    boolean newIsThisOperators = true;
    char newOperator = this.operator;
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber = this.rightSideNumber;
    StringBuilder newEquation = new StringBuilder(makeEquation.toString());


    if (isThisOperators) {
      String caluateResult = calculateResult(operator);
      newEquation = new StringBuilder();
      newOperator = input;
      newLeftSideNumber = Integer.parseInt(caluateResult);
      newRightSideNumber = 0;
      newEquation.append(caluateResult).append(input);
    } else {
      newEquation.append(input);
      newOperator = input;
    }

    return new SimpleCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator);
  }

  private Calculator equalSignInput(char input) {
    StringBuilder newEquation = new StringBuilder();
    long newLeftSideNumber = this.leftSideNumber;
    long newRightSideNumber = this.rightSideNumber;
    boolean newIsThisOperators = this.isThisOperators;
    char newOperator = this.operator;

    String calculateResult = calculateResult(newOperator);
    newEquation = new StringBuilder();
    newEquation.append(calculateResult);
    newLeftSideNumber = Integer.parseInt(calculateResult);
    newRightSideNumber = 0;
    newIsThisOperators = false;
    newOperator = '=';

    return new SimpleCalculator(newEquation, newLeftSideNumber,
            newRightSideNumber, newIsThisOperators, newOperator);
  }


  @Override
  public Calculator input(char input) {
    boolean validNumber = (input >= '0' && input <= '9');
    boolean validOperator = (input == '*' || input == '-' || input == '+');

    if (input == 'C') {
      return this.clearEverything();
    } else if (validOperator && (this.makeEquation.length() == 0
            || makeEquation.charAt(this.makeEquation.length() - 1) == '-'
            || makeEquation.charAt(this.makeEquation.length() - 1) == '+'
            || makeEquation.charAt(this.makeEquation.length() - 1) == '*')) {
      throw new IllegalArgumentException("Must put in a number before an operator");
    } else if (input == '=' && this.operator == '=') {
      return this;
    } else if (isOverflow(rightSideNumber) || isOverflow(leftSideNumber)) {
      return new SimpleCalculator(makeEquation, leftSideNumber, rightSideNumber,
              isThisOperators, input);
    } else if (validNumber) {
      return onlyNumber(input);
    } else if (validOperator) {
      return onlyOperator(input);
    } else if (input == '=') {
      return equalSignInput(input);
    } else {
      throw new IllegalArgumentException("Invalid operator");
    }
  }


  private boolean isOverflow(long result) {
    return Integer.MAX_VALUE < result || result < Integer.MIN_VALUE;
  }


  private String calculateResult(char operator) {
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


  private Calculator clearEverything() {
    long newLeftNumber = 0;
    long newRightNumber = 0;
    StringBuilder newEquation = new StringBuilder();
    boolean newIsThisOperators = false;
    char newOperator = '0';

    return new SimpleCalculator(newEquation, newLeftNumber, newRightNumber,
            newIsThisOperators, newOperator);
  }


  @Override
  public String getResult() {
    return this.makeEquation.toString();
  }


}
