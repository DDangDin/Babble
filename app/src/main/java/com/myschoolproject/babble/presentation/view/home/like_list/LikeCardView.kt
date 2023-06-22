package com.myschoolproject.babble.presentation.view.home.like_list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myschoolproject.babble.R
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault
import com.myschoolproject.babble.utils.CustomThemeEffect

@Composable
fun LikeCardView(
    modifier: Modifier = Modifier,
    likeListEntity: LikeListEntity,
    onDelete: () -> Unit,
    onSend: () -> Unit
) {

    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape),
//                model = ImageRequest.Builder(context)
//                    .data(likeListEntity.thumbnail)
//                    .crossfade(true)
//                    .build(),
                model = ImageRequest.Builder(context)
                    .data(R.drawable.dog1)
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = likeListEntity.nickname,
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp,
                color = TextDefault
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                35.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            CompositionLocalProvider(LocalRippleTheme provides CustomThemeEffect.NoRippleTheme) {
                IconButton(onClick = onDelete) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                        contentDescription = "delete",
                        tint = Color(0xFFF86A6A)
                    )
                }
                IconButton(onClick = onSend) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_send),
                        contentDescription = "send",
                        tint = Color(0xFF87E786)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikeCardViewPreview() {
    LikeCardView(
        likeListEntity = LikeListEntity(
            nickname = "워니",
            city = "",
            age = 0,
            id_email = "",
            thumbnail = "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog1.jpg?alt=media&token=abc3e0bb-2254-45cb-8598-22f105e54b1d"
        ),
        onDelete = {},
        onSend = {}
    )
}