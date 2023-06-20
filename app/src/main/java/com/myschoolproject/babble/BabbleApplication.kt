package com.myschoolproject.babble

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BabbleApplication: Application() {
}

/**
 email 값: SharedPreference로 저장 되어 있음 (앱 최초 실행 시 저장 되게 함)
 **/