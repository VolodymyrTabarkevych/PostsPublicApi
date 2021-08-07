package ua.tabarkevych.postspublicapi.network.model

import ua.tabarkevych.postspublicapi.model.Post
import ua.tabarkevych.postspublicapi.util.EntityMapper

class PostNetworkMapper : EntityMapper<PostNetworkEntity, Post> {
    override fun mapToDomainModel(model: PostNetworkEntity): Post {
        return Post(
            id = model.id,
            title = model.title,
            body = model.body
        )
    }

    override fun mapFromDomainModel(domainModel: Post): PostNetworkEntity {
        return PostNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body
        )
    }

    fun toDomainList(initial: List<PostNetworkEntity>): List<Post> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Post>): List<PostNetworkEntity> {
        return initial.map { mapFromDomainModel(it) }
    }
}