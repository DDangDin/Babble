package com.myschoolproject.babble.data.repository

import com.myschoolproject.babble.data.source.local.LikeListDao
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.domain.repository.LikeListRepository

class LikeListRepositoryImpl(
    private val dao: LikeListDao
): LikeListRepository {

    override suspend fun getLikeList(): List<LikeListEntity> {
        return dao.getLikeList()
    }

    override suspend fun insertLikeLike(likeList: LikeListEntity) {
        dao.insertLikeLike(likeList)
    }

    override suspend fun deleteLikeList() {
        dao.deleteLikeList()
    }

    override suspend fun isEmpty(): Boolean {
        return dao.isEmpty()
    }
}