package be.vergauwen.simon.androidtestingexample.ui

import be.vergauwen.simon.androidtestingexample.core.di.ActivityScope
import be.vergauwen.simon.androidtestingexample.core.di.ApplicationComponent
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ExampleComponent : ExampleContract.Component<ExampleContract.View,ExamplePresenter>