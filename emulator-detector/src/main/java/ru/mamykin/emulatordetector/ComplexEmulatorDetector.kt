package ru.mamykin.emulatordetector

import android.content.Context
import ru.mamykin.emulatordetector.internal.property.PropertiesEmulatorDetector

class ComplexEmulatorDetector private constructor(
    private val context: Context,
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector {

    override fun getState(): DeviceState {
        return detectors.map { it.getState() }.first()
    }

    class Builder(
        private val context: Context,
    ) {
        private val emulators = mutableListOf<EmulatorDetector>()

        fun checkSensors() = this.apply {
//            emulators.add(PropertiesEmulatorDetector())
        }

        fun checkProperties() = this.apply {
            emulators.add(PropertiesEmulatorDetector(context))
        }

        fun build(): EmulatorDetector = ComplexEmulatorDetector(context, emulators)
    }
}