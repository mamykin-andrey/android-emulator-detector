package ru.mamykin.emulatordetector

import android.content.Context
import ru.mamykin.emulatordetector.internal.InternalEmulatorDetector

class CombinedEmulatorDetector private constructor(
    private val context: Context,
    private val detectors: Collection<InternalEmulatorDetector>,
) : EmulatorDetector {

    override fun getState(): DeviceState {
        TODO("Not yet implemented")
    }

    class Builder(
        private val context: Context,
    ) {
        private var checkSensors = false
        private var checkProperties = false

        fun checkSensors() = this.apply {
            checkSensors = true
        }

        fun checkProperties() = this.apply {
            checkProperties = true
        }

        fun build(): EmulatorDetector = CombinedEmulatorDetector(context, emptyList())
    }
}