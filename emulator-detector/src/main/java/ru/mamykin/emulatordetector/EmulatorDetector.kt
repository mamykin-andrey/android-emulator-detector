package ru.mamykin.emulatordetector

interface EmulatorDetector {

    fun check(onCheckCompleted: (DeviceState) -> Unit)

    fun cancelCheck()
}