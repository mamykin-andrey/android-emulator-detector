package ru.mamykin.emulatordetector

// TODO: Add verdict description
interface EmulatorDetector {

    fun getState(onState: (DeviceState) -> Unit)
}