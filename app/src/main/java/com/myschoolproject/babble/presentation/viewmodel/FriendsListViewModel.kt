package com.myschoolproject.babble.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val firestoreUseCases: FirestoreUseCases
): ViewModel() {


}