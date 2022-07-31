package ru.mamykin.emulatordetector

sealed class VerdictSource {

    class Properties(
        val suspectDeviceProperties: List<Pair<String, String>>
    ) : VerdictSource()

    class Sensors(
        val suspectSensorValues: List<FloatArray>
    ) : VerdictSource()
}