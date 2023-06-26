package com.myschoolproject.babble.presentation.view.chat.chatting_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault
import com.myschoolproject.babble.ui.theme.TextFieldBackground
import com.myschoolproject.babble.ui.theme.TextHint2

@Composable
fun ChattingScreenBottomBar(
    modifier: Modifier = Modifier,
    inputMessage: String,
    inputMessageChanged: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(9f)
                .border(
                    1.dp,
                    Color.Transparent
                ),
            value = inputMessage,
            onValueChange = { inputMessageChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = TextFieldBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                textColor = TextDefault
            ),
            placeholder = {
                Text(
                    text = "메세지를 입력하세요...",
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.sp,
                    color = TextHint2
                )
            }
        )
        IconButton(
            modifier = Modifier
                .weight(1f)
                .size(24.dp),
            onClick = onSend
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_send),
                contentDescription = "send",
                tint = Color(0xFFA8A8A8)
            )
        }
    }
}