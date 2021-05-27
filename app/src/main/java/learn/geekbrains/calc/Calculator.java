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
    private static final int CALCULATION_COMPLETED = 1;
    private static final int CALCULATION_NOT_COMPLETED = 0;
    private static final int ERROR_FOUND = 1;
    private static final int ERROR_NOT_FOUND = 0;
    private static final int MEMORY_REGISTER_SAVED = 1;
    private static final int MEMORY_REGISTER_EMPTY = 0;

    private StringBuilder operand1, operand2;
    private double number1, number2, result;
    private String operationSymbol;
    private int operation;
    private double memoryRegister;
    private int memoryRegisterStatus;
    private int calculationStatus;
    private int errorStatus;

    private Callback<String> sendError;
    private Callback<String> sendText;
    private Callback<String> sendMemoryRegisterStatus;

    public Calculator() {
        operation = NO_OPERATION;
        operand1 = new StringBuilder();
        operand2 = new StringBuilder();
        operationSymbol = "";
        calculationStatus = CALCULATION_NOT_COMPLETED;
        errorStatus = ERROR_NOT_FOUND;
    }

    public void inputNumber(String s) {
        if (calculationStatus == CALCULATION_COMPLETED) {
            operand1 = new StringBuilder();
            calculationStatus = CALCULATION_NOT_COMPLETED;
        }
        if (operation == NO_OPERATION) {
            if (s.equals(String.valueOf(DOT)) && operand1.toString().contains(String.valueOf(DOT))) {
                return;
            }
            operand1.append(s);
            if (operand1.toString().equals(String.valueOf(DOT))) operand1.insert(0, ZERO);
            try {
                number1 = Double.parseDouble(operand1.toString());
            } catch (NumberFormatException e) {
                sendError.callback("Разработчик натупил");
                errorStatus = ERROR_FOUND;
            }
        } else {
            if (s.equals(String.valueOf(DOT)) && operand2.toString().contains(String.valueOf(DOT))) {
                return;
            }
            operand2.append(s);
            if (operand2.toString().equals(String.valueOf(DOT))) operand2.insert(0, 0);
            try {
                number2 = Double.parseDouble(operand2.toString());
            } catch (NumberFormatException e) {
                sendError.callback("Разработчик натупил");
                errorStatus = ERROR_FOUND;
            }
        }
        if (errorStatus == ERROR_NOT_FOUND) {
            sendText.callback(getSequence());
        } else {
            restoreCalc();
        }
    }

    private void restoreCalc() {
        errorStatus = ERROR_NOT_FOUND;
        operation = NO_OPERATION;
        operand1 = new StringBuilder();
        operand2 = new StringBuilder();
        operationSymbol = "";
        calculationStatus = CALCULATION_NOT_COMPLETED;
        number2 = 0;
        number1 = 0;
    }

    public String getSequence() {
        String s1, s2;
        if (operand1.length() > 10) {
            s1 = String.format("%e", number1);
        } else {
            if (number1 == (int) number1) {
                s1 = String.valueOf((int) number1);
            } else s1 = String.valueOf(number1);
        }
        if (operand2.toString().equals("")) {
            s2 = operand2.toString();
        } else if (operand2.length() > 10) {
            s2 = String.format("%e", number2);
        } else {
            if (number2 == (int) number2) {
                s2 = String.valueOf((int) number2);
            } else s2 = String.valueOf(number2);
        }
        return s1 + operationSymbol + s2;
    }

    public void inputOperation(int operation, String operationSymbol) {
        calculationStatus = CALCULATION_NOT_COMPLETED;
        if (this.operation != NO_OPERATION) {
            calculate();
        }
        this.operationSymbol = operationSymbol;
        this.operation = operation;
        if (errorStatus == ERROR_NOT_FOUND) {
            sendText.callback(getSequence());
        } else {
            restoreCalc();
        }
    }

    private void calculate() {
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
                    sendError.callback("Деление на ноль!");
                    errorStatus = ERROR_FOUND;
                    return;
                }
                result = number1 / number2;
                break;
        }
        restoreCalc();
        operand1 = new StringBuilder(String.valueOf(result));
        number1 = Double.parseDouble(operand1.toString());
        result = 0;
    }

    public void inputEquals() {
        calculate();
        calculationStatus = CALCULATION_COMPLETED;
        if (errorStatus == ERROR_NOT_FOUND) {
            sendText.callback(getSequence());
        } else {
            restoreCalc();
        }
    }

    public void setSendError(Callback<String> sendError) {
        this.sendError = sendError;
    }

    public void setSendText(Callback<String> sendText) {
        this.sendText = sendText;
    }

    public void setSendMemoryRegisterStatus(Callback<String> sendMemoryRegisterStatus) {
        this.sendMemoryRegisterStatus = sendMemoryRegisterStatus;
    }

    public void memoryClear() {
        memoryRegisterStatus = MEMORY_REGISTER_EMPTY;
        sendMemoryRegisterStatus.callback("");
    }

    public void memorySave() {
        if (number1 != 0) {
            memoryRegisterStatus = MEMORY_REGISTER_SAVED;
            memoryRegister = number1;
            sendMemoryRegisterStatus.callback("M");
        }
    }

    //не доделано
    /*public void memoryRead() {
        if (memoryRegisterStatus == MEMORY_REGISTER_SAVED) {
            if (operation != NO_OPERATION) {
                number1 = memoryRegister;
            } else {
                number2 = memoryRegister;
            }
            sendText.callback(getSequence());
            if (errorStatus == ERROR_NOT_FOUND) {
                sendText.callback(getSequence());
            } else {
                restoreCalc();
            }
        }
    }*/
}
