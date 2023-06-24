package com.myschoolproject.babble.domain.use_case.display_friend_task

import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository

class DeleteDisplayFriends(
    private val repo: DisplayFriendsRepository
) {
    suspend operator fun invoke(displayFriendId: String) = repo.deleteDisplayFriendtoFirestore(displayFriendId)
}