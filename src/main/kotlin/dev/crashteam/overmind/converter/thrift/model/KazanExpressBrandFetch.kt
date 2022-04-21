package dev.crashteam.overmind.converter.thrift.model

data class KazanExpressBrandFetch(
    val id: Long,
    val name: String,
    val image: String,
    val description: String?,
    val categoryId: Long,
    val productIds: List<Long>
)
