package com.myschoolproject.babble.presentation.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myschoolproject.babble.R
import com.myschoolproject.babble.presentation.view.common.BottomBar
import com.myschoolproject.babble.presentation.view.common.CustomTextField
import com.myschoolproject.babble.presentation.view.common.TopBar
import com.myschoolproject.babble.presentation.viewmodel.LoginViewModel
import com.myschoolproject.babble.ui.theme.IconBackColor
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextBig
import com.myschoolproject.babble.utils.CustomSharedPreference

@Composable
fun FirstRegisterScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onNavigate: () -> Unit,
    onBackStack: () -> Unit
) {

    val userState = loginViewModel.userState.value
    val addDisplayFriendState = loginViewModel.addDisplayFriendState.value

    val context = LocalContext.current

    var isEmptyValue_1 by remember { mutableStateOf(false) }
    var isEmptyValue_2 by remember { mutableStateOf(false) }
    var isEmptyValue_3 by remember { mutableStateOf(false) }
    var isEmptyValue_4 by remember { mutableStateOf(false) }

    var errorControl by remember { mutableStateOf(false) }

    LaunchedEffect(userState) {
        // 사실은 에러났을 때 처리해줘야 함
        if (
            !userState.loading &&
            userState.userData != null &&
            addDisplayFriendState
        ) {
            // 1. HomeActivity로 User 데이터 넘김
            // 2. 아예 HomeActivity에서 서버에 호출한 뒤 데이터 불러옴
            onNavigate()
        } else if (userState.error.isNotEmpty()) {
            errorControl = true
        }

    }


    Box(modifier.fillMaxSize()) {
        if (errorControl) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "다시 시도",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = MainColorMiddle
            )
        } else {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                TopBar(
                    onBackStack = onBackStack
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 20.dp),
                    text = stringResource(id = R.string.register_1_title),
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    color = TextBig
                )
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 5.dp, start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        23.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    CustomTextField(
                        dataName = R.string.register_1_subTitle_1,
                        dataValue = loginViewModel.nickname.value,
                        onTextChanged = loginViewModel::nicknameChanged,
                        keyboardType = KeyboardType.Text,
                        isEmptyValue = isEmptyValue_1
                    )
                    CustomTextField(
                        dataName = R.string.register_1_subTitle_2,
                        dataValue = loginViewModel.age.value,
                        onTextChanged = loginViewModel::ageChanged,
                        keyboardType = KeyboardType.Number,
                        isEmptyValue = isEmptyValue_2
                    )
                    CustomTextField(
                        dataName = R.string.register_1_subTitle_3,
                        dataValue = loginViewModel.city.value,
                        onTextChanged = loginViewModel::cityChanged,
                        keyboardType = KeyboardType.Text,
                        isEmptyValue = isEmptyValue_3
                    )
                    CustomTextField(
                        dataName = R.string.register_1_subTitle_4,
                        dataValue = loginViewModel.gender.value,
                        onTextChanged = loginViewModel::genderChanged,
                        keyboardType = KeyboardType.Text,
                        isEmptyValue = isEmptyValue_4
                    )
                }

            }
            if (userState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp),
                    strokeWidth = 3.dp,
                    color = MainColorMiddle
                )
            } else {
                BottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .align(Alignment.BottomCenter),
                    onClick = {
                        isEmptyValue_1 = loginViewModel.nickname.value.isEmpty()
                        isEmptyValue_2 = loginViewModel.age.value.isEmpty()
                        isEmptyValue_3 = loginViewModel.city.value.isEmpty()
                        isEmptyValue_4 = loginViewModel.gender.value.isEmpty()

                        if (
                            loginViewModel.nickname.value.isNotEmpty() &&
                            loginViewModel.age.value.isNotEmpty() &&
                            loginViewModel.city.value.isNotEmpty() &&
                            loginViewModel.gender.value.isNotEmpty()
                        ) {
                            CustomSharedPreference(context).setUserPrefs(
                                "email",
                                loginViewModel.googleSignInState.value.result?.user?.email ?: ""
                            )
                            loginViewModel.register()
                        }
                    }
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun FirstRegisterScreenPreview() {
//    val loginViewModel: LoginViewModel = hiltViewModel()
//    FirstRegisterScreen(
//        loginViewModel = loginViewModel,
//        onNavigate = {},
//        onBackStack = {}
//    )
}

@Preview
@Composable
fun ErrorTextPreview() {
    Text(
        text = "다시 시도",
        fontFamily = PretendardFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        color = MainColorMiddle
    )
}