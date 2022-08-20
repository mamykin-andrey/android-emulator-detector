package ru.mamykin.emulatordetector

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ComplexEmulatorDetectorTest {

    @Test
    fun `check returns not emulator when detectors are empty`() = runTest {
        val detector = ComplexEmulatorDetector(emptyList())

        val state = detector.getState()

        assertTrue(state is DeviceState.NotEmulator)
    }

    @Test
    fun `check returns emulator when detector returns emulator`() = runTest {
        val detector = ComplexEmulatorDetector(listOf(mockk<EmulatorDetector>().apply {
            coEvery { getState() } returns DeviceState.Emulator(VerdictSource.Properties(emptyList()))
        }))

        val state = detector.getState()

        assertTrue(state is DeviceState.Emulator)
    }

    @Test
    fun `check launches checks in parallel`() = runTest {
        assertVirtualTime(600) {
            val delayDetector = mockk<EmulatorDetector>().apply {
                coEvery { getState() } coAnswers {
                    delay(500)
                    DeviceState.NotEmulator
                }
            }
            val detector = ComplexEmulatorDetector(listOf(delayDetector, delayDetector))

            detector.getState()

            coVerify(exactly = 2) {
                delayDetector.getState()
            }
        }
    }
}