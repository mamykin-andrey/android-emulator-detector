package ru.mamykin.emulatordetector

import android.content.Context
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector

class ComplexEmulatorDetector private constructor(
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector {

    override fun getState(onState: (DeviceState) -> Unit) {
        detectors.first().getState(onState)
    }

    class Builder(
        private val context: Context,
    ) {
        private val emulators = mutableListOf<EmulatorDetector>()

        fun checkSensors() = this.apply {
        }

        fun checkProperties() = this.apply {
            emulators.add(PropertiesEmulatorDetector())
        }

        fun build(): EmulatorDetector = ComplexEmulatorDetector(emulators)
    }
}