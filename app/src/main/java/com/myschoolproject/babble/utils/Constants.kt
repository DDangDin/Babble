package com.myschoolproject.babble.utils

import android.app.Activity
import android.graphics.Point
import android.view.Display
import com.myschoolproject.babble.BuildConfig

object Constants {

    const val BABBLE_MOCK_API_SERVER = "https://2263e0cb-14c8-47db-ba5a-d44d6f6509a5.mock.pstmn.io"

    const val GOOGLE_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID

    fun getScreenSize(activity: Activity): Point {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }
}