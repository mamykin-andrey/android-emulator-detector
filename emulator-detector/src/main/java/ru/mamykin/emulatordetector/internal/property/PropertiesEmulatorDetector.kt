package ru.mamykin.emulatordetector.internal.property

import android.os.Build
import ru.mamykin.emulatordetector.DeviceState
import ru.mamykin.emulatordetector.internal.InternalEmulatorDetector
import ru.mamykin.emulatordetector.VerdictSource

internal class PropertiesEmulatorDetector : InternalEmulatorDetector {

    private val checkFunctions = listOf(
        ::isEmulatorFingerprint,
        ::isEmulatorHardware,
        ::isEmulatorBoard,
        ::isEmulatorModel,
        ::isEmulatorDevice,
        ::isEmulatorBrand,
        ::isEmulatorManufacturer,
        ::isEmulatorProduct
    )

    override fun check(onCheckCompleted: (DeviceState) -> Unit) {
        val buildPropsInfo = getInfo()
        val state = if (checkFunctions.any { it(buildPropsInfo) }) {
            DeviceState.Emulator(VerdictSource.Properties(emptyList()))
        } else {
            DeviceState.NotEmulator
        }
        onCheckCompleted(state)
    }

    override fun cancelCheck() {
        TODO("Not yet implemented")
    }

    private fun getInfo() = BuildPropsInfo(
        fingerprint = Build.FINGERPRINT,
        hardware = Build.HARDWARE,
        board = Build.BOARD,
        model = Build.MODEL,
        device = Build.DEVICE,
        brand = Build.BRAND,
        manufacturer = Build.MANUFACTURER,
        product = Build.PRODUCT
    )

    private fun isEmulatorFingerprint(info: BuildPropsInfo): Boolean {
        return listOf(
            "generic/sdk/generic",
            "generic_x86/sdk_x86/generic_x86",
            "Andy",
            "ttVM_Hdragon",
            "generic_x86_64",
            "generic/google_sdk/generic",
            "vbox86p",
            "generic/vbox86p/vbox86p"
        ).any { info.fingerprint.contains(it) } || info.fingerprint.startsWith("unknown")
    }

    private fun isEmulatorHardware(info: BuildPropsInfo): Boolean {
        return listOf(
            "nox",
            "ttVM_x86",
            "ranchu"
        ).any { info.hardware.contains(it) } || listOf(
            "goldfish",
            "vbox86"
        ).any { info.hardware == it }
    }

    private fun isEmulatorBoard(info: BuildPropsInfo): Boolean {
        return info.board.contains("unknown")
    }

    private fun isEmulatorModel(info: BuildPropsInfo): Boolean {
        return listOf(
            "sdk",
            "google_sdk",
            "Emulator",
            "Droid4X",
            "TiantianVM",
            "Andy"
        ).any { info.model.contains(it) } || listOf(
            "Android SDK built for x86_64",
            "Android SDK built for x86"
        ).any { info.model == it }
    }

    private fun isEmulatorDevice(info: BuildPropsInfo): Boolean {
        return listOf(
            "generic",
            "generic_x86",
            "Andy",
            "ttVM_Hdragon",
            "Droid4X",
            "nox",
            "generic_x86_64",
            "vbox86p"
        ).any { info.device.contains(it) }
    }

    private fun isEmulatorBrand(info: BuildPropsInfo): Boolean {
        return listOf(
            "generic",
            "generic_x86",
            "TTVM"
        ).any { info.brand == it } || info.brand.contains("Andy")
    }

    private fun isEmulatorManufacturer(info: BuildPropsInfo): Boolean {
        return listOf(
            "unknown",
            "Genymotion"
        ).any { info.manufacturer == it } || listOf(
            "Andy",
            "MIT",
            "nox",
            "TiantianVM"
        ).any { info.manufacturer.contains(it) }
    }

    private fun isEmulatorProduct(info: BuildPropsInfo): Boolean {
        return listOf(
            "sdk",
            "Andy",
            "ttVM_Hdragon",
            "google_sdk",
            "Droid4X",
            "nox",
            "sdk_x86",
            "sdk_google",
            "vbox86p"
        ).any { info.product.contains(it) }
    }

    data class BuildPropsInfo(
        val fingerprint: String,
        val hardware: String,
        val board: String,
        val model: String,
        val device: String,
        val brand: String,
        val manufacturer: String,
        val product: String
    )
}