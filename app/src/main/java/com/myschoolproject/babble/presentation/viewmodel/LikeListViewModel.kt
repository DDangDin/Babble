package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.domain.repository.LikeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeListViewModel @Inject constructor(
    private val likeListRepository: LikeListRepository
) : ViewModel() {

    var likeList = mutableStateOf(emptyList<LikeListEntity>())
        private set

    init {
        viewModelScope.launch {
//            if (!likeListRepository.isEmpty()) {
//                likeList.value = likeListRepository.getLikeList()
//            }

            if (likeListRepository.getLikeList().isNotEmpty()) {
                likeList.value = likeListRepository.getLikeList()
            }

//            likeListRepository.deleteLikeList()
        }
    }
}