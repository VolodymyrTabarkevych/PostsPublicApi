package ua.tabarkevych.postspublicapi.room.post

import ua.tabarkevych.postspublicapi.model.Post
import ua.tabarkevych.postspublicapi.util.EntityMapper

class PostDatabaseMapper : EntityMapper<PostDatabaseEntity, Post> {
    override fun mapToDomainModel(model: PostDatabaseEntity): Post {
        return Post(
            id = model.id,
            title = model.title,
            body = model.body
        )
    }

    override fun mapFromDomainModel(domainModel: Post): PostDatabaseEntity {
        return PostDatabaseEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun toDomainList(initial: List<PostDatabaseEntity>): List<Post> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Post>): List<PostDatabaseEntity> {
        return initial.map { mapFromDomainModel(it) }
    }
}