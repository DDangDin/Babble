package com.myschoolproject.babble.domain.repository

import android.net.Uri

interface FirebaseStorageRepository {

    suspend fun setImage(id_email: String, uri: String)

    suspend fun getImage(id_email: String): Uri?
}