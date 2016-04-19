package be.vergauwen.simon.androidtestingexample

import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.DaggerApplicationComponent
import be.vergauwen.simon.androidtestingexample.core.di.TestApplicationModule
import be.vergauwen.simon.androidtestingexample.core.di.TestServiceModule

class TestExampleApp : ExampleApp() {
  override fun createComponent(): ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(
      TestApplicationModule(this)).serviceModule(TestServiceModule()).build()
}