package com.myschoolproject.babble.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myschoolproject.babble.domain.repository.FirebaseStorageRepository
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseStorageRepositoryImpl(
    private val storageRef: StorageReference
): FirebaseStorageRepository {

    override suspend fun setImage(id_email: String, uri: String) {
        val file = Uri.fromFile(File(uri))
        val imageRef = storageRef.child(id_email)
        val uploadTask = imageRef.putFile(file)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            Log.d("FirebaseStorage_Log (upload)", taskSnapshot.totalByteCount.toString())
        }
    }

    override suspend fun getImage(id_email: String): Uri? {
        var uri: Uri? = null
        storageRef.child("${id_email}.png").downloadUrl.addOnSuccessListener {
            Log.d("FirebaseStorage_Log (download)", it.toString())
            uri = it
        }.addOnFailureListener {
            Log.d("FirebaseStorage_Log (error)", it.localizedMessage ?: "error")
        }
        return uri
    }
}