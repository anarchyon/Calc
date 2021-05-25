package learn.geekbrains.calc;

public class Calculator {
    public static final int NO_OPERATION = 0;
    public static final int SUM = 1;
    public static final int DIFF = 2;
    public static final int PROD = 3;
    public static final int DIV = 4;
    public static final char DOT = '.';
    public static final char ZERO = '0';


    private StringBuilder sequence;
    private double number1, number2, result;
    private int operation;

    public Calculator() {
        operation = ZERO;
        sequence = new StringBuilder(ZERO);
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

    public double sum() {
        return 0;
    }

    public double diff() {
        return 0;
    }

    public void appendString(String s) {
        if (s.equals(String.valueOf(DOT)) && sequence.toString().contains(String.valueOf(DOT))) return;
        sequence.append(s);
        if (sequence.charAt(0) == DOT) {
            sequence.insert(0, ZERO);
        } else if (sequence.length() > 1 && sequence.charAt(0) == ZERO && sequence.charAt(1) != DOT) {
            sequence.delete(0, 1);
        }
    }

    public double getResult() {
        return result;
    }

    public int getOperation() {
        return operation;
    }

    public StringBuilder getSequence() {
        return sequence;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public double getNumber1() {
        return number1;
    }
}
