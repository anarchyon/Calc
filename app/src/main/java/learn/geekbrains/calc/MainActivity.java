package learn.geekbrains.calc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private MaterialButton key1, key2, key3, key4, key5, key6, key7, key8, key9, key0, keyEquals,
            keyPlus, keyMinus, keyProduction, keyDivide, keyAC, keyMC, keyMR, keyMS, keyDot,
            keyPlusMinus, keyPercent;
    private MaterialTextView textFieldInput, textFieldHistory, textFieldMemory;
    private Calculator calc;
    private boolean isNewOperand;
    private static final String SAVE_TAG = "MainActivityState";
    private static final String SAVED_HISTORY_TAG = "history";
    private static final String SAVED_INPUT_TAG = "input";
    private static final String SAVED_MEMORY_STATE = "memory";
    private static final String SAVE_CALC_TAG = "CalcState";
    private static final String SAVED_MEMORY_REGISTER = "MemoryRegister";
    private static final String SAVED_MEMORY_REGISTER_STATUS = "memRegStatus";
    private static final String ACTION_CALC = "gb.calc";
    private int currentMode;
    private ActivityResultLauncher<Intent> getContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = getIntent();
                // TODO: 03.06.2021
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        currentMode = AppCompatDelegate.getDefaultNightMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent();
            getContent.launch(new Intent(this, SettingsActivity.class));

//            if (currentMode != AppCompatDelegate.MODE_NIGHT_YES) {
//                currentMode = AppCompatDelegate.MODE_NIGHT_YES;
//            } else {
//                currentMode = AppCompatDelegate.MODE_NIGHT_NO;
//            }
        }
        AppCompatDelegate.setDefaultNightMode(currentMode);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
        String history = prefs.getString(SAVED_HISTORY_TAG, "");
        String input = prefs.getString(SAVED_INPUT_TAG, "0");
        String memory = prefs.getString(SAVED_MEMORY_STATE, "");
        textFieldHistory.setText(history);
        textFieldInput.setText(input);
        textFieldMemory.setText(memory);
        SharedPreferences calcPrefs = getSharedPreferences(SAVE_CALC_TAG, MODE_PRIVATE);
        String memoryRegister = calcPrefs.getString(SAVED_MEMORY_STATE, "0");
        int memoryRegisterStatus = calcPrefs.getInt(SAVED_MEMORY_REGISTER_STATUS, 0);
        calc.setMemoryRegister(Double.parseDouble(memoryRegister));
        calc.setMemoryRegisterStatus(memoryRegisterStatus);
    }

    @Override
    protected void onStop() {
        saveState();
        super.onStop();
    }

    private void initView() {
        isNewOperand = true;
        textFieldInput = findViewById(R.id.text_field_input);
        textFieldHistory = findViewById(R.id.text_field_history);
        textFieldHistory.setMovementMethod(new ScrollingMovementMethod());
        textFieldMemory = findViewById(R.id.text_field_memory);
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
        keyPlusMinus = findViewById(R.id.key_plus_minus);
        keyPercent = findViewById(R.id.key_percent);
        initClickListeners();
    }

    private void initClickListeners() {
        keyAC.setOnClickListener(b -> setStartingPosition());

        keyDot.setOnClickListener(b -> handleDotButton());

        key0.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key1.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key2.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key3.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key4.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key5.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key6.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key7.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key8.setOnClickListener(b -> handleNumButton((MaterialButton) b));
        key9.setOnClickListener(b -> handleNumButton((MaterialButton) b));

        keyPlus.setOnClickListener(b -> handleOperationButton((MaterialButton) b, Calculator.SUM));
        keyMinus.setOnClickListener(b -> handleOperationButton((MaterialButton) b, Calculator.DIFF));
        keyProduction.setOnClickListener(b -> handleOperationButton((MaterialButton) b, Calculator.PROD));
        keyDivide.setOnClickListener(b -> handleOperationButton((MaterialButton) b, Calculator.DIV));

        keyEquals.setOnClickListener(b -> handleEqualsButton());

        keyMC.setOnClickListener(b -> handleMCButton());
        keyMS.setOnClickListener(b -> handleMSButton());
        keyMR.setOnClickListener(b -> handleMRButton());

        if (keyPercent != null) {
            keyPercent.setOnClickListener(b -> handlePercentButton());
        }
        if (keyPlusMinus != null) {
            keyPlusMinus.setOnClickListener(b -> handlePlusMinusButton());
        }
    }

    private void setStartingPosition() {
        calc = new Calculator();
        calc.setSendTextInput(this::insertTextInputFromCalculator);
        calc.setSendTextHistoryAppend(this::insertHistoryFromCalculator);
        calc.setSendMemoryRegisterStatus(this::showMemoryRegisterStatus);
        calc.setSendIsNewOperandWaiting(this::changeIsNewOperand);
        textFieldInput.setText(String.valueOf(Calculator.ZERO));
        textFieldHistory.setText("");
    }

    private void changeIsNewOperand(Boolean isNewOperand) {
        this.isNewOperand = isNewOperand;
    }

    private void insertTextInputFromCalculator(String s) {
        textFieldInput.setText(s);
    }

    private void insertHistoryFromCalculator(String s) {
        textFieldHistory.append(s);
    }

    private void showMemoryRegisterStatus(String s) {
        textFieldMemory.setText(s);
    }

    private void handleNumButton(MaterialButton b) {
        if (isNewOperand) {
            textFieldInput.setText(String.valueOf(Calculator.ZERO));
            isNewOperand = false;
        }
        textFieldInput.append(b.getText());
        String text = textFieldInput.getText().toString();
        if (text.length() > 1 && text.charAt(0) == Calculator.ZERO && text.charAt(1) != Calculator.DOT) {
            textFieldInput.setText(text.substring(1));
        }
    }

    private void handleDotButton() {
        if (isNewOperand) {
            textFieldInput.setText(String.valueOf(Calculator.ZERO));
            isNewOperand = false;
        }
        textFieldInput.append(String.valueOf(Calculator.DOT));
    }

    private void handleOperationButton(MaterialButton b, int operation) {
        calc.inputOperation(textFieldInput.getText().toString(), operation, b.getText().toString());
        saveState();
    }

    private void handleEqualsButton() {
        calc.inputEquals(textFieldInput.getText().toString());
        saveState();
    }

    private void saveState() {
        SharedPreferences prefs = getSharedPreferences(SAVE_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SAVED_HISTORY_TAG, textFieldHistory.getText().toString());
        editor.putString(SAVED_INPUT_TAG, textFieldInput.getText().toString());
        editor.putString(SAVED_MEMORY_STATE, textFieldMemory.getText().toString());
        editor.apply();
        SharedPreferences calcPrefs = getSharedPreferences(SAVE_CALC_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = calcPrefs.edit();
        editor1.putString(SAVED_MEMORY_STATE, String.valueOf(calc.getMemoryRegister()));
        editor1.putInt(SAVED_MEMORY_REGISTER_STATUS, calc.getMemoryRegisterStatus());
        editor1.apply();
    }

    private void handleMCButton() {
        calc.memoryClear();
    }

    private void handleMSButton() {
        calc.memorySave(textFieldInput.getText().toString());
    }

    private void handleMRButton() {
        calc.memoryRead();
    }

    private void handlePlusMinusButton() {
        calc.inputPlusMinus(textFieldInput.getText().toString());
    }

    private void handlePercentButton() {
        calc.inputPercent(textFieldInput.getText().toString());
    }
}