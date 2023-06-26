package com.myschoolproject.babble.presentation.view.chat.chatting_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.IconBackColor
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextBig

@Composable
fun ChattingScreenTopBar(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit,
    nickname: String,
    onSiren: () -> Unit,
    onExitRoom: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            IconButton(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                onClick = onBackStack
            ) {
                Icon(
                    modifier = Modifier
                        .width(13.dp)
                        .height(21.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "back",
                    tint = IconBackColor
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                text = nickname,
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = TextBig
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            IconButton(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                onClick = onSiren
            ) {
                Icon(
                    modifier = Modifier
                        .width(26.dp)
                        .height(27.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_siren2),
                    contentDescription = "back",
                    tint = TextBig
                )
            }
            IconButton(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                onClick = onExitRoom
            ) {
                Icon(
                    modifier = Modifier
                        .width(26.dp)
                        .height(23.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_exit),
                    contentDescription = "back",
                    tint = TextBig
                )
            }
        }
    }
}