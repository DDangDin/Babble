package com.myschoolproject.babble.presentation.view.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.ui.theme.PretendardFont

@Composable
fun RequireProfileThumbnail(
    modifier: Modifier = Modifier,
    updateMyProfilePhoto: (Uri) -> Unit
) {


    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                updateMyProfilePhoto(uri)
            }
        }
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = "프로필 사진을 등록 해 주세요",
            textAlign = TextAlign.Center,
            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            color = MainColorMiddle
        )
        IconButton(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(Color.LightGray)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                tint = Color.White
            )
        }
//        OpenPhotoPicker(
//            modifier = Modifier,
//            selectedImageUri = selectedImageUri
//        )
    }
}