package ru.mamykin.emulatordetector.sample

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import ru.mamykin.emulatordetector.ComplexEmulatorDetector
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val tvVerdict: TextView = findViewById(R.id.tv_device_verdict)
        val emulatorDetector: EmulatorDetector = ComplexEmulatorDetector.Builder(this)
            .checkSensors()
            .checkProperties()
            .build()

        emulatorDetector.check {
            tvVerdict.text = convertToDeviceVerdict(it)
        }
    }

    private fun convertToDeviceVerdict(state: DeviceState): String {
        @StringRes val verdictRes = when (state) {
            is DeviceState.Emulator -> R.string.verdict_emulator
            is DeviceState.MaybeEmulator -> R.string.verdict_maybe_emulator
            else -> R.string.verdict_not_emulator
        }
        return getString(verdictRes)
    }
}