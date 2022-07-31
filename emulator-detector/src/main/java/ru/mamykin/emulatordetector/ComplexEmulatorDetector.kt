package ru.mamykin.emulatordetector

import android.content.Context
import android.hardware.Sensor
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector
import ru.mamykin.emulatordetector.internal.sensor.SensorEmulatorDetector
import kotlin.concurrent.thread

class ComplexEmulatorDetector private constructor(
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector {

    override fun check(onCheckCompleted: (DeviceState) -> Unit) {
        thread {
            val states = mutableListOf<DeviceState>()

            fun onCheckInternalCompleted(state: DeviceState) {
                states.add(state)
                if (state is DeviceState.Emulator) {
                    onCheckCompleted(state)
                    cancelCheck()
                } else if (states.size == detectors.size) {
                    onCheckCompleted(getComplexState(states))
                }
            }

            for (detector in detectors) {
                detector.check { onCheckInternalCompleted(it) }
            }
        }
    }

    override fun cancelCheck() {
        for (detector in detectors) {
            detector.cancelCheck()
        }
    }

    private fun getComplexState(states: List<DeviceState>): DeviceState {
        return states.firstOrNull { it is DeviceState.Emulator }
            ?: DeviceState.NotEmulator
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