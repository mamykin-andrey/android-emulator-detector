package ru.mamykin.emulatordetector.sample;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.mamykin.emulatordetector.DeviceState;
import ru.mamykin.emulatordetector.EmulatorDetector;
import ru.mamykin.emulatordetector.VerdictSource;

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
        if (state instanceof DeviceState.Emulator) {
            return getDeviceVerdictDescription(((DeviceState.Emulator) state).getSource());
        } else if (state instanceof DeviceState.MaybeEmulator) {
            return getString(R.string.verdict_maybe_emulator);
        } else if (state instanceof DeviceState.NotEmulator) {
            return getString(R.string.verdict_not_emulator);
        }
        throw new IllegalStateException("Unknown DeviceState type: " + state + "!");
    }

    @NonNull
    private String getDeviceVerdictDescription(@NonNull VerdictSource source) {
        if (source instanceof VerdictSource.Properties) {
            return getString(
                R.string.verdict_emulator_properties,
                TextUtils.join(", ", ((VerdictSource.Properties) source).getSuspectDeviceProperties())
            );
        }
        if (source instanceof VerdictSource.Sensors) {
            return getString(
                R.string.verdict_emulator_sensors,
                TextUtils.join(", ", ((VerdictSource.Sensors) source).getSuspectSensorValues())
            );
        }
        throw new IllegalStateException("Unknown VerdictSource type: " + source + "!");
    }
}