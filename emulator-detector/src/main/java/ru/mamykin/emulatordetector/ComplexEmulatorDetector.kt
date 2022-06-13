package ru.mamykin.emulatordetector

import android.content.Context
import android.hardware.Sensor
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector
import ru.mamykin.emulatordetector.internal.sensor.SensorEmulatorDetector

class ComplexEmulatorDetector private constructor(
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector {

    override fun getState(onState: (DeviceState) -> Unit) {
        val results = mutableListOf<DeviceState>()
        for (detector in detectors) {
            detector.getState {
                if (it == DeviceState.EMULATOR) {
                    // cancel other checks
                    onState(it)
                } else {
                    results.add(it)
                    if (results.size == detectors.size) {
                        onState(getState(results))
                    }
                }
            }
        }
    }

    private fun getState(states: List<DeviceState>): DeviceState {
        return if (states.any { it == DeviceState.EMULATOR })
            DeviceState.EMULATOR
        else
            DeviceState.NOT_EMULATOR
    }

    class Builder(
        private val context: Context,
    ) {
        private val emulators = mutableListOf<EmulatorDetector>()

        fun checkSensors() = this.apply {
            emulators.add(SensorEmulatorDetector(context, Sensor.TYPE_ACCELEROMETER, 10, 100))
            emulators.add(SensorEmulatorDetector(context, Sensor.TYPE_GYROSCOPE, 10, 100))
        }

        fun checkProperties() = this.apply {
            emulators.add(PropertiesEmulatorDetector())
        }

        fun build(): EmulatorDetector = ComplexEmulatorDetector(emulators)
    }
}