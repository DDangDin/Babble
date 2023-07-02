package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChattingRepository {

    fun getChatMessages(my_email: String, friend_email: String): Flow<Resource<List<Chat>>>
}