package com.myschoolproject.babble.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.domain.repository.DisplayFriends
import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DisplayFriendsRepositoryImpl constructor(
    private val displayFriendRef: CollectionReference
) : DisplayFriendsRepository {

    override fun getDisplayFriendsFromFirestore(): Flow<Resource<DisplayFriends>> = callbackFlow {
        Log.d("ServerCall_Log_Firebase", "Firestore Get")
        Resource.Loading(null)

        val snapshotListener = displayFriendRef.orderBy(Constants.DISPLAY_FRIENDS_ID)
            .addSnapshotListener { snapshot, e ->
                val displayFriendsResponse = if (snapshot != null) {
                    val displayFriends = snapshot.toObjects(DisplayFriend::class.java)
                    Log.d("HomeImagesState", displayFriends[0].thumbnail)
                    Resource.Success(displayFriends)
                } else {
                    Log.d("HomeImagesState", "Firestore Error!!!")
                    Resource.Error(e?.localizedMessage ?: "Firestore(GET data) Error")
                }
                trySend(displayFriendsResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addDisplayFriendtoFirestore(displayFriend: DisplayFriend): Resource<Boolean> =
        try {
            Log.d("ServerCall_Log_Firebase", "Firestore ADD")
            val id = displayFriend.id_email.ifEmpty { // id_email -> email
                displayFriendRef.document().id // 고유 id_email 값임
            }
            displayFriendRef.document(id).set(displayFriend).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(ADD) Error")
        }

    override suspend fun deleteDisplayFriendtoFirestore(displayFriendId: String): Resource<Boolean> =
        try {
            Log.d("ServerCall_Log_Firebase", "Firestore DELETE")
            displayFriendRef.document(displayFriendId).delete().await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Firestore(DELETE) Error")
        }
}