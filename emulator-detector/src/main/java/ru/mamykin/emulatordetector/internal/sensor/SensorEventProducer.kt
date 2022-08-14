package ru.mamykin.emulatordetector.internal.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.CompletableDeferred

internal class SensorEventProducer(
    private val context: Context,
    private val sensorType: Int
) {
    suspend fun getSensorEvents(eventsCount: Int): Result<List<FloatArray>> {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        val sensor = sensorManager?.getDefaultSensor(sensorType)
            ?: return Result.failure(NoSuchSensorException())

        val events = mutableListOf<FloatArray>()
        val eventsDeferred = CompletableDeferred<List<FloatArray>>()

        val eventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                events.add(event.values.copyOf())
                if (events.size == eventsCount) {
                    sensorManager.unregisterListener(this)
                    eventsDeferred.complete(events)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) = Unit
        }
        sensorManager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        return Result.success(eventsDeferred.await())
    }
}