package ru.mamykin.emulatordetector.internal.sensor

import android.content.Context
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.internal.InternalEmulatorDetector
import ru.mamykin.emulatordetector.VerdictSource

internal class SensorEmulatorDetector(
    context: Context,
    sensorType: Int,
    private val eventsCount: Int,
    private val timeBetweenEvents: Int
) : InternalEmulatorDetector {

    private val sensorDataProcessor = SensorDataValidator()
    private val sensorEventProducer = SensorEventProducer(context, sensorType)

    override fun check(onCheckCompleted: (DeviceState) -> Unit) = runCatching {
        sensorEventProducer.getSensorEvents(eventsCount) {
            val state = if (sensorDataProcessor.isEmulator(it)) {
                DeviceState.Emulator(VerdictSource.Sensors(emptyList()))
            } else {
                DeviceState.NotEmulator
            }
            onCheckCompleted(state)
        }
    }.getOrElse { onCheckCompleted(DeviceState.NotEmulator) }

    override fun cancelCheck() {
        TODO("Not yet implemented")
    }
}