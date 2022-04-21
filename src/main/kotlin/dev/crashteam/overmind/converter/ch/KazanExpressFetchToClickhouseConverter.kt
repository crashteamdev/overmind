package dev.crashteam.overmind.converter.ch

import dev.crashteam.overmind.converter.DataConverter
import dev.crashteam.overmind.converter.ch.model.ChKazanExpressProductConverterResult
import dev.crashteam.overmind.converter.thrift.model.KazanExpressProductCategory
import dev.crashteam.overmind.converter.thrift.model.KazanExpressProductFetch
import dev.crashteam.overmind.dao.model.ChKazanExpressCharacteristic
import dev.crashteam.overmind.dao.model.ChKazanExpressProduct
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class KazanExpressFetchToClickhouseConverter :
    DataConverter<KazanExpressProductFetch, ChKazanExpressProductConverterResult> {

    override fun convert(source: KazanExpressProductFetch): ChKazanExpressProductConverterResult {
        return ChKazanExpressProductConverterResult(source.product.skuList.map { sku ->
            ChKazanExpressProduct(
                fetchTime = source.fetchTime,
                productId = source.product.id,
                skuId = sku.id,
                title = source.product.title,
                categoryPaths = categoryToPath(source.product.category),
                rating = source.product.rating,
                reviewsAmount = source.product.reviewsAmount,
                totalOrdersAmount = source.product.ordersAmount,
                totalAvailableAmount = source.product.totalAvailableAmount,
                availableAmount = sku.availableAmount,
                fullPrice = sku.fullPrice.movePointRight(2).toLong(),
                charityProfit = sku.charityProfit.movePointRight(2).toLong(),
                purchasePrice = sku.purchasePrice.movePointRight(2).toLong(),
                barCode = sku.barcode,
                vatType = sku.vat.type,
                vatAmount = sku.vat.vatAmount.movePointRight(2).toLong(),
                vatPrice = sku.vat.price.movePointRight(2).toLong(),
                charityCommission = source.product.charityCommission,
                attributes = source.product.attributes,
                tags = source.product.tags,
                photoKey = sku.characteristics.firstNotNullOfOrNull {
                    val productCharacteristic = source.product.characteristics[it.charIndex]
                    val characteristicValue = productCharacteristic.values[it.valueIndex]
                    val value = characteristicValue.value
                    source.product.photos.filter { photo -> photo.color != null }.find { photo -> photo.color == value }
                }?.photoKey ?: source.product.photos.firstOrNull()?.photoKey,
                characteristics = sku.characteristics.map {
                    val productCharacteristic = source.product.characteristics[it.charIndex]
                    val characteristicValue = productCharacteristic.values[it.valueIndex]
                    ChKazanExpressCharacteristic(productCharacteristic.title, characteristicValue.title)
                },
                sellerId = source.product.seller.id,
                sellerAccountId = source.product.seller.sellerAccountId,
                sellerTitle = source.seller.title,
                sellerLink = source.seller.link,
                sellerRegistrationDate = source.seller.registrationDate,
                sellerRating = source.seller.rating,
                sellerReviewsCount = source.seller.reviews,
                sellerOrders = source.seller.orders,
                sellerOfficial = source.seller.official,
                sellerContacts = source.product.seller.contacts.stream()
                    .collect(Collectors.toMap({ it.type }, { it.value })),
                isEco = source.product.isEco,
                isPerishable = source.product.isPerishable,
                hasVerticalPhoto = source.product.hasVerticalPhoto,
                showKitty = source.product.showKitty,
                bonusProduct = source.product.bonusProduct,
                adultCategory = source.product.adultCategory,
                colorPhotoPreview = source.product.colorPhotoPreview
            )
        })
    }

    private fun categoryToPath(category: KazanExpressProductCategory): List<Long> {
        val paths = mutableListOf<Long>()
        var nextCategory: KazanExpressProductCategory? = category
        while (nextCategory != null) {
            paths.add(category.id)
            nextCategory = nextCategory.parent
        }
        return paths.toList()
    }

}
