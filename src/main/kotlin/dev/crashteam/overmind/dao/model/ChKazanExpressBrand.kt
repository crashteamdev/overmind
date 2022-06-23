package dev.crashteam.overmind.dao.model

import com.google.protobuf.Timestamp

data class ChKazanExpressBrand(
    val fetchTime: Timestamp,
    val brandId: Long,
    val name: String,
    val description: String,
    val image: String,
    val categoryId: Long,
    val productIds: List<String>
)
