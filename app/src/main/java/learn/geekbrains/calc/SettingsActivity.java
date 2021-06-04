package learn.geekbrains.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    private int themeMode;
    private MaterialButtonToggleGroup toggleThemeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();

        Intent inputIntent = getIntent();
        setToggleThemeGroup(inputIntent);

        findViewById(R.id.apply_settings).setOnClickListener(b -> {
            if (toggleThemeGroup.getCheckedButtonId() != View.NO_ID) {
                Intent intent = new Intent();
                intent.putExtra(SettingsActivityContract.EXTRA_THEME_MODE, themeMode);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }

    private void setToggleThemeGroup(Intent inputIntent) {
        if (inputIntent != null) {
            themeMode = inputIntent.getIntExtra(SettingsActivityContract.EXTRA_CURRENT_THEME_MODE,
                    AppCompatDelegate.MODE_NIGHT_NO);
            if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) {
                toggleThemeGroup.check(R.id.dark_theme);
            } else toggleThemeGroup.check(R.id.light_theme);
        }
    }

    private void initViews() {
        toggleThemeGroup = findViewById(R.id.toggle_theme);

        toggleThemeGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.dark_theme) {
                themeMode = AppCompatDelegate.MODE_NIGHT_YES;
            } else if (checkedId == R.id.light_theme) {
                themeMode = AppCompatDelegate.MODE_NIGHT_NO;
            }
        });
    }
}
