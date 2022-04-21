package dev.crashteam.overmind.converter.thrift.model

import com.google.protobuf.Timestamp
import java.math.BigDecimal

data class KazanExpressProductFetch(
    val fetchTime: Timestamp,
    val product: KazanExpressProduct,
    val seller: KazanExpressSellerAdditional,
    val reviews: List<KazanExpressProductReview>
)

data class KazanExpressProductCategory(
    val id: Long,
    val title: String,
    val productAmount: String,
    val parent: KazanExpressProductCategory?,
)

data class KazanExpressProduct(
    val id: Long,
    val title: String,
    val category: KazanExpressProductCategory,
    val rating: BigDecimal,
    val reviewsAmount: Int,
    val ordersAmount: Long,
    val rOrdersAmount: Long,
    val totalAvailableAmount: Long,
    val charityCommission: Int,
    val description: String,
    val comments: List<KazanExpressProductComment>,
    val attributes: List<String>,
    val tags: List<String>,
    val photos: List<KazanExpressPhoto>,
    val characteristics: List<KazanExpressCharacteristic>,
    val skuList: List<KazanExpressProductSku>,
    val seller: KazanExpressProductSeller,
    val isEco: Boolean,
    val isPerishable: Boolean,
    val hasVerticalPhoto: Boolean,
    val showKitty: Boolean,
    val bonusProduct: Boolean,
    val adultCategory: Boolean,
    val colorPhotoPreview: Boolean,
)

data class KazanExpressProductSeller(
    val id: Long,
    val title: String,
    val link: String,
    val description: String,
    val registrationDate: String,
    val rating: BigDecimal,
    val reviews: Long,
    val orders: Long,
    val official: Boolean,
    val contacts: List<KazanExpressProductSellerContact>,
    val sellerAccountId: Long,
)

data class KazanExpressProductSellerContact(
    val type: String,
    val value: String,
)

data class KazanExpressProductSku(
    val id: Long,
    val characteristics: List<KazanExpressProductSkuCharacteristic>,
    val availableAmount: Long,
    val fullPrice: BigDecimal,
    val charityProfit: BigDecimal,
    val purchasePrice: BigDecimal,
    val barcode: String,
    val vat: KazanExpressProductSkuVat
)

data class KazanExpressProductSkuVat(
    val type: String,
    val vatAmount: BigDecimal,
    val price: BigDecimal,
)

data class KazanExpressProductSkuCharacteristic(
    val charIndex: Int,
    val valueIndex: Int
)

data class KazanExpressCharacteristic(
    val id: Int,
    val title: String,
    val values: List<KazanExpressCharacteristicValue>
)

data class KazanExpressCharacteristicValue(
    val id: Int,
    val title: String,
    val value: String,
)

data class KazanExpressPhoto(
    val photoKey: String,
    val color: String?,
    val hasVerticalPhoto: Boolean
)

data class KazanExpressProductComment(
    val commentType: String,
    val comment: String
)

data class KazanExpressSellerAdditional(
    val id: Long,
    val title: String,
    val link: String,
    val description: String,
    val hasCharityProducts: Boolean,
    val registrationDate: Long,
    val rating: BigDecimal,
    val reviews: Int,
    val orders: Long,
    val official: Boolean,
    val categories: List<KazanExpressSellerAdditionalCategory>,
    val sellerAccountId: Long,
    val ogrnip: String,
    val accountName: String,
)

data class KazanExpressSellerAdditionalCategory(
    val id: Long,
    val productAmount: Long,
    val adult: Boolean,
    val eco: Boolean,
    val title: String,
    val seoMetaTag: String,
    val seoHeader: String,
    val children: List<KazanExpressSellerAdditionalCategory>?,
    val path: List<Long>
)

data class KazanExpressProductReview(
    val reviewId: Long,
    val productId: Long,
    val date: Long,
    val edited: Boolean,
    val customer: String,
    val reply: KazanExpressProductReviewReply?,
    val rating: Short,
    val characteristics: List<KazanExpressProductReviewCharacteristic>,
    val content: String,
    val photos: List<KazanExpressPhoto>,
    val status: String,
    val like: Boolean,
    val dislike: Boolean,
    val amountLike: Long,
    val amountDislike: Long,
    val id: Long,
    val isAnonymous: Boolean
)

data class KazanExpressProductReviewCharacteristic(
    val characteristic: String,
    val characteristicValue: String,
)

data class KazanExpressProductReviewReply(
    val id: Long,
    val date: Long,
    val edited: Boolean,
    val content: String,
    val shop: String,
)
