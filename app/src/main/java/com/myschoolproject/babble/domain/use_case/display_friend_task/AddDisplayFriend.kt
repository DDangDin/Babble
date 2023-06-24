package com.myschoolproject.babble.domain.use_case.display_friend_task

import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository

class AddDisplayFriend(
    private val repo: DisplayFriendsRepository
) {

    suspend operator fun invoke(
        displayFriend: DisplayFriend
    ) = repo.addDisplayFriendtoFirestore(displayFriend)
}