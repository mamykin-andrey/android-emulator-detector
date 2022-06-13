package ru.mamykin.emulatordetector

internal interface BlockingEmulatorDetector {

    fun getState(): DeviceState
}