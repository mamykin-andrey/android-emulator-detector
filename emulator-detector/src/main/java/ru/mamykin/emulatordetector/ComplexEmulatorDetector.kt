package ru.mamykin.emulatordetector

import kotlin.concurrent.thread

internal class ComplexEmulatorDetector(
    private val detectors: Collection<EmulatorDetector>,
) : EmulatorDetector() {

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
}