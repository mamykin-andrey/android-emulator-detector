package ru.mamykin.emulatordetector.internal.property

import android.content.Context
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

internal class PropertiesEmulatorDetector(
    context: Context
) : EmulatorDetector {

    companion object {
        private const val SUSPECT_DETECTORS_COUNT = 3
    }

    private val detectors: List<EmulatorDetector> = listOf(
        BuildPropsDetector()
    )

    override fun getState(): DeviceState {
        val detectorResults = detectors.map { it.getState() }
        if (detectorResults.any { it == DeviceState.EMULATOR }) {
            return DeviceState.EMULATOR
        }
        return if (detectorResults.count { it == DeviceState.MAYBE_EMULATOR } >= SUSPECT_DETECTORS_COUNT)
            DeviceState.EMULATOR
        else
            DeviceState.NOT_EMULATOR
    }
}