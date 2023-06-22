package com.myschoolproject.babble

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BabbleApplication : Application() {
}

/**
< user(thumbnail), email 값: SharedPreference로 저장 되어 있음 (앱 최초 실행 시 저장 되게 함) >
1. user(thumbnail) key 값 -> user_photo
2. email key 값 -> email

< LikeList 데이터: RoomDB로 저장 >
 **/