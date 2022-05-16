package ru.mamykin.emulatordetector.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.mamykin.emulatordetector.CombinedEmulatorDetector
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

class SampleActivity : AppCompatActivity() {

    private val emulatorDetector: EmulatorDetector by lazy {
        CombinedEmulatorDetector(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvVerdict: TextView = findViewById(R.id.tv_device_verdict)
        emulatorDetector.checkDeviceCompat(object : EmulatorDetector.DeviceCheckedListener {
            override fun onDeviceChecked(probability: DeviceState) {
                tvVerdict.text = convertToDeviceVerdict(probability)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        emulatorDetector.cancelCheck()
    }

    private fun convertToDeviceVerdict(probability: DeviceState): String {
        val verdictTitle = when (probability) {
            DeviceState.EMULATOR -> R.string.emulator_most_likely_title
            DeviceState.MAYBE_EMULATOR -> R.string.emulator_maybe_title
            DeviceState.NOT_EMULATOR -> R.string.emulator_unlikely_title
        }
        return getString(verdictTitle)
    }
}