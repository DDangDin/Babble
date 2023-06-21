package com.myschoolproject.babble.presentation.view.profile

import android.graphics.ColorSpace
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.PretendardFont

@Composable
fun ProfileCustomButton(
    modifier: Modifier = Modifier,
    @DrawableRes imageVector: Int,
    text: String,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(9.dp, alignment = Alignment.CenterVertically)
    ) {
//        Shadow(
//            color = Color(0x4c000000),
//            offset = Offset(2f, 2f),
//            blurRadius = 7f
//        )
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(65.dp)
                .background(Color.Transparent)
                .border(
                    // 뒷 배경 그림자 느낌 주는거 꿀팁(shadow도 있긴함)
                    width = 7.dp,
                    shape = CircleShape,
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFE7E7E7), Color.White.copy(0f)),
                        radius = 100f,
                    )
                ),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier
                    .width(25.dp)
                    .height(17.dp),
                imageVector = ImageVector.vectorResource(id = imageVector),
                contentDescription = "friendsList",
                tint = Color(0xFF7D848F)
            )
        }

        Text(
            text = text,
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF68707E)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileCustomButtonPreview() {
    ProfileCustomButton(
        imageVector = R.drawable.ic_setting_friends_list,
        text = "친구목록",
        onClick = {}
    )
}