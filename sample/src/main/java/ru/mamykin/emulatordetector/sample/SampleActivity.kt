package ru.mamykin.emulatordetector.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector
import ru.mamykin.emulatordetector.VerdictSource

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val tvVerdict: TextView = findViewById(R.id.tv_device_verdict)
        val emulatorDetector: EmulatorDetector = EmulatorDetector.Builder(this)
            .checkSensors()
            .checkProperties()
            .build()

        lifecycleScope.launch {
            val state = emulatorDetector.getState()
            tvVerdict.text = getDeviceStateDescription(state)
        }
    }

    private fun getDeviceStateDescription(state: DeviceState): String {
        return if (state is DeviceState.Emulator) {
            getDeviceVerdictDescription(state.source)
        } else {
            getString(R.string.verdict_not_emulator)
        }
    }

    private fun getDeviceVerdictDescription(source: VerdictSource): String {
        val (descriptionId, data) = when (source) {
            is VerdictSource.Properties -> {
                R.string.verdict_emulator_properties to source.suspectDeviceProperties.joinToString(", ")
            }
            is VerdictSource.Sensors -> {
                R.string.verdict_emulator_sensors to source.suspectSensorValues.joinToString(", ")
            }
        }
        return getString(descriptionId, data)
    }
}