package be.vergauwen.simon.androidtestingexample.ui

import be.vergauwen.simon.androidtestingexample.BuildConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertTrue

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ExampleActivityTestDemoMock {
  @Test
  fun testMockBuild() {
    val activity = Robolectric.buildActivity(
        ExampleActivity::class.java).create().start().resume().visible().get()
    assertTrue(activity.isMockBuild())
  }
}