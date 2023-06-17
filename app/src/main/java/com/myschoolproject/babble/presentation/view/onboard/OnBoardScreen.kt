package com.myschoolproject.babble.presentation.view.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.MainColorBottom
import com.myschoolproject.babble.ui.theme.MainColorTop
import com.myschoolproject.babble.ui.theme.PretendardFont

@Composable
fun OnBoardScreen(modifier: Modifier = Modifier) {

    val backgroundColors = listOf(MainColorTop, MainColorBottom)

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

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                30.dp,
                alignment = Alignment.CenterVertically
            ),
        ) {
            GoogleLoginButton(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = R.string.google_login_button,
                onClick = {  }
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

@Preview
@Composable
fun OnBoardScreenPreview() {
    OnBoardScreen()
}