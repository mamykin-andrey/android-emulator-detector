package ru.mamykin.emulatordetector.internal

import ru.mamykin.emulatordetector.DeviceState

internal interface InternalEmulatorDetector {

    fun getState(): DeviceState
}