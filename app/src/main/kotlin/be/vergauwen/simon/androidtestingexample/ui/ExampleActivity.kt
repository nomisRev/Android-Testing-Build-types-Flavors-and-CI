package be.vergauwen.simon.androidtestingexample.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import be.vergauwen.simon.androidtestingexample.BuildConfig
import be.vergauwen.simon.androidtestingexample.ExampleApp
import be.vergauwen.simon.androidtestingexample.R
import be.vergauwen.simon.himurakotlin.MVPDaggerActivity

class ExampleActivity : MVPDaggerActivity<ExampleContract.View, ExamplePresenter, ExampleComponent>(), ExampleContract.View {

  val githubRepo by lazy { findViewById(R.id.github_repos) as TextView}

  override fun createComponent(): ExampleComponent =
      DaggerExampleComponent.builder().applicationComponent((application as ExampleApp).component).build()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.example_activity)
    githubRepo.text = "Github repo = \n"
  }

  override fun printRepo(repoName: String) {
    Log.e("ExampleActivity", repoName)
    githubRepo.text = githubRepo.text.toString() + repoName + "\n"
  }

  override fun showError() {
    Log.e("ExampleActivity", "showError(")
    githubRepo.text = "error"
  }

  fun isMockBuild() : Boolean {
    return BuildConfig.MOCK
  }
}