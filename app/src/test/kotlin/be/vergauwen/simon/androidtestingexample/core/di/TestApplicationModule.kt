package be.vergauwen.simon.androidtestingexample.core.di

import android.app.Application
import android.content.Context
import be.vergauwen.simon.androidtestingexample.core.rx.MockRxUtil
import be.vergauwen.simon.androidtestingexample.core.rx.Transformers
import dagger.Module
import dagger.Provides

@Module
class TestApplicationModule(private val application: Application) : ApplicationModule(application) {

  @ApplicationScope
  @Provides
  override fun provideApplicationContext(): Context {
    return application
  }

  @ApplicationScope
  @Provides
  override fun provideTransformers(): Transformers {
    return MockRxUtil()
  }
}