package com.myschoolproject.babble.presentation.view.common

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun OpenPhotoPicker(
    modifier: Modifier = Modifier,
    selectedImageUri: Uri?
) {

    Box(modifier = modifier) {
        selectedImageUri?.let { uri ->
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp)
                    .clip(CircleShape),
                model = uri,
                contentDescription = "my_thumbnail",
                contentScale = ContentScale.Crop
            )
        }
    }
}