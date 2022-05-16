package ru.mamykin.emulatordetector.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.mamykin.emulatordetector.CombinedEmulatorDetector;
import ru.mamykin.emulatordetector.EmulatorDetector;
import ru.mamykin.emulatordetector.DeviceState;

public class SampleCompatActivity extends AppCompatActivity {

    @NonNull
    private EmulatorDetector emulatorDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emulatorDetector = new CombinedEmulatorDetector(this);
        setContentView(R.layout.activity_main);
        TextView tvVerdict = findViewById(R.id.tv_device_verdict);
        emulatorDetector.checkDeviceCompat(probability -> tvVerdict.setText(convertToDeviceVerdict(probability)));
    }

    @NonNull
    private String convertToDeviceVerdict(@NonNull DeviceState probability) {
        int verdictTitle = 0;
        switch (probability) {
            case EMULATOR: {
                verdictTitle = R.string.emulator_most_likely_title;
                break;
            }
            case MAYBE_EMULATOR: {
                verdictTitle = R.string.emulator_maybe_title;
                break;
            }
            case NOT_EMULATOR: {
                verdictTitle = R.string.emulator_unlikely_title;
                break;
            }
        }
        return getString(verdictTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emulatorDetector.cancelCheck();
    }
}