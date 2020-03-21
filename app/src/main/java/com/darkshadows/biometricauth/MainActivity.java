package com.darkshadows.biometricauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Switch switch1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";
    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Executor executor = Executors.newSingleThreadExecutor();
        final FragmentActivity activity = this;

        switch1 = findViewById(R.id.sDisable);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    System.exit(0);
                }

                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    System.exit(0);
                }
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateViews();

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Please scan using a registered finger")
                .setNegativeButtonText("Exit")
                .build();

        if (switchOnOff) {
            biometricPrompt.authenticate(promptInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        Executor executor = Executors.newSingleThreadExecutor();
        final FragmentActivity activity = this;

        switch1 = (Switch) findViewById(R.id.sDisable);

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    System.exit(0);
                }

                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    System.exit(0);
                }
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        loadData();
        updateViews();

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Please scan using a registered finger")
                .setNegativeButtonText("Exit")
                .build();

        if (switchOnOff) {
            biometricPrompt.authenticate(promptInfo);
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();

        if (!switchOnOff) {
            Toast.makeText(this, "Fingerprint Enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fingerprint Disabled", Toast.LENGTH_SHORT).show();
        }

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, true);

    }

    public void updateViews() {
        switch1.setChecked(switchOnOff);
    }

}