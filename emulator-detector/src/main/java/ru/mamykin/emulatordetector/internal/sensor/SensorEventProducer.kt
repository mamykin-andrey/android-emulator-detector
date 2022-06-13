package ru.mamykin.emulatordetector.internal.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

internal class SensorEventProducer(
    private val context: Context,
    private val sensorType: Int
) {
    @Throws(NoSuchSensorException::class)
    fun getSensorEvents(eventsCount: Int, onEvents: (List<FloatArray>) -> Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        val sensor = sensorManager?.getDefaultSensor(sensorType) ?: throw NoSuchSensorException()
        val events = mutableListOf<FloatArray>()

        val eventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                events.add(event.values.copyOf())
                if (events.size == eventsCount) {
                    sensorManager.unregisterListener(this)
                    onEvents(events)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) = Unit
        }
        sensorManager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
}