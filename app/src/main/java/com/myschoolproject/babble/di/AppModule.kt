package com.myschoolproject.babble.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myschoolproject.babble.data.repository.AuthRepositoryImpl
import com.myschoolproject.babble.data.repository.ChatRepositoryImpl
import com.myschoolproject.babble.data.repository.ChattingRepositoryImpl
import com.myschoolproject.babble.data.repository.DisplayFriendsRepositoryImpl
import com.myschoolproject.babble.data.repository.FirebaseStorageRepositoryImpl
import com.myschoolproject.babble.data.repository.FriendsRepositoryImpl
import com.myschoolproject.babble.data.repository.LikeListRepositoryImpl
import com.myschoolproject.babble.data.repository.TestRepositoryImpl
import com.myschoolproject.babble.data.repository.UserRepositoryImpl
import com.myschoolproject.babble.data.source.local.ChatDao
import com.myschoolproject.babble.data.source.local.ChatDatabase
import com.myschoolproject.babble.data.source.local.LikeListDao
import com.myschoolproject.babble.data.source.local.LikeListDatabase
import com.myschoolproject.babble.data.source.remote.BabbleApi
import com.myschoolproject.babble.domain.repository.AuthRepository
import com.myschoolproject.babble.domain.repository.ChatRepository
import com.myschoolproject.babble.domain.repository.ChattingRepository
import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository
import com.myschoolproject.babble.domain.repository.FirebaseStorageRepository
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.domain.repository.LikeListRepository
import com.myschoolproject.babble.domain.repository.TestRepository
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.domain.use_case.display_friend_task.AddDisplayFriend
import com.myschoolproject.babble.domain.use_case.display_friend_task.DeleteDisplayFriends
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import com.myschoolproject.babble.domain.use_case.display_friend_task.GetDisplayFriends
import com.myschoolproject.babble.domain.use_case.display_friend_task.UpdateThumbnailForDisplay
import com.myschoolproject.babble.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBabbleApi(): BabbleApi {

        fun String?.isJsonObject(): Boolean {
            return this?.startsWith("{") == true && this.endsWith("}")
        }

        fun String?.isJsonArray(): Boolean {
            return this?.startsWith("[") == true && this.endsWith("]")
        }

        val client = OkHttpClient
            .Builder()
//            .readTimeout(25, TimeUnit.SECONDS)
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                else -> {
                    try {
                        Log.d("Retrofit_Log", JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d("Retrofit_Log", message)
                    }
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.BABBLE_REAL_API_SERVER)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BabbleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTestRepository(api: BabbleApi): TestRepository {
        return TestRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: BabbleApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    // ---------------------- Firebase ----------------------
    // Firebase - Auth
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    // Firebase - Firestore
    @Provides
    @Singleton
    @Named("display_friends")
    fun provideDisplayFriendRef() = Firebase.firestore.collection(Constants.DISPLAY_FRIENDS)

    @Provides
    @Singleton
    fun provideDisplayFriendsRepository(
        @Named("display_friends")
        displayFriendRef: CollectionReference
    ): DisplayFriendsRepository {
        return DisplayFriendsRepositoryImpl(displayFriendRef)
    }

    @Provides
    @Singleton
    @Named("friends")
    fun provideFriendsRef() = Firebase.firestore.collection(Constants.FRIENDS_LIST)

    @Provides
    @Singleton
    fun provideFriendsRepository(
        @Named("friends")
        friendsRef: CollectionReference,
    ): FriendsRepository {
        return FriendsRepositoryImpl(friendsRef)
    }

    @Provides
    @Singleton
    fun provideChatRef() = Firebase.database.reference

    @Provides
    @Singleton
    fun provideChattingRepository(chatRef: DatabaseReference): ChattingRepository {
        return ChattingRepositoryImpl(chatRef)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        dao: ChatDao
    ): ChatRepository {
        return ChatRepositoryImpl(dao)
    }

    // Firebase - Storage
    @Provides
    @Singleton
    fun provideFirebaseStorageRef() = FirebaseStorage.getInstance().reference

    @Provides
    @Singleton
    fun provideFirebaseStorageRepository(storage: StorageReference): FirebaseStorageRepository {
        return FirebaseStorageRepositoryImpl(storage)
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repo: DisplayFriendsRepository
    ) = FirestoreUseCases(
        getDisplayFriends = GetDisplayFriends(repo),
        addDisplayFriend = AddDisplayFriend(repo),
        deleteDisplayFriends = DeleteDisplayFriends(repo),
        updateThumbnailForDisplay = UpdateThumbnailForDisplay(repo)
    )
    // ---------------------- Firebase ----------------------


    // ---------------------- Room Database ----------------------
    @Provides
    @Singleton
    fun provideLikeListDatabase(@ApplicationContext context: Context): LikeListDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LikeListDatabase::class.java,
            "like_list_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLikeListDao(appDatabase: LikeListDatabase) = appDatabase.LikeListDao()

    @Provides
    @Singleton
    fun provideLikeListRepository(
        dao: LikeListDao
    ): LikeListRepository {
        return LikeListRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): ChatDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ChatDatabase::class.java,
            "chat_list_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideChatDao(appDatabase: ChatDatabase) = appDatabase.ChatDao()
}