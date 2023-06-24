package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.domain.repository.LikeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                getLikeList()
            }

//            likeListRepository.deleteLikeList()
        }
    }

    private fun getLikeList() {
        viewModelScope.launch {
            likeList.value = likeListRepository.getLikeList()
        }
    }

    fun deleteFriendFromLikeList(likeFriend: LikeListEntity) {
        viewModelScope.launch {
            likeListRepository.deleteFriendFromLikeList(likeFriend)
            // 데이터 지우고 나서 콜백이나 상태 값의 변경이 이뤄지지 않아
            // compose가 재구성이 되지 않음
            // 그래서 get 호출
            getLikeList()
        }
    }
}