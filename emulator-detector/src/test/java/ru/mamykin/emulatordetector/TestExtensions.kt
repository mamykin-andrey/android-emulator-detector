package ru.mamykin.emulatordetector

import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime

internal suspend fun TestScope.assertVirtualTime(timeMillis: Int, block: suspend () -> Unit) {
    val startTime = currentTime
    block()
    val finishTime = currentTime
    val totalTime = finishTime - startTime

    assertTrue(
        "Time limit failed, required time: $timeMillis, total time: $totalTime",
        totalTime < timeMillis
    )
}