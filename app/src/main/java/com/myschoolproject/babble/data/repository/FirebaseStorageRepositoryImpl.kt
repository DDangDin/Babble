package com.myschoolproject.babble.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myschoolproject.babble.domain.repository.FirebaseStorageRepository
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseStorageRepositoryImpl(
    private val storageRef: StorageReference
) : FirebaseStorageRepository {

    override suspend fun setImage(id_email: String, uri: Uri): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading())

        try {
            val imageRef = storageRef.child("${id_email}.png")
            val uploadTask = imageRef.putFile(uri).await()
            val uriResult = storageRef.child("${id_email}.png").downloadUrl.await()
            Log.d("FirebaseStorage_Log (upload)", uriResult.toString())
            emit(Resource.Success(uriResult))
        } catch (e: Exception) {
            Log.d("FirebaseStorage_Log (upload_error)", "upload error")
            emit(Resource.Error(e.localizedMessage ?: "Firebase Storage Error"))
        }
    }

    override suspend fun getImage(id_email: String): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading())

        try {
            val uri = storageRef.child("${id_email}.png").downloadUrl.await()
            Log.d("FirebaseStorage_Log (get)", uri.toString())
            emit(Resource.Success(uri!!))
        } catch (e: Exception) {
            Log.d("FirebaseStorage_Log (get_error)","get error")
            emit(Resource.Error(e.localizedMessage ?: "Firebase Storage Error"))
        }
    }
}