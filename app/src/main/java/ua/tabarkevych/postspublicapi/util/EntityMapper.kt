package ua.tabarkevych.postspublicapi.util

interface EntityMapper<M, D> {
    fun mapToDomainModel(model: M): D

    fun mapFromDomainModel(domainModel: D): M
}