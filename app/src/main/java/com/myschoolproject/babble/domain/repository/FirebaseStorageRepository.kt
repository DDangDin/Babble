package com.myschoolproject.babble.domain.repository

import android.net.Uri
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseStorageRepository {

    suspend fun setImage(id_email: String, uri: Uri): Flow<Resource<Uri>>

    suspend fun getImage(id_email: String): Flow<Resource<Uri>>
}