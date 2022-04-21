package dev.crashteam.overmind.dao.model

import com.google.protobuf.Timestamp
import java.math.BigDecimal

data class ChKazanExpressProduct(
    val fetchTime: Timestamp,
    val productId: Long,
    val skuId: Long,
    val title: String,
    val categoryPaths: List<Long>,
    val rating: BigDecimal,
    val reviewsAmount: Int,
    val totalOrdersAmount: Long,
    val totalAvailableAmount: Long,
    val availableAmount: Long,
    val fullPrice: Long,
    val charityProfit: Long,
    val purchasePrice: Long,
    val barCode: String,
    val vatType: String,
    val vatAmount: Long,
    val vatPrice: Long,
    val charityCommission: Int,
    val attributes: List<String>,
    val tags: List<String>,
    val photoKey: String?,
    val characteristics: List<ChKazanExpressCharacteristic>,
    val sellerId: Long,
    val sellerAccountId: Long,
    val sellerTitle: String,
    val sellerLink: String,
    val sellerRegistrationDate: Long,
    val sellerRating: BigDecimal,
    val sellerReviewsCount: Int,
    val sellerOrders: Long,
    val sellerOfficial: Boolean,
    val sellerContacts: Map<String, String>,
    val isEco: Boolean,
    val isPerishable: Boolean,
    val hasVerticalPhoto: Boolean,
    val showKitty: Boolean,
    val bonusProduct: Boolean,
    val adultCategory: Boolean,
    val colorPhotoPreview: Boolean,
)

data class ChKazanExpressCharacteristic(
    val type: String,
    val title: String
)
