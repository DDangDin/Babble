package com.myschoolproject.babble

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.myschoolproject.babble.navigation.BabbleNavigationGraph
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivityLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        CustomSharedPreference(this).setUserPrefs("email", "mock@test.com")

        setContent {
            
            /*TODO 프로젝트 얼추 마무리 후 네트워크 호출 추적해보기 (ex. 쓸데 없이 중복 호출 되는 경우)*/

            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    BabbleNavigationGraph(
                        navController = navController,
                        onNavigate = {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}