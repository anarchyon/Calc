package learn.geekbrains.calc;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatDelegate;

import static android.app.Activity.RESULT_OK;

public class SettingsActivityContract extends ActivityResultContract<Integer, Integer> {
    public static final String EXTRA_THEME_MODE = "theme_mode";
    public static final String EXTRA_CURRENT_THEME_MODE = "current_mode";

    @Override
    public Intent createIntent(Context context, Integer input) {
        return new Intent(context, SettingsActivity.class).putExtra(EXTRA_CURRENT_THEME_MODE, input);
    }

    @Override
    public Integer parseResult(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            return intent.getIntExtra(EXTRA_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);
        } else return null;
    }
}
