package ru.mamykin.emulatordetector.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import ru.mamykin.emulatordetector.DeviceState;
import ru.mamykin.emulatordetector.EmulatorDetector;

public class SampleJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        TextView tvVerdict = findViewById(R.id.tv_device_verdict);
        EmulatorDetector emulatorDetector = new EmulatorDetector.Builder(this)
            .checkSensors()
            .checkProperties()
            .build();

        emulatorDetector.check(deviceState -> {
            tvVerdict.setText(convertToDeviceVerdict(deviceState));
            return null;
        });
    }

    @NonNull
    private String convertToDeviceVerdict(@NonNull DeviceState state) {
        @StringRes int verdictRes;
        if (state instanceof DeviceState.Emulator) {
            verdictRes = R.string.verdict_emulator;
        } else if (state instanceof DeviceState.MaybeEmulator) {
            verdictRes = R.string.verdict_maybe_emulator;
        } else {
            verdictRes = R.string.verdict_not_emulator;
        }
        return getString(verdictRes);
    }
}