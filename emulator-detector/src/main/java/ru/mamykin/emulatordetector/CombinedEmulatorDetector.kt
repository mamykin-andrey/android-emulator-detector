package ru.mamykin.emulatordetector

import android.content.Context

class CombinedEmulatorDetector(
    private val context: Context
) : EmulatorDetector {

    override suspend fun getProbability(): DeviceState {
        TODO("Not yet implemented")
    }

    override fun getProbabilityCompat(listener: EmulatorDetector.ProbabilityCalculatedListener): DeviceState {
        TODO("Not yet implemented")
    }
}