package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

typealias DisplayFriends = List<DisplayFriend>
typealias DisplayFriendsResponse = Resource<DisplayFriends>
typealias AddDisplayFriendResponse = Resource<Boolean>
typealias DeleteDisplayFriendResponse = Resource<Boolean>

interface DisplayFriendsRepository {

    fun getDisplayFriendsFromFirestore(): Flow<DisplayFriendsResponse>

    suspend fun addDisplayFriendtoFirestore(displayFriend: DisplayFriend): AddDisplayFriendResponse

    suspend fun deleteDisplayFriendtoFirestore(displayFriendId: String): DeleteDisplayFriendResponse
}
