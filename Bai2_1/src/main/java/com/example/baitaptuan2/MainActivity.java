package com.example.baitaptuan2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout layoutRoot;
    private SwitchCompat switchBackground;

    private final int[] listBackgrounds = {
            R.drawable.anh1,
            R.drawable.anh2,
            R.drawable.anh3
    };

    private int currentBackgroundResId = -1;

    private static final String PREFS = "AppPrefs";
    private static final String KEY_LAST_BG = "LastBackground";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutRoot = findViewById(R.id.layoutRoot);
        switchBackground = findViewById(R.id.switchBackground);

        changeBackgroundOnAppStart();

        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeBackgroundBySwitch(isChecked);
        });
    }

    private void changeBackgroundOnAppStart() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int lastBg = prefs.getInt(KEY_LAST_BG, -1);
        int newBg = getRandomBackgroundExcluding(lastBg);
        setAndSaveBackground(newBg);
    }

    private void changeBackgroundBySwitch(boolean isOn) {
        if (isOn) {
            int newBg = getRandomBackgroundExcluding(currentBackgroundResId);
            setAndSaveBackground(newBg);
        } else {
            setAndSaveBackground(R.drawable.anh1);
        }
    }

    private void setAndSaveBackground(int resId) {
        layoutRoot.setBackgroundResource(resId);
        currentBackgroundResId = resId;

        getSharedPreferences(PREFS, MODE_PRIVATE)
                .edit()
                .putInt(KEY_LAST_BG, resId)
                .apply();
    }

    private int getRandomBackgroundExcluding(int excludeResId) {
        Random random = new Random();
        int newResId;
        do {
            int index = random.nextInt(listBackgrounds.length);
            newResId = listBackgrounds[index];
        } while (newResId == excludeResId);
        return newResId;
    }
}
