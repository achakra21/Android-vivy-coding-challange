package com.abhijit.vivy_codingtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()
//very important to declare this in AndroidManifest.xml - android:name=".App"