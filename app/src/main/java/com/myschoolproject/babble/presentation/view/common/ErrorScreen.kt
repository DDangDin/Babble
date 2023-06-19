package com.myschoolproject.babble.presentation.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextHint2
import com.myschoolproject.babble.ui.theme.TextRegular

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMsg: String,
    onBackStack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(
            Modifier
                .align(Alignment.TopCenter)
                .padding(start = 10.dp),
            onBackStack = onBackStack
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp, alignment = Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = R.string.error_text),
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = TextRegular
            )
            Text(
                text = "에러: $errorMsg",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = TextHint2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        modifier = Modifier.fillMaxSize(),
        errorMsg = "(에러코드)",
        onBackStack = {}
    )
}