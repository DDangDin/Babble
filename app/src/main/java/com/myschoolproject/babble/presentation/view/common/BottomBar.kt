package com.myschoolproject.babble.presentation.view.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.R

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        text = R.string.btn_next_text,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar() {}
}