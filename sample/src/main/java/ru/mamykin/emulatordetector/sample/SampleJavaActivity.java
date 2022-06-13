package ru.mamykin.emulatordetector.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.mamykin.emulatordetector.ComplexEmulatorDetector;
import ru.mamykin.emulatordetector.DeviceState;
import ru.mamykin.emulatordetector.EmulatorDetector;

public class SampleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        TextView tvVerdict = findViewById(R.id.tv_device_verdict);
        EmulatorDetector emulatorDetector = new ComplexEmulatorDetector.Builder(this)
            .checkSensors()
            .checkProperties()
            .build();

        emulatorDetector.getState(deviceState -> {
            tvVerdict.setText(convertToDeviceVerdict(deviceState));
            return null;
        });
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
}