package ru.mamykin.emulatordetector.internal.sensor

internal class SensorDataValidator {

    companion object {

        private const val INAPPROPRIATE_PERCENT: Double = 0.5
        private const val D_COUNT_IN_PAIR: Int = 2
    }

    fun isEmulator(sensorData: List<FloatArray>): Boolean {
        var dx: Float
        var dy: Float
        var dz: Float

        var lastX = 0f
        var lastY = 0f
        var lastZ = 0f

        var sameEventCount = 0

        for (i in sensorData.indices) {
            if (i == 0) {
                lastX = sensorData[i][0]
                lastY = sensorData[i][1]
                lastZ = sensorData[i][2]
                continue
            }
            dx = sensorData[i][0] - lastX
            dy = sensorData[i][1] - lastY
            dz = sensorData[i][2] - lastZ
            var sameD = 0
            if (dx == 0f) sameD++
            if (dy == 0f) sameD++
            if (dz == 0f) sameD++
            if (sameD >= D_COUNT_IN_PAIR) sameEventCount++
        }
        return sameEventCount.toDouble() / sensorData.size.toDouble() >= INAPPROPRIATE_PERCENT
    }
}