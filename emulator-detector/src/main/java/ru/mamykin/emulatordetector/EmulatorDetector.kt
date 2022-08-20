package ru.mamykin.emulatordetector

import android.content.Context
import android.hardware.Sensor
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector
import ru.mamykin.emulatordetector.internal.sensor.SensorEmulatorDetector

abstract class EmulatorDetector {

    abstract suspend fun getState(): DeviceState

    class Builder(
        private val context: Context,
    ) {
        companion object {
            private const val ACCELEROMETER_EVENTS_COUNT = 10
            private const val GYROSCOPE_EVENTS_COUNT = 10
        }

        private val emulators = mutableListOf<EmulatorDetector>()

        fun checkSensors() = this.apply {
            emulators.add(
                SensorEmulatorDetector(
                    context,
                    Sensor.TYPE_ACCELEROMETER,
                    ACCELEROMETER_EVENTS_COUNT,
                )
            )
            emulators.add(
                SensorEmulatorDetector(
                    context,
                    Sensor.TYPE_GYROSCOPE,
                    GYROSCOPE_EVENTS_COUNT,
                )
            )
        }

        fun checkProperties() = this.apply {
            emulators.add(PropertiesEmulatorDetector())
        }

        fun build(): EmulatorDetector = ComplexEmulatorDetector(emulators)
    }
}