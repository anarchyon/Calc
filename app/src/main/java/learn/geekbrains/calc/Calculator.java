package learn.geekbrains.calc;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    public static final int NO_OPERATION = 0;
    public static final int SUM = 1;
    public static final int DIFF = 2;
    public static final int PROD = 3;
    public static final int DIV = 4;
    public static final char DOT = '.';
    public static final char ZERO = '0';
    public static final char EQUALS = '=';
    private static final int MEMORY_REGISTER_SAVED = 1;
    private static final int MEMORY_REGISTER_EMPTY = 0;
    private static final String ERROR_DIVIDE_BY_ZERO = "Деление на ноль!";

    private int operation;
    private double memoryRegister;
    private int memoryRegisterStatus;
    private String errorStatus;
    private List<Double> operands;

    private Callback<String> sendTextInput;
    private Callback<String> sendTextHistoryAppend;
    private Callback<String> sendMemoryRegisterStatus;
    private Callback<Boolean> sendIsNewOperandWaiting;

    public Calculator() {
        operands = new ArrayList<>();
        operation = NO_OPERATION;
        errorStatus = "";
    }

    private String normalizeString(Double m) {
        String s = String.valueOf(m);
        if (s.length() > 10) {
            s = String.format("%e", m);
        } else {
            if (m == (int) ((double) m)) {
                s = String.valueOf((int) ((double) m));
            }
        }
        return s;
    }

    public void inputOperation(String number, int operation, String operationSymbol) {
        double operand = Double.parseDouble(number);
        operands.add(operand);
        String operandString = normalizeString(operand);
        if (operands.size() > 1) {
            operands.set(0, calculate());
            sendTextInput.callback(normalizeString(operands.get(0)));
            if (!errorStatus.isEmpty()) {
                operands = new ArrayList<>();
                sendTextHistoryAppend.callback(operandString + System.lineSeparator()
                        + EQUALS + errorStatus + System.lineSeparator());
                return;
            }
            operands.remove(1);
        } else {
            sendTextInput.callback(String.valueOf(ZERO));
        }
        this.operation = operation;
        sendIsNewOperandWaiting.callback(true);
        sendTextHistoryAppend.callback(operandString + operationSymbol);
    }

    private double calculate() {
        double result = 0, number1, number2;
        try {
            number1 = operands.get(0);
        } catch (IndexOutOfBoundsException e) {
            number1 = 0;
        }
        try {
            number2 = operands.get(1);
        } catch (IndexOutOfBoundsException e) {
            number2 = 0;
        }
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
                if (number2 == 0) {
                    errorStatus = ERROR_DIVIDE_BY_ZERO;
                    return 0;
                } else result = number1 / number2;
                break;
        }
        return result;
    }

    public void inputEquals(String number) {
        double operand = Double.parseDouble(number);
        String operandString = normalizeString(operand);
        operands.add(operand);
        double result = calculate();
        String resultString = normalizeString(result);
        operands = new ArrayList<>();
        if (errorStatus.isEmpty()) {
            sendTextHistoryAppend.callback(operandString + System.lineSeparator()
                    + EQUALS + resultString + System.lineSeparator());
        } else {
            sendTextHistoryAppend.callback(operandString + System.lineSeparator()
                    + EQUALS + errorStatus + System.lineSeparator());
            errorStatus = "";
        }
        operation = NO_OPERATION;
        sendIsNewOperandWaiting.callback(true);
        sendTextInput.callback(resultString);
    }

    public void setSendTextInput(Callback<String> sendTextInput) {
        this.sendTextInput = sendTextInput;
    }

    public void setSendTextHistoryAppend(Callback<String> sendTextHistoryAppend) {
        this.sendTextHistoryAppend = sendTextHistoryAppend;
    }

    public void setSendMemoryRegisterStatus(Callback<String> sendMemoryRegisterStatus) {
        this.sendMemoryRegisterStatus = sendMemoryRegisterStatus;
    }

    public void setSendIsNewOperandWaiting(Callback<Boolean> sendIsNewOperandWaiting) {
        this.sendIsNewOperandWaiting = sendIsNewOperandWaiting;
    }

    public void memoryClear() {
        memoryRegisterStatus = MEMORY_REGISTER_EMPTY;
        sendMemoryRegisterStatus.callback("");
    }

    public void memorySave(String number) {
        if (!number.equals(String.valueOf(ZERO))) {
            memoryRegisterStatus = MEMORY_REGISTER_SAVED;
            memoryRegister = Double.parseDouble(number);
            sendMemoryRegisterStatus.callback("M");
        }
    }

    public void memoryRead() {
        if (memoryRegisterStatus == MEMORY_REGISTER_SAVED) {
            String savedNumberString = normalizeString(memoryRegister);
            sendTextInput.callback(savedNumberString);
        }
    }

    public void inputPlusMinus(String number) {
        double operand = -Double.parseDouble(number);
        String operandString = normalizeString(operand);
        sendTextInput.callback(operandString);
    }

    public void inputPercent(String number) {
        double operand = Double.parseDouble(number) / 100;
        String operandString = normalizeString(operand);
        sendTextInput.callback(operandString);
    }

    public double getMemoryRegister() {
        return memoryRegister;
    }

    public void setMemoryRegister(double memoryRegister) {
        this.memoryRegister = memoryRegister;
    }

    public int getMemoryRegisterStatus() {
        return memoryRegisterStatus;
    }

    public void setMemoryRegisterStatus(int memoryRegisterStatus) {
        this.memoryRegisterStatus = memoryRegisterStatus;
    }
}
