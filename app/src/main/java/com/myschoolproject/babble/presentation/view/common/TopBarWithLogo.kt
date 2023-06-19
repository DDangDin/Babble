package com.myschoolproject.babble.presentation.view.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.IconBackColor
import com.myschoolproject.babble.ui.theme.MainColorMiddle

@Composable
fun TopBarWithLogo(
    modifier: Modifier = Modifier,
    onlyLogo: Boolean,
    onNavigateLikeList: () -> Unit,
    @DrawableRes icon: Int = R.drawable.ic_alpha_empty,
) {

    val likeListIcon = if (onlyLogo) {
        R.drawable.ic_alpha_empty
    } else {
        R.drawable.ic_likelist
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .width(120.dp)
                .height(30.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_main_top_logo),
            contentDescription = "back",
            tint = MainColorMiddle
        )
        IconButton(
            modifier = Modifier
                .padding(vertical = 20.dp),
            onClick = onNavigateLikeList,
            enabled = !onlyLogo
        ) {
            Icon(
                modifier = Modifier
                    .width(30.dp)
                    .height(26.dp),
                imageVector = ImageVector.vectorResource(id = likeListIcon),
                contentDescription = "back",
                tint = MainColorMiddle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarWithLogoPreview() {
    TopBarWithLogo(
        modifier = Modifier.fillMaxWidth(),
        onNavigateLikeList = { /*TODO*/ },
        onlyLogo = false
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarWithLogoPreview2() {
    TopBarWithLogo(
        modifier = Modifier.fillMaxWidth(),
        onNavigateLikeList = { /*TODO*/ },
        onlyLogo = true
    )
}