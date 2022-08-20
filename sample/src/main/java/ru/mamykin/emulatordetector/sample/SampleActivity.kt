package ru.mamykin.emulatordetector.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector
import ru.mamykin.emulatordetector.VerdictSource

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val tvVerdict: TextView = findViewById(R.id.tv_device_verdict)
        val emulatorDetector: EmulatorDetector = EmulatorDetector.Builder(this)
//            .checkSensors()
            .checkProperties()
            .build()

        emulatorDetector.getState { state ->
            tvVerdict.text = getDeviceStateDescription(state)
        }
    }

    private fun getDeviceStateDescription(state: DeviceState): String {
        return when (state) {
            is DeviceState.Emulator -> getDeviceVerdictDescription(state.source)
            is DeviceState.MaybeEmulator -> getString(R.string.verdict_maybe_emulator)
            else -> getString(R.string.verdict_not_emulator)
        }
    }

    private fun getDeviceVerdictDescription(source: VerdictSource): String = when (source) {
        is VerdictSource.Properties -> {
            getString(
                R.string.verdict_emulator_properties,
                source.suspectDeviceProperties.joinToString(", ")
            )
        }
        is VerdictSource.Sensors -> {
            getString(
                R.string.verdict_emulator_sensors,
                source.suspectSensorValues.joinToString(", ")
            )
        }
    }
}