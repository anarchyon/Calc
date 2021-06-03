package learn.geekbrains.calc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    private int themeMode;
    private MaterialButtonToggleGroup toggleGroup;
    private Button buttonLightTheme, buttonDarkTheme;
    private static final String EXTRA_THEME_MODE = "theme_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        setMode(toggleGroup.getCheckedButtonId());


        findViewById(R.id.apply_settings).setOnClickListener(b -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_THEME_MODE, themeMode);

        });
    }

    private void initViews() {
        toggleGroup = findViewById(R.id.toggle_theme);
        buttonLightTheme = findViewById(R.id.light_theme);
        buttonDarkTheme = findViewById(R.id.dark_theme);

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> setMode(toggleGroup.getCheckedButtonId()));
    }

    private void setMode(int themeButtonId) {
        switch (themeButtonId) {
            case R.id.dark_theme:
                themeMode = AppCompatDelegate.MODE_NIGHT_YES;
                break;
            case R.id.light_theme:
                themeMode = AppCompatDelegate.MODE_NIGHT_NO;
                break;
        }
    }
}
