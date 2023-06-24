package com.myschoolproject.babble.presentation.view.home.swipe_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.WarningColor

@Composable
fun FriendInfoBar(
    modifier: Modifier = Modifier,
    nickname: String,
    age: String,
    city: String
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${nickname},",
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 35.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = age,
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
            Text(
                text = city,
                fontFamily = PretendardFont,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_siren1),
                contentDescription = "siren",
                tint = WarningColor
            )
            Text(
                text = "!",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun FriendInfoBarPreview() {
    FriendInfoBar(nickname = "홍길동", age = "23", city = "천안시 동남구")
}