# Android emulator detector
## Description
This is a simple library, which allows to detect device emulators. It has easy API and written on Kotlin with Coroutines.

Thanks for the approaches to https://github.com/framgia/android-emulator-detector and https://github.com/dmitrikudrenko/Emulator-Detector.

## Installation
TBD

## Overview
Example of usage can be found [here](https://github.com/mamykin-andrey/android-emulator-detector/blob/master/sample/src/main/java/ru/mamykin/emulatordetector/sample/SampleActivity.kt).

To get device state you need to set up EmulatorDetector and launch check in a coroutine:
```kotlin
val emulatorDetector: EmulatorDetector = EmulatorDetector.Builder(this)
    .checkSensors() // detector will check device sensors
    .checkProperties() // detector will check device parameters
    .build()

socourineScope.launch {
    val state = emulatorDetector.getState() // suspend function, which calculates and returns device state (emulator/not emulator)
}
```

You can also get an explanation, why the device recognized as an emulator:
```kotlin
if (state is DeviceState.Emulator) {
    val source: VerdictSource = state.source
    when (source) {
        is VerdictSource.Properties -> {
            val suspectDeviceProperties: List<Pair<String, String>> = source.suspectDeviceProperties
        }
        is VerdictSource.Sensors -> {
            val suspectSensorValues: List<FloatArray> = source.suspectSensorValues
        }
    }
}
```

## Collected properties
Library checks following device parameters:
- Build identifier (fingerprint)
- Hardware name
- Device board
- Device model
- Device name
- Device brand
- Device manufacturer
- Product name

And checks following sensors:
- Sensor.TYPE_ACCELEROMETER
- Sensor.TYPE_GYROSCOPE
