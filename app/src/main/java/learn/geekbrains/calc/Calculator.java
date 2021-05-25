package learn.geekbrains.calc;

import android.util.Log;

import java.text.DecimalFormat;

public class Calculator {
    public static final int NO_OPERATION = 0;
    public static final int SUM = 1;
    public static final int DIFF = 2;
    public static final int PROD = 3;
    public static final int DIV = 4;
    public static final char DOT = '.';
    public static final char ZERO = '0';


    private StringBuilder sequence, operand1, operand2;
    private double number1, number2, result;
    private int operation;
    private String operationSymbol;

    public Calculator() {
        operation = 0;
        operand1 = new StringBuilder();
        operand2 = new StringBuilder();
        operationSymbol = "";
    }

    public boolean setNumber1() {
        try {
            number1 = Double.parseDouble(sequence.toString());
            return true;
        } catch (NumberFormatException e) {
            setOperation(ZERO);
            sequence = new StringBuilder(ZERO);
            return false;
        }
    }

    public void setNumber2(String number2) {

    }

    public void inputNumber(String s) {
        if (operation == 0) {
            appendOperand(operand1, s);
        } else {
            appendOperand(operand2, s);
        }
    }

    private void appendOperand(StringBuilder operand, String s) {
        if (s.equals(String.valueOf(DOT)) && operand.toString().contains(String.valueOf(DOT)))
            return;
        operand.append(s);
        normalizeIncompleteOperand(operand);
    }

    private void normalizeIncompleteOperand(StringBuilder operand) {
        if (operand.charAt(0) == DOT) {
            operand.insert(0, ZERO);
        } else if (operand.length() > 1 && operand.charAt(0) == ZERO && operand.charAt(1) != DOT) {
            operand.delete(0, 1);
        }
    }

    protected StringBuilder normalizeCompleteOperand(StringBuilder operand) {
        if (operand.toString().equals("")) {
            operand.append(ZERO);
        }
        String s = operand.toString();
        StringBuilder result = new StringBuilder();
        if (s.contains(String.valueOf(DOT))) {
            result.append(s.replaceAll("0*$|\\.0*$", ""));
        } else {
            result.append(s);
        }
        return result;
    }

    public double getResult() {
        return result;
    }

    public int getOperation() {
        return operation;
    }

    public StringBuilder getSequence() {
        StringBuilder sequence = new StringBuilder(operand1);
        return sequence.append(operationSymbol).append(operand2);
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public double getNumber1() {
        return number1;
    }

    public void setOperationSymbol(String operationSymbol) {
        this.operationSymbol = operationSymbol;
    }

    public void inputDot() {
    }

    public void inputOperation(int operation, String operationSymbol) {
        if (this.operation != 0) {
            calculate();
        }
        this.operationSymbol = operationSymbol;
        this.operation = operation;
        operand1 = normalizeCompleteOperand(operand1);
    }

    private void calculate() {
        operand1 = normalizeCompleteOperand(operand1);
        operand2 = normalizeCompleteOperand(operand2);
        number1 = Double.parseDouble(operand1.toString());
        number2 = Double.parseDouble(operand2.toString());
        switch (operation) {
            case SUM:
                result = number1 + number2;
                break;
            case DIFF:
                result = number1 - number2;
                break;
            case PROD:
                result = number1 * number2;
                break;
            case DIV:
                result = number1 / number2;
                break;
        }
        operand1 = new StringBuilder(String.valueOf(result));
        operand2 = new StringBuilder();
        operation = 0;
        operationSymbol = "";
        operand1 = normalizeCompleteOperand(operand1);
    }

    private void normalizeResult() {
        //operand1
    }

    public void inputEquals() {
        calculate();
    }

    public void inputAC() {
    }

    public StringBuilder getOperand1() {
        return operand1;
    }

    public void setOperand1(StringBuilder operand1) {
        this.operand1 = operand1;
    }
}
