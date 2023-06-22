package com.myschoolproject.babble.presentation.view.home.like_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.presentation.view.common.TopBar
import com.myschoolproject.babble.ui.theme.SpacerCustomColor

@Composable
fun LikeListScreen(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit,
    likeList: List<LikeListEntity>
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(
            modifier = Modifier,
            title = "Like 목록",
            onBackStack = onBackStack
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        if (likeList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    25.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                items(likeList) { likeListEntity ->
                    LikeCardView(
                        likeListEntity = likeListEntity,
                        onDelete = { },
                        onSend = { }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikeListScreenPreview() {

    val items = List(10) {
        LikeListEntity(
            id_email = "",
            age = 0,
            city = "",
            thumbnail = "",
            nickname = "워니"
        )
    }

    LikeListScreen(
        modifier = Modifier.fillMaxSize(),
        likeList = items,
        onBackStack = {}
    )
}