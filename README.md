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

##### Mock server example

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

**Now why would you use this? I could just remove the lines when switching build, or I could just comment the logging real quick. You will forget and unexpected behavior will arise. But even more important, your code is does that for you now. And you can define tests for the different build types!**

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
* Since by default a release and debug build type is configured, we now have the following `build variants`
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


# Jenkins Android setup

* Ubuntu server (or desktop) running in VirtualBox
	* Installed without any additional packages since setting up dns, apache etc is out of scope and depends on your enviroment

## Installing java

* Installing Java with apt-get is easy. First, update the package index: `sudo apt-get update`
* Check if Java is not already installed: `java -version`, "The program java can be found in the following packages" means it's not installed
* To install JRE execute the following command: `sudo apt-get install default-jre`
* To install JDK use `sudo apt-get install default-jdk`

## Installing android sdk

* `wget http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz`
* `tar -xvf android-sdk_r24.4.1-linux.tgz`
* `mv android-sdk-linux /usr/lib/android`
* `rm android-sdk_r24.4.1-linux.tgz`
* `cd /usr/lib/android/tools`
* `sudo apt-get install lib32stdc++6`
* `sudo apt-get install lib32z1`

Get some coffee for the next part cause this takes a while!!!!

* `./android update sdk --no-ui --all`

**There is a gradle plugin from Jake Wharton (https://github.com/JakeWharton/sdk-manager-plugin) that takes care of missing android dependencies. I don't prefer this setup, cause gradle is not responsible for installing packages on the master server. I will prefer to do this with a cronjob that checks for new packages.**
	* Jenkins now has a option that installs missing android packages if required (not tested yet)

## Installing gradle **Not required**
* This step is not required because you can set Jenkins to use the gradle/wrapper and specify which gradle version you want to use : `distributionUrl=https\://services.gradle.org/distributions/gradle-2.10-all.zip` (Android studio automatically does this for you)

* Because gradle is available trough PPA (Personal Package Archive), we'll first have to add it to our system: ` sudo add-apt-repository ppa:cwchien/gradle`
* After we've added it, we'll have to update our repository `sudo apt-get update`
* `sudo apt-get install grade` --> If you don't want the latest gradle version you can append a version `gradle-1.5.0`

* **IMPORTANT** Check which version of gradle is running (!!) to check which Gradle version is required for which android gradle plugin. `gradle -v`, http://tools.android.com/tech-docs/new-build-system/version-compatibility

**Gradle recommends NOT using gradle daemon in the CI environment!**

* In case you got any of the following errors, you most likely have a mismatch gradle version issue.
	* `Could not find method jcenter() for arguments [] on repository container`
	* `> Could not create plugin of type 'AppPlugin'.`

## Installing git

* `sudo apt-get install git`

## Installing jenkins

* `wget --no-check-certificate -q -O - https://jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -`
* `sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'`
* `sudo apt-get update`
* `sudo apt-get install jenkins`

## After installing all components

* `sudo apt-get update`
* `sudo apt-get upgrade`


### Enviroment variables

* `sudo nano /etc/profile`
* add at the end of file:
```
export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
export ANDROID_HOME=/usr/lib/android
```
