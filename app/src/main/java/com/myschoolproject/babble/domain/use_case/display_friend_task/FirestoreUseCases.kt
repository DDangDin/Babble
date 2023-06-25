package com.myschoolproject.babble.domain.use_case.display_friend_task

data class FirestoreUseCases(
    val getDisplayFriends: GetDisplayFriends,
    val addDisplayFriend: AddDisplayFriend,
    val deleteDisplayFriends: DeleteDisplayFriends,
    val updateThumbnailForDisplay: UpdateThumbnailForDisplay
)