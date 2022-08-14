package ru.mamykin.emulatordetector

import android.content.Context
import android.hardware.Sensor
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector
import ru.mamykin.emulatordetector.internal.sensor.SensorEmulatorDetector

abstract class EmulatorDetector {

    abstract fun check(onCheckCompleted: (DeviceState) -> Unit)

    abstract fun cancelCheck()

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