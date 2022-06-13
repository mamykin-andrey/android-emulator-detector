package ru.mamykin.emulatordetector.internal.sensor

import android.content.Context
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector

internal class SensorEmulatorDetector(
    context: Context,
    sensorType: Int,
    private val eventsCount: Int,
    private val timeBetweenEvents: Int
) : EmulatorDetector {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override fun getState(onState: (DeviceState) -> Unit) = runCatching {
        sensorEventProducer.getSensorEvents(eventsCount) {
            val state = if (sensorDataProcessor.isEmulator(it)) DeviceState.EMULATOR else DeviceState.NOT_EMULATOR
            onState(state)
        }
    }.getOrElse { onState(DeviceState.NOT_EMULATOR) }
}