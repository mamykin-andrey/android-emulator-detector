package ru.mamykin.emulatordetector.internal.sensor

import android.content.Context
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

internal class SensorEmulatorDetector(
    context: Context,
    private val sensorType: Int,
    private val eventsCount: Int,
    private val timeBetweenEvents: Int
) : EmulatorDetector {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override fun getState(onState: (DeviceState) -> Unit) = runCatching {
        val events = mutableListOf<FloatArray>()
        sensorEventProducer.getSensorEvents {
            events.add(it.values.copyOf())
            if (events.size == eventsCount) {
                val state = if (sensorDataProcessor.isEmulator(events)) {
                    DeviceState.EMULATOR
                } else {
                    DeviceState.NOT_EMULATOR
                }
                onState(state)
                return@getSensorEvents
            }
        }
    }.getOrElse { onState(DeviceState.NOT_EMULATOR) }
}