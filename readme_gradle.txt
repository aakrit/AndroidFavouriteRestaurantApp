##### HOW TO SET-UP YOUR PROJECT TO USE GRADLE FOR BUILDING AND INSTRUMENTATION TESTING#######

(1) install Gradle 1.6 or higher (get the latest one)  (http://www.gradle.org/downloads)

(2)
add these lines to your .gitignore file in the root of your project; and make sure to add an empty carriage-return at the
end of the file:

#for gradle
build/
.gradle/
gradle-app.setting


(you may copy this file from ~/content/slides/gradle/.gitignore)

(3)  make sure that you have a file called local.properties in the root of your project.
This file must contain the path to your local android sdk, and it should point to your local
installation of your android sdk.

(you may copy this file from ~/content/slides/gradle/local.properties -- and then modify it!)

on PC this might look like this:
sdk.dir=C:/port/adt/sdk

on Mac it might look like this:
sdk.dir=/users/admin/adt/sdk


(4) create a new file called build.gradle in the root of your project. Just copy this file from
~/content/slides/gradle/build.gradle and place it in the root of your project.

This build.gradle file will work with the standard directory structures. For example, by convention
you put all your source files in src, and all your jars in libs, etc.


(5) save and commit everything. close your project in Android Studio and then re-Import it.

When prompted, select Import project from external model || select gradle

In the next dialog:
use local gradle distribution
set the gradle home to the directory where you originally installed gradle. On my
PC, the parent is C:/java/gradle-1.6  but it will be different on your pc/mac.

then click finish.

You now have a working gradle project!

(6) Let's create some Instrumenation tests.

If it's not already there, create a tests directory
in the root of your project.

In tests create a new directory called src. It should turn green because build.gradle is telling the
project that this is the Test Source root. If it's not green, then right-click it and select
Mark Directory as Test Source root, and then src (within tests) will turn green.


(7) Open the ~/content/slides/gradle/tests-dir/ directory. In there, you will find two files:

AndroidManifest.xml and project.properties. Copy both of these files to the tests directory you just created.

Warning: do NOT overwrite the AndroidManifest.xml in the root of your project. this AndroidManifest.xml belongs
in the tests directory.


(8) Make sure that the project.properties is target=android-17  or modify it to the target sdk version.
 Modify two lines in the AndroidManifest.xml file so that:

  package="edu.uchicago.yourproject.test"

  android:targetPackage="edu.uchicago.yourproject"


(9) Add your Tests. Congratulations, you now have instrumentation tests.