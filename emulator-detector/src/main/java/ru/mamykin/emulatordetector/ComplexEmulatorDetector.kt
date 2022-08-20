package ru.mamykin.emulatordetector

import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

internal class ComplexEmulatorDetector(
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector() {

    override suspend fun getState(): DeviceState {
        return supervisorScope {
            val states = detectors
                .map { async { it.getState() } }
                .map { it.await() }

            states.firstOrNull { it is DeviceState.Emulator }
                ?: DeviceState.NotEmulator
        }
    }
}