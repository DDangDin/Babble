package com.myschoolproject.babble.utils

import android.app.Activity
import android.graphics.Point
import android.view.Display
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.BuildConfig
import com.myschoolproject.babble.R
import com.myschoolproject.babble.domain.repository.DisplayFriends

object Constants {

    const val BABBLE_MOCK_API_SERVER = "https://2263e0cb-14c8-47db-ba5a-d44d6f6509a5.mock.pstmn.io"
    const val BABBLE_REAL_API_SERVER = ""

    const val GOOGLE_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID

    fun getScreenSize(activity: Activity): Point {
        val display: Display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    const val BABBLE_BOTTOM_BAR_PADDING = 66

    val TEST_IMAGES_REMOTE = listOf(
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog1.jpg?alt=media&token=abc3e0bb-2254-45cb-8598-22f105e54b1d",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog2.jpg?alt=media&token=78d92bad-d7cd-4dc0-b722-281ab1e758ed",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog3.png?alt=media&token=bff95bdb-0b55-4e12-b03d-c059e9ec97c5",
    )

    val TEST_IMAGES_LOCAL = listOf(
        R.drawable.dog1,
        R.drawable.dog2,
        R.drawable.dog3,
    )

    // Firebase - Firestore
    const val DISPLAY_FRIENDS = "display_friends"
    const val DISPLAY_FRIENDS_ID = "id"
}