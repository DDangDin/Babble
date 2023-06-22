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

    val TEST_IMAGES_FROM_FIREBASE_STORAGE = listOf(
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog9.jpg?alt=media&token=0b15ce60-668d-4459-b3ff-128470b0a653`",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog8.jpg?alt=media&token=19ee4817-fb6c-4897-83cb-79eee5b999c3",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog7.jpg?alt=media&token=a2270f4d-2fb9-4403-aedf-7801349a1a68",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog6.jpg?alt=media&token=501c6d5c-a437-4a3a-bc0c-597844cd7ce9",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog5.jpg?alt=media&token=76170030-e885-48a9-ac57-8ef97943bf83",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog4.jpg?alt=media&token=7776bdc5-fb93-456a-8aa3-cdf8e7146eb0",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog3.png?alt=media&token=bff95bdb-0b55-4e12-b03d-c059e9ec97c5",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog2.jpg?alt=media&token=78d92bad-d7cd-4dc0-b722-281ab1e758ed",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog10.jpg?alt=media&token=d4b12977-8644-4bc0-a630-c053f355c6da",
        "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog1.jpg?alt=media&token=abc3e0bb-2254-45cb-8598-22f105e54b1d",
    )

    val TEST_IMAGES_LOCAL = listOf(
        R.drawable.dog1,
        R.drawable.dog2,
        R.drawable.dog3,
    )

    // Firebase - Firestore
    const val DISPLAY_FRIENDS = "display_friends"
    const val DISPLAY_FRIENDS_ID = "id_email"
}