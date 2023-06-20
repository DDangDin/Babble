package com.myschoolproject.babble.domain.use_case.display_friend_task

import com.myschoolproject.babble.data.source.remote.response.dto.user.DisplayFriend
import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository

class GetDisplayFriends(
    private val repo: DisplayFriendsRepository
) {
    operator fun invoke() = repo.getDisplayFriendsFromFirestore()
}