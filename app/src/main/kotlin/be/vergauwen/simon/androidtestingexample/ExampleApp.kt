package be.vergauwen.simon.androidtestingexample

import android.app.Application
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationModule
import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.ServiceModule

open class ExampleApp : Application() {
  val component by lazy { createComponent() }

  @Override
  open fun createComponent(): ApplicationComponent =
      DaggerApplicationComponent.builder()
          .serviceModule(ServiceModule())
          .applicationModule(ApplicationModule(this))
          .build()
}