package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

typealias FriendsList = List<FriendInFirebase>
typealias FriendsListResponse = Resource<FriendsList>
typealias AddFriendResponse = Resource<Boolean>
typealias RejectResponse = Resource<Boolean>
typealias AcceptResponse = Resource<Boolean>
typealias DeleteFriendResponse = Resource<Boolean>

interface FriendsRepository {

    // Firestore

    fun getFriends(id_email: String): Flow<FriendsListResponse>

    suspend fun addFriend(id_email: String, friend: FriendInFirebase): AddFriendResponse

    suspend fun rejectRequest(id_email: String, friend: FriendInFirebase): RejectResponse

    suspend fun acceptRequest(id_email: String, friend: FriendInFirebase): AcceptResponse

    suspend fun deleteFriend(id_email: String, friend: FriendInFirebase): DeleteFriendResponse

    suspend fun initializeFriendsList(id_email: String): Resource<Boolean>

    suspend fun createTestDummy(id_email: String, friend_email: String, dummy: FriendInFirebase): Resource<Boolean>
}