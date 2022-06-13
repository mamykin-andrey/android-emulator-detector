package ru.mamykin.emulatordetector.internal.property

import ru.mamykin.emulatordetector.BlockingEmulatorDetector
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

internal class PropertiesEmulatorDetector : EmulatorDetector {

    companion object {
        private const val SUSPECT_DETECTORS_COUNT = 3
    }

    private val detectors: List<BlockingEmulatorDetector> = listOf(
        BuildPropsDetector()
    )

    override fun getState(onState: (DeviceState) -> Unit) {
        val results = detectors.map { it.getState() }
        val state = when {
            results.any { it == DeviceState.EMULATOR } -> DeviceState.EMULATOR
            results.count { it == DeviceState.MAYBE_EMULATOR } >= SUSPECT_DETECTORS_COUNT -> DeviceState.MAYBE_EMULATOR
            else -> DeviceState.NOT_EMULATOR
        }
        onState(state)
    }
}