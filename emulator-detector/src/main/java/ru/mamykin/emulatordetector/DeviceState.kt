package ru.mamykin.emulatordetector

sealed class DeviceState {

    class Emulator(
        val source: VerdictSource
    ) : DeviceState()

    object MaybeEmulator : DeviceState()

    object NotEmulator : DeviceState()
}