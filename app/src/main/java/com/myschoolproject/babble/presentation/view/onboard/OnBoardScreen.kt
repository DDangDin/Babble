package com.myschoolproject.babble.presentation.view.onboard

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.myschoolproject.babble.R
import com.myschoolproject.babble.presentation.viewmodel.LoginViewModel
import com.myschoolproject.babble.ui.theme.MainColorBottom
import com.myschoolproject.babble.ui.theme.MainColorTop
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.CustomSharedPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnBoardScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    val TAG = "BabbleLog_OnBoardScreen"

    var email by remember { mutableStateOf("") }
    var emailCheckLoading by remember { mutableStateOf(false) }

    val loginViewModel: LoginViewModel = hiltViewModel()
    val googleSignInState = loginViewModel.googleSignInState.value

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                loginViewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                Log.d(TAG, "loginStatusCode: ${it.statusCode} ")
            }
        }

    val backgroundColors = listOf(MainColorTop, MainColorBottom)

    LaunchedEffect(Unit) {
        email = CustomSharedPreference(context).getUserPrefs("email")
        delay(1500L)
        if (email.isNotEmpty()) {
            onNavigate()
        } else {
            emailCheckLoading = true
        }
    }

    LaunchedEffect(googleSignInState) {
        scope.launch {
            if (googleSignInState.result != null) {
                CustomSharedPreference(context).setUserPrefs("email", googleSignInState.result.user?.email ?: "")
                Toast.makeText(context, googleSignInState.result.user?.email ?: "", Toast.LENGTH_SHORT).show()
                onNavigate()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColors,
                )
            )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Icon(
                modifier = Modifier.size(65.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo_hand),
                contentDescription = "logo",
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Normal,
                fontSize = 40.sp,
                color = Color.White,
                letterSpacing = 4.sp
            )
        }

        if (!emailCheckLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                strokeWidth = 3.dp,
                color = Color.White
            )
        } else {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                visible = emailCheckLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        30.dp,
                        alignment = Alignment.CenterVertically
                    ),
                ) {
                    GoogleLoginButton(
                        modifier = Modifier.padding(horizontal = 30.dp),
                        text = R.string.google_login_button,
                        onClick = {
                            emailCheckLoading = false
                            googleLogin(context, launcher)
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.login_trouble_text),
                        fontSize = 14.sp,
                        fontFamily = PretendardFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}


private fun googleLogin(context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val gso = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(Constants.GOOGLE_CLIENT_ID)
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    launcher.launch(googleSignInClient.signInIntent)

    // test toast
    Toast.makeText(context, "Google Login...", Toast.LENGTH_SHORT).show()
}


@Preview
@Composable
fun OnBoardScreenPreview() {
//    OnBoardScreen()
}