package ru.mamykin.emulatordetector

interface EmulatorDetector {

    suspend fun getProbability(): EmulatorProbability

    fun getProbabilityCompat(listener: ProbabilityCalculatedListener): EmulatorProbability

    interface ProbabilityCalculatedListener {

        fun onProbabilityCalculated(probability: EmulatorProbability)
    }
}