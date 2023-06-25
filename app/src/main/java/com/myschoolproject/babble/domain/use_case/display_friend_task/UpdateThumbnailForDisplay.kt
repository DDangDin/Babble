package com.myschoolproject.babble.domain.use_case.display_friend_task

import com.myschoolproject.babble.domain.repository.DisplayFriendsRepository

class UpdateThumbnailForDisplay(
    private val repo: DisplayFriendsRepository
) {

    suspend operator fun invoke(email: String, uri: String) = repo.updateThumbnailForDisplay(email, uri)
}