package ru.mamykin.emulatordetector

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class ComplexEmulatorDetectorTest {

    @Test
    fun `check returns not emulator when detectors are empty`() = runTest {
        val detector = ComplexEmulatorDetector(emptyList())

        val state = detector.check()

        assertTrue(state is DeviceState.NotEmulator)
    }

    @Test
    fun `check returns emulator when detector returns emulator`() = runTest {
        val detector = ComplexEmulatorDetector(listOf(mockk<EmulatorDetector>().apply {
            coEvery { check() } returns DeviceState.Emulator(VerdictSource.Properties(emptyList()))
        }))

        val state = detector.check()

        assertTrue(state is DeviceState.Emulator)
    }

    @Test(timeout = 600)
    fun `check launches checks in parallel`() = runTest {
        val firstMockEmulator = mockk<EmulatorDetector>().apply {
            coEvery { check() } coAnswers {
                delay(500)
                DeviceState.NotEmulator
            }
        }
        val secondMockEmulator = mockk<EmulatorDetector>().apply {
            coEvery { check() } coAnswers {
                delay(500)
                DeviceState.NotEmulator
            }
        }
        val detector = ComplexEmulatorDetector(listOf(firstMockEmulator, secondMockEmulator))

        detector.check()

        coVerify {
            firstMockEmulator.check()
            secondMockEmulator.check()
        }
    }
}