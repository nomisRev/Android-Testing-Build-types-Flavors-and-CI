package be.vergauwen.simon.androidtestingexample.ui

import be.vergauwen.simon.androidtestingexample.BuildConfig
import be.vergauwen.simon.androidtestingexample.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ExampleActivityTestDemo {

  @Test
  fun testString() {
    val activity = Robolectric.buildActivity(
        ExampleActivity::class.java).create().start().resume().visible().get()

    val string = activity.getString(R.string.hello_world)
    assertEquals(string, "Demo version only.")
  }
}