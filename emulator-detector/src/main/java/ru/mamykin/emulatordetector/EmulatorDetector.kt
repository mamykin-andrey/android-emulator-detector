package ru.mamykin.emulatordetector

import androidx.annotation.AnyThread

interface EmulatorDetector {

    suspend fun checkDevice(): DeviceState

    @AnyThread
    fun checkDeviceCompat(listener: DeviceCheckedListener): DeviceState

    fun cancelCheck()

    interface DeviceCheckedListener {

        fun onDeviceChecked(probability: DeviceState)
    }
}