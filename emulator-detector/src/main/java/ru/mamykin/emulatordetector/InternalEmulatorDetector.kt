package ru.mamykin.emulatordetector

interface InternalEmulatorDetector {

    suspend fun getProbability(): EmulatorProbability
}