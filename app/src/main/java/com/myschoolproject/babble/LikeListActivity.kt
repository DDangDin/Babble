package com.myschoolproject.babble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.myschoolproject.babble.presentation.view.home.like_list.LikeListScreen
import com.myschoolproject.babble.presentation.viewmodel.LikeListViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val likeListViewModel: LikeListViewModel = hiltViewModel()

                    LikeListScreen(
                        onBackStack = { finish() },
                        likeList = likeListViewModel.likeList.value
                    )
                }
            }
        }
    }
}
