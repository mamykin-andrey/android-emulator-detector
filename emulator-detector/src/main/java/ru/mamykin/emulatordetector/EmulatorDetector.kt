package ru.mamykin.emulatordetector

interface EmulatorDetector {

    fun getState(onState: (DeviceState) -> Unit)
}