package learn.geekbrains.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private MaterialButton key1, key2, key3, key4, key5, key6, key7, key8, key9, key0, keyEquals,
            keyPlus, keyMinus, keyProduction, keyDivide, keyAC, keyMC, keyMR, keyMS, keyDot;
    private MaterialButton[] numbersButton = new MaterialButton[10];
    private TextInputEditText textField;
    private StringBuilder num;
    private String num1, num2;
    private static final int SUM = 1;
    private static final int DIFF = 2;
    private static final int PROD = 3;
    private static final int DIV = 4;
    private int currentOperation;
    private MaterialButton lastButton;
    private Calculator calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        textField = findViewById(R.id.text_field_result);
        setStartingPosition();
        keyDot = findViewById(R.id.key_dot);
        key0 = findViewById(R.id.key_0);
        key1 = findViewById(R.id.key_1);
        key2 = findViewById(R.id.key_2);
        key3 = findViewById(R.id.key_3);
        key4 = findViewById(R.id.key_4);
        key5 = findViewById(R.id.key_5);
        key6 = findViewById(R.id.key_6);
        key7 = findViewById(R.id.key_7);
        key8 = findViewById(R.id.key_8);
        key9 = findViewById(R.id.key_9);
        keyEquals = findViewById(R.id.key_equal);
        keyPlus = findViewById(R.id.key_plus);
        keyMinus = findViewById(R.id.key_minus);
        keyProduction = findViewById(R.id.key_production);
        keyDivide = findViewById(R.id.key_divide);
        keyAC = findViewById(R.id.key_AC);
        keyMR = findViewById(R.id.key_MR);
        keyMS = findViewById(R.id.key_MS);
        keyMC = findViewById(R.id.key_MC);
        initClickListeners();
    }

    private void initClickListeners() {
        keyAC.setOnClickListener(b -> setStartingPosition());

        keyDot.setOnClickListener(b -> dotHandler());

        key0.setOnClickListener(b -> numHandler((MaterialButton) b));
        key1.setOnClickListener(b -> numHandler((MaterialButton) b));
        key2.setOnClickListener(b -> numHandler((MaterialButton) b));
        key3.setOnClickListener(b -> numHandler((MaterialButton) b));
        key4.setOnClickListener(b -> numHandler((MaterialButton) b));
        key5.setOnClickListener(b -> numHandler((MaterialButton) b));
        key6.setOnClickListener(b -> numHandler((MaterialButton) b));
        key7.setOnClickListener(b -> numHandler((MaterialButton) b));
        key8.setOnClickListener(b -> numHandler((MaterialButton) b));
        key9.setOnClickListener(b -> numHandler((MaterialButton) b));

        keyPlus.setOnClickListener(b -> operationHandler((MaterialButton) b, Calculator.SUM));
        keyMinus.setOnClickListener(b -> operationHandler((MaterialButton) b, Calculator.DIFF));
        keyProduction.setOnClickListener(b -> operationHandler((MaterialButton) b, Calculator.PROD));
        keyDivide.setOnClickListener(b -> operationHandler((MaterialButton) b, Calculator.DIV));

        keyEquals.setOnClickListener(b -> resultHandler());
    }

    private void setStartingPosition() {
        calc = new Calculator();
        textField.setText(String.valueOf(Calculator.ZERO));
    }

    private void numHandler(MaterialButton b) {
        calc.inputNumber(b.getText().toString());
        textField.setText(calc.getSequence());
    }

    private void dotHandler() {
        calc.inputNumber(String.valueOf(Calculator.DOT));
        textField.setText(calc.getSequence());
    }

    private void operationHandler(MaterialButton b, int operation) {
        calc.inputOperation(operation, b.getText().toString());
        textField.setText(calc.getSequence());
    }

    private void resultHandler() {
        calc.inputEquals();
        textField.setText(calc.getSequence());
    }
}