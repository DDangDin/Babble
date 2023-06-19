package com.myschoolproject.babble.presentation.view.common

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.R
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault
import com.myschoolproject.babble.ui.theme.TextFieldBackground
import com.myschoolproject.babble.ui.theme.TextHint
import com.myschoolproject.babble.ui.theme.TextHint2
import com.myschoolproject.babble.ui.theme.TextRegular

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    @StringRes dataName: Int,
    dataValue: String,
    onTextChanged: (String) -> Unit,
    keyboardType: KeyboardType,
    isEmptyValue: Boolean
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = dataName),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontFamily = PretendardFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = TextHint2,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    1.dp,
                    if (isEmptyValue) Color.Red else Color.Transparent,
                    RoundedCornerShape(10.dp)
                ),
            value = dataValue,
            onValueChange = { onTextChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = TextFieldBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                textColor = TextDefault
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        )
    }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        dataName = R.string.register_1_subTitle_1,
        dataValue = "홍길동",
        onTextChanged = {},
        keyboardType = KeyboardType.Text,
        isEmptyValue = false
    )
    
}