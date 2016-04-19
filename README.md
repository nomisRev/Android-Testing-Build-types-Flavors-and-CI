# Under construction!!!

# Testing : Build types, Product flavors and Continuous integration

<!-- ## Default Config

The defaultConfig element configures core settings and entries in the manifest file (AndroidManifest.xml) dynamically from the build system. The values in defaultConfig override those in the manifest file.
The configuration specified in the defaultConfig element applies to all build variants, unless the configuration for a build variant overrides some of these values.

```
defaultConfig {
    applicationId "be.vergauwen.simon.androidtestingexample"
    minSdkVersion 9
    targetSdkVersion 23
    versionCode 1
    versionName "1.0"
}
``` -->

## Build types

* By default the build system defines two build types, `debug` and `release`. But in some cases that might not be enough for you, let's "imagine" the situation when you or your team is developing but the backend is behind schedule. You could just hang around or do something else but now you're so excited to continue working on this product that you create a small node.js project or a mock service so that you can continue your development.
* This could be the perfect situation to decide to add a build type. So let's add a `mock` build type, so let's say we want to switch between backend depending on the build type, there are serveral ways to do this.

##### Node.js example

* We can define `BuildConfig` fields, in this case we could define a field `URI` which we use to make a connection to our backend. This solution is the most straight forward, and is the cleanest code wise because the URI switches based on the build type.

```
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        buildConfigField "String", "URI",  "\"http://release.com\""
    }
    debug {
        debuggable true
        applicationIdSuffix ".debug"
        buildConfigField "String", "URI",  "\"http://debug.com\""
    }
    mock {
        applicationIdSuffix ".mock"
        buildConfigField "String", "URI",  "\"http://localhost:8080\""
    }
}
```

* In order to get the `URI` in code: `val URI = BuildConfig.URI`

##### Other example

* Default the build system adds a boolean field for `debug`, which is true only if the build type is debug. You could do this for other types as well. And use it to enable or disable http logging retrofit for example. You don't want this to be enabled in a release version!

* You can do this simply by checking `if(BuildConfig.DEBUG)`

**Now why would you use this? I could just remove the lines when switching build, or I could just comment the logging real quick. You will forget and unexpected behavior will arise. But even more important, your code is automated now. And you can define tests for the different build types**

* So now we could add all the test folders like `app/src/testDemoMock`, within this folder you can write tests that will run from command-line/CI when the build type is MOCK.
* To test this we us `./gradlew test`, if you encounter problems while your test run correctly in Android studio try using `./gradlew clean test`


## Product flavors

* A product flavor is a "flavor" of a product.
* The most used example  is a free or paid app, an admin or user app. Let's take another context: If Coffee is the product. You can have latte and mocha flavors. If we translate this back to development this means: Coffee is our "product" then latte and mocha are two coffee products with a different implementation. I'll illustrate a bit more in depth and in code what that means. 

* Creating product flavors is pretty straight forward. We can do this by adding the following in the app/build.gradle
```
android {
	 productFlavors {
        demo {
            applicationId "be.vergauwen.simon.androidtestingexample.demo"
            versionName "1.0-demo"
        }
        full {
            applicationId "be.vergauwen.simon.androidtestingexample.full"
            versionName "1.0-full"
        }
    }
}

```
* Since we defined a release and debug build type, we now have the following `build variants`
	* debugDemo
	* releaseDemo
	* debugFull
	* releaseFull

* We can now define our flavor directories
	* app/src/demo
	* app/src/testDemo (app/src/androidTestDemo)
	* app/src/full
	* app/src/testFull (app/src/androidTestFull)

* Using product flavors, let's say we want a different string to show in our demo and full variant
* We add the following to `full/res/values/strings.xml` and `demo/res/values/strings.xml` > `<string name="hello_world">Full version only.</string>`
