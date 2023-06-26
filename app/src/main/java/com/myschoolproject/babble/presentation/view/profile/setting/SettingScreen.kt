package com.myschoolproject.babble.presentation.view.profile.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.presentation.view.common.TopBar
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.SpacerCustomColor
import com.myschoolproject.babble.ui.theme.TextRegular
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.CustomThemeEffect
import com.myschoolproject.babble.utils.CustomThemeEffect.clickableWithoutRipple

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(
            modifier = Modifier,
            title = "",
            onBackStack = onBackStack
        )
        Image(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
                .width(178.dp)
                .height(46.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_main_top_logo),
            contentDescription = "logo"
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        Box(modifier = Modifier
            .clickable { }
        ) {
            SettingItem(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 13.dp),
                textLeft = "버전",
                textRight = Constants.APP_VERSION,
                onClick = { }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        Box(modifier = Modifier
            .clickable { }
        ) {
            SettingItem(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 13.dp),
                textLeft = "서비스 이용약관",
                textRight = ">",
                onClick = { }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        Box(modifier = Modifier
            .clickable { onLogout() }
        ) {
            SettingItem(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 13.dp),
                textLeft = "로그아웃",
                textHighlighting = true,
                onClick = { }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpacerCustomColor)
        )
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    textLeft: String,
    textHighlighting: Boolean = false,
    textRight: String = "",
    onClick: () -> Unit
) {

    val textLeftColor = if (textHighlighting) Color(0xFFED3D3D) else TextRegular

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = textLeft,
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp,
            color = textLeftColor
        )
        CompositionLocalProvider(LocalRippleTheme provides CustomThemeEffect.NoRippleTheme) {
            Text(
                modifier = Modifier.clickable {
                    if (textRight == ">") {
                        onClick()
                    }
                },
                text = textRight,
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp,
                color = Color(0xFFA89FA2)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        onBackStack = {},
        onLogout = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SettingItemPreview() {
    SettingItem(
        textLeft = "로그아웃",
        textRight = "",
        onClick = {},
        textHighlighting = true
    )
}