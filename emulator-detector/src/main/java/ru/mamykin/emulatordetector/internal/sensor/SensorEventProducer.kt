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
    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager? }
    private var listener: SensorEventListener? = null

    @Throws(NoSuchSensorException::class)
    fun getSensorEvents(onEvent: (SensorEvent) -> Unit) {
        val sensor = sensorManager?.getDefaultSensor(sensorType) ?: throw NoSuchSensorException()

        listener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) = onEvent(sensorEvent)
            override fun onAccuracyChanged(sensor: Sensor, i: Int) = Unit
        }
        sensorManager!!.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stop() {
        sensorManager!!.unregisterListener(requireNotNull(listener))
    }
}