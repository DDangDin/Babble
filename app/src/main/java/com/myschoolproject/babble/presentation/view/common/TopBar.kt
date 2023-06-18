package com.myschoolproject.babble.presentation.view.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.IconBackColor

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier
                .padding(vertical = 20.dp),
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
    }
}