package ru.mamykin.emulatordetector

sealed class DeviceState {

    class Emulator(
        val source: VerdictSource
    ) : DeviceState()

    object NotEmulator : DeviceState()
}