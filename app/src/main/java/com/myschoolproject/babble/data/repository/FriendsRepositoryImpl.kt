package com.myschoolproject.babble.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.data.source.remote.firebase.getEmptyFriend
import com.myschoolproject.babble.domain.repository.AcceptResponse
import com.myschoolproject.babble.domain.repository.AddFriendResponse
import com.myschoolproject.babble.domain.repository.DeleteFriendResponse
import com.myschoolproject.babble.domain.repository.FriendsListResponse
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.domain.repository.RejectResponse
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FriendsRepositoryImpl constructor(
    private val friendsRef: CollectionReference,
) : FriendsRepository {

    override fun getFriends(id_email: String): Flow<FriendsListResponse> = callbackFlow {
        Log.d("ServerCall_Log_Firebase(get friends_list)", "Firestore get friends_list")
        Resource.Loading(null)

        val snapshotListener = friendsRef.document(id_email).collection(Constants.FRIENDS_LIST)
            .addSnapshotListener { snapshot, e ->
                val friendsResponse = if (snapshot != null) {
                    val friends = snapshot.toObjects(FriendInFirebase::class.java)
                    Resource.Success(friends)
                } else {
                    Resource.Error(e?.localizedMessage ?: "Firestore(get friends_list) Error")
                }
                trySend(friendsResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addFriend(user_data: FriendInFirebase, friend: FriendInFirebase): AddFriendResponse =
        try {
            Log.d("ServerCall_Log_Firebase(add_friend)", "Firestore add_friend")
            friendsRef
                .document(friend.id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(user_data.id_email)
                .set(user_data)
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(add_friend) Error")
        }

    override suspend fun rejectRequest(id_email: String, friend: FriendInFirebase): RejectResponse =
        try {
            Log.d("ServerCall_Log_Firebase(reject_request)", "Firestore reject_request")
            friendsRef
                .document(id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(friend.id_email)
                .delete()
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(reject_request) Error")
        }

    override suspend fun acceptRequest(user_data: FriendInFirebase, friend: FriendInFirebase): AcceptResponse =
        try {
            Log.d("ServerCall_Log_Firebase(accept_request)", "Firestore accept_request")
            friendsRef
                .document(user_data.id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(friend.id_email)
                .set(
                    friend.copy(
                        friend_check = true
                    )
                )
            friendsRef
                .document(friend.id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(user_data.id_email)
                .set(
                    user_data.copy(
                        friend_check = true
                    )
                )
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(accept_request) Error")
        }

    override suspend fun deleteFriend(
        id_email: String,
        friend: FriendInFirebase
    ): DeleteFriendResponse =
        try {
            Log.d("ServerCall_Log_Firebase(delete_friend)", "Firestore delete_friend")
            friendsRef
                .document(id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(friend.id_email)
                .delete()

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(delete_friend) Error")
        }

    override suspend fun initializeFriendsList(id_email: String): Resource<Boolean> =
        try {
            Log.d("ServerCall_Log_Firebase(init_friends_list)", "Firestore init_friends_list")
            friendsRef
                .document(id_email)
                .collection(Constants.FRIENDS_LIST)
                .document("test")
                .set(getEmptyFriend().copy(id_email = "test"))
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(init_friends_list) Error")
        }

    override suspend fun createTestDummy(
        id_email: String,
        friend_email: String,
        dummy: FriendInFirebase
    ): Resource<Boolean> =
        try {
            friendsRef
                .document(id_email)
                .collection(Constants.FRIENDS_LIST)
                .document(friend_email)
                .set(dummy)
                .await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore Error")
        }
}
