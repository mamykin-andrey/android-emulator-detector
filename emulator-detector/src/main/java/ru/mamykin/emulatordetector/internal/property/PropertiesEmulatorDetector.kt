package ru.mamykin.emulatordetector.internal.property

import android.os.Build
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.EmulatorDetector
import ru.mamykin.emulatordetector.VerdictSource

internal class PropertiesEmulatorDetector : EmulatorDetector() {

    private val suspectProperties by lazy {
        listOf(
            getEmulatorFingerprintInfo(),
            getEmulatorHardwareInfo(),
            getEmulatorBoardInfo(),
            getEmulatorModelInfo(),
            getEmulatorDeviceInfo(),
            getEmulatorBrandInfo(),
            getEmulatorManufacturerInfo(),
            getEmulatorProductInfo(),
        )
    }

    override suspend fun check(): DeviceState {
        val foundProperties = suspectProperties.filterNotNull()
        return if (foundProperties.isNotEmpty()) {
            DeviceState.Emulator(VerdictSource.Properties(foundProperties))
        } else {
            DeviceState.NotEmulator
        }
    }

    private fun getEmulatorFingerprintInfo(): Pair<String, String>? {
        val fingerprint = Build.FINGERPRINT
        return if (
            fingerprint.containsAny(
                "generic/sdk/generic",
                "generic_x86/sdk_x86/generic_x86",
                "Andy",
                "ttVM_Hdragon",
                "generic_x86_64",
                "generic/google_sdk/generic",
                "vbox86p",
                "generic/vbox86p/vbox86p"
            ) || fingerprint.startsWith("unknown")
        ) "Fingerprint" to fingerprint else null
    }

    private fun getEmulatorHardwareInfo(): Pair<String, String>? {
        val hardware = Build.HARDWARE
        return if (
            hardware.containsAny(
                "nox",
                "ttVM_x86",
                "ranchu"
            ) || hardware in listOf(
                "goldfish",
                "vbox86"
            )
        ) "Hardware" to hardware else null
    }

    private fun getEmulatorBoardInfo(): Pair<String, String>? {
        val board = Build.BOARD
        return if (
            board.contains("unknown")
        ) "Board" to board else null
    }

    private fun getEmulatorModelInfo(): Pair<String, String>? {
        val model = Build.MODEL
        return if (
            model.containsAny(
                "sdk",
                "google_sdk",
                "Emulator",
                "Droid4X",
                "TiantianVM",
                "Andy"
            ) || model in listOf(
                "Android SDK built for x86_64",
                "Android SDK built for x86"
            )
        ) "Model" to model else null
    }

    private fun getEmulatorDeviceInfo(): Pair<String, String>? {
        val device = Build.DEVICE
        return if (
            device.containsAny(
                "generic",
                "generic_x86",
                "Andy",
                "ttVM_Hdragon",
                "Droid4X",
                "nox",
                "generic_x86_64",
                "vbox86p"
            )
        ) "Device" to device else null
    }

    private fun getEmulatorBrandInfo(): Pair<String, String>? {
        val brand = Build.BRAND
        return if (listOf(
                "generic",
                "generic_x86",
                "TTVM"
            ).any { brand == it } || brand.containsAny("Andy")
        ) "Brand" to brand else null
    }

    private fun getEmulatorManufacturerInfo(): Pair<String, String>? {
        val manufacturer = Build.MANUFACTURER
        return if (listOf(
                "unknown",
                "Genymotion"
            ).any { manufacturer == it } || manufacturer.containsAny(
                "Andy",
                "MIT",
                "nox",
                "TiantianVM"
            )
        ) "Manufacturer" to manufacturer else null
    }

    private fun getEmulatorProductInfo(): Pair<String, String>? {
        val product = Build.PRODUCT
        return if (product.containsAny(
                "sdk",
                "Andy",
                "ttVM_Hdragon",
                "google_sdk",
                "Droid4X",
                "nox",
                "sdk_x86",
                "sdk_google",
                "vbox86p"
            )
        ) "Product" to product else null
    }

    private fun String.containsAny(vararg parts: String): Boolean {
        return parts.any { this.contains(it) }
    }
}