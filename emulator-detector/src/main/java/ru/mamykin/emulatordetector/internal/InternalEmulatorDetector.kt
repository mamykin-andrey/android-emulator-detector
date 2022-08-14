package ru.mamykin.emulatordetector.internal

import ru.mamykin.emulatordetector.DeviceState

interface InternalEmulatorDetector {

    fun check(onCheckCompleted: (DeviceState) -> Unit)

    fun cancelCheck()
}