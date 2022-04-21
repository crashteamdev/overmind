package dev.crashteam.overmind.converter.thrift

import com.google.protobuf.Struct
import dev.crashteam.kz.fetcher.ProductFetch
import dev.crashteam.overmind.converter.DataConverter
import dev.crashteam.overmind.converter.thrift.model.*
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ProductFetchConverter : DataConverter<ProductFetch, KazanExpressProductFetch> {

    override fun convert(source: ProductFetch): KazanExpressProductFetch {
        val productStruct = source.product
        val sellerStruct = source.seller
        val reviewsListValue = source.reviews
        return KazanExpressProductFetch(
            fetchTime = source.fetchTime,
            product = KazanExpressProduct(
                id = productStruct.getFieldsOrThrow("id").numberValue.toLong(),
                title = productStruct.getFieldsOrThrow("title").stringValue,
                category = convertCategory(productStruct.getFieldsOrThrow("category").structValue),
                rating = BigDecimal.valueOf(productStruct.getFieldsOrThrow("rating").numberValue),
                reviewsAmount = productStruct.getFieldsOrThrow("reviewsAmount").numberValue.toInt(),
                ordersAmount = productStruct.getFieldsOrThrow("ordersAmount").numberValue.toLong(),
                rOrdersAmount = productStruct.getFieldsOrThrow("rOrdersAmount").numberValue.toLong(),
                totalAvailableAmount = productStruct.getFieldsOrThrow("totalAvailableAmount").numberValue.toLong(),
                charityCommission = productStruct.getFieldsOrThrow("charityCommission").numberValue.toInt(),
                description = productStruct.getFieldsOrThrow("description").stringValue,
                comments = productStruct.getFieldsOrThrow("comments").listValue.valuesList.map {
                    val commentType = it.structValue.getFieldsOrThrow("commentType").stringValue
                    val comment = it.structValue.getFieldsOrThrow("comment").stringValue
                    KazanExpressProductComment(commentType, comment)
                },
                attributes = productStruct.getFieldsOrThrow("attributes").listValue.valuesList.map { it.stringValue },
                tags = productStruct.getFieldsOrThrow("tags").listValue.valuesList.map { it.stringValue },
                photos = productStruct.getFieldsOrThrow("photos").listValue.valuesList.map {
                    val photoKey = it.structValue.getFieldsOrThrow("photoKey").stringValue
                    val color = it.structValue.getFieldsOrThrow("color").stringValue
                    val hasVerticalPhoto = it.structValue.getFieldsOrThrow("hasVerticalPhoto").boolValue
                    KazanExpressPhoto(photoKey, color, hasVerticalPhoto)
                },
                characteristics = productStruct.getFieldsOrThrow("characteristics").listValue.valuesList.map {
                    KazanExpressCharacteristic(
                        id = it.structValue.getFieldsOrThrow("id").numberValue.toInt(),
                        title = it.structValue.getFieldsOrThrow("title").stringValue,
                        values = it.structValue.getFieldsOrThrow("values").listValue.valuesList.map {
                            KazanExpressCharacteristicValue(
                                id = it.structValue.getFieldsOrThrow("id").numberValue.toInt(),
                                title = it.structValue.getFieldsOrThrow("title").stringValue,
                                value = it.structValue.getFieldsOrThrow("value").stringValue
                            )
                        }
                    )
                },
                skuList = productStruct.getFieldsOrThrow("skuList").listValue.valuesList.map {
                    KazanExpressProductSku(
                        id = it.structValue.getFieldsOrThrow("id").numberValue.toLong(),
                        characteristics = it.structValue.getFieldsOrThrow("characteristics").listValue.valuesList.map {
                            KazanExpressProductSkuCharacteristic(
                                charIndex = it.structValue.getFieldsOrThrow("charIndex").numberValue.toInt(),
                                valueIndex = it.structValue.getFieldsOrThrow("valueIndex").numberValue.toInt()
                            )
                        },
                        availableAmount = it.structValue.getFieldsOrThrow("availableAmount").numberValue.toLong(),
                        fullPrice = BigDecimal.valueOf(it.structValue.getFieldsOrThrow("fullPrice").numberValue).setScale(2),
                        charityProfit = BigDecimal.valueOf(it.structValue.getFieldsOrThrow("charityProfit").numberValue).setScale(2),
                        purchasePrice = BigDecimal.valueOf(it.structValue.getFieldsOrThrow("purchasePrice").numberValue).setScale(2),
                        barcode = it.structValue.getFieldsOrThrow("barcode").stringValue,
                        vat = KazanExpressProductSkuVat(
                            type = it.structValue.getFieldsOrThrow("vat").structValue.getFieldsOrThrow("type").stringValue,
                            vatAmount = BigDecimal.valueOf(
                                it.structValue.getFieldsOrThrow("vat").structValue.getFieldsOrThrow(
                                    "vatAmount"
                                ).numberValue
                            ).setScale(2),
                            price = BigDecimal.valueOf(
                                it.structValue.getFieldsOrThrow("vat").structValue.getFieldsOrThrow(
                                    "price"
                                ).numberValue
                            ).setScale(2)
                        )
                    )
                },
                seller = KazanExpressProductSeller(
                    id = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("id").numberValue.toLong(),
                    title = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("title").stringValue,
                    link = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("link").stringValue,
                    description = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("description").stringValue,
                    registrationDate = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("registrationDate").stringValue,
                    rating = BigDecimal.valueOf(productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("rating").numberValue),
                    reviews = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("reviews").numberValue.toLong(),
                    orders = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("orders").numberValue.toLong(),
                    official = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("official").boolValue,
                    contacts = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("contacts").listValue.valuesList.map {
                        KazanExpressProductSellerContact(
                            type = it.structValue.getFieldsOrThrow("type").stringValue,
                            value = it.structValue.getFieldsOrThrow("value").stringValue
                        )
                    },
                    sellerAccountId = productStruct.getFieldsOrThrow("seller").structValue.getFieldsOrThrow("sellerAccountId").numberValue.toLong(),
                ),
                isEco = productStruct.getFieldsOrThrow("isEco").boolValue,
                isPerishable = productStruct.getFieldsOrThrow("isPerishable").boolValue,
                hasVerticalPhoto = productStruct.getFieldsOrThrow("hasVerticalPhoto").boolValue,
                showKitty = productStruct.getFieldsOrThrow("showKitty").boolValue,
                bonusProduct = productStruct.getFieldsOrThrow("bonusProduct").boolValue,
                adultCategory = productStruct.getFieldsOrThrow("adultCategory").boolValue,
                colorPhotoPreview = productStruct.getFieldsOrThrow("colorPhotoPreview").boolValue,
            ),
            seller = KazanExpressSellerAdditional(
                id = sellerStruct.getFieldsOrThrow("id").numberValue.toLong(),
                title = sellerStruct.getFieldsOrThrow("title").stringValue,
                link = sellerStruct.getFieldsOrThrow("link").stringValue,
                description = sellerStruct.getFieldsOrThrow("description").stringValue,
                hasCharityProducts = sellerStruct.getFieldsOrThrow("hasCharityProducts").boolValue,
                registrationDate = sellerStruct.getFieldsOrThrow("registrationDate").numberValue.toLong(),
                rating = BigDecimal.valueOf(sellerStruct.getFieldsOrThrow("rating").numberValue),
                reviews = sellerStruct.getFieldsOrThrow("reviews").numberValue.toInt(),
                orders = sellerStruct.getFieldsOrThrow("orders").numberValue.toLong(),
                official = sellerStruct.getFieldsOrThrow("official").boolValue,
                categories = sellerStruct.getFieldsOrThrow("categories").listValue.valuesList.map {
                    convertSellerCategory(it.structValue)
                },
                sellerAccountId = sellerStruct.getFieldsOrThrow("sellerAccountId").numberValue.toLong(),
                ogrnip = sellerStruct.getFieldsOrThrow("info").structValue.getFieldsOrThrow("ogrnip").stringValue,
                accountName = sellerStruct.getFieldsOrThrow("info").structValue.getFieldsOrThrow("accountName").stringValue
            ),
            reviews = reviewsListValue.valuesList.map {
                KazanExpressProductReview(
                    reviewId = it.structValue.getFieldsOrThrow("reviewId").numberValue.toLong(),
                    productId = it.structValue.getFieldsOrThrow("productId").numberValue.toLong(),
                    date = it.structValue.getFieldsOrThrow("date").numberValue.toLong(),
                    edited = it.structValue.getFieldsOrThrow("edited").boolValue,
                    customer = it.structValue.getFieldsOrThrow("customer").stringValue,
                    reply = it.structValue.descriptorForType.findFieldByName("reply")?.let { reply ->
                        KazanExpressProductReviewReply(
                            id = it.structValue.getFieldsOrThrow("reply").structValue.getFieldsOrThrow("id").numberValue.toLong(),
                            date = it.structValue.getFieldsOrThrow("reply").structValue.getFieldsOrThrow("date").numberValue.toLong(),
                            edited = it.structValue.getFieldsOrThrow("reply").structValue.getFieldsOrThrow("edited").boolValue,
                            content = it.structValue.getFieldsOrThrow("reply").structValue.getFieldsOrThrow("content").stringValue,
                            shop = it.structValue.getFieldsOrThrow("reply").structValue.getFieldsOrThrow("shop").stringValue
                        )
                    },
                    rating = it.structValue.getFieldsOrThrow("rating").numberValue.toInt().toShort(),
                    characteristics = it.structValue.getFieldsOrThrow("characteristics").listValue.valuesList.map {
                        KazanExpressProductReviewCharacteristic(
                            characteristic = it.structValue.getFieldsOrThrow("characteristic").stringValue,
                            characteristicValue = it.structValue.getFieldsOrThrow("characteristicValue").stringValue
                        )
                    },
                    content = it.structValue.getFieldsOrThrow("content").stringValue,
                    photos = it.structValue.getFieldsOrThrow("photos").listValue.valuesList.map {
                        KazanExpressPhoto(
                            photoKey = it.structValue.getFieldsOrThrow("photoKey").stringValue,
                            color = it.structValue.getFieldsOrThrow("color").stringValue,
                            hasVerticalPhoto = it.structValue.getFieldsOrThrow("hasVerticalPhoto").boolValue
                        )
                    },
                    status = it.structValue.getFieldsOrThrow("status").stringValue,
                    like = it.structValue.getFieldsOrThrow("like").boolValue,
                    dislike = it.structValue.getFieldsOrThrow("dislike").boolValue,
                    amountLike = it.structValue.getFieldsOrThrow("amountLike").numberValue.toLong(),
                    amountDislike = it.structValue.getFieldsOrThrow("amountDislike").numberValue.toLong(),
                    id = it.structValue.getFieldsOrThrow("id").numberValue.toLong(),
                    isAnonymous = it.structValue.getFieldsOrThrow("isAnonymous").boolValue
                )
            }
        )
    }

    private fun convertCategory(category: Struct): KazanExpressProductCategory {
        return KazanExpressProductCategory(
            id = category.getFieldsOrThrow("id").numberValue.toLong(),
            title = category.getFieldsOrThrow("title").stringValue,
            productAmount = category.getFieldsOrThrow("productAmount").stringValue,
            parent = if (!category.getFieldsOrThrow("parent").hasNullValue()) {
                convertCategory(
                    category.getFieldsOrThrow(
                        "parent"
                    ).structValue
                )
            } else null
        )
    }

    private fun convertSellerCategory(sellerCategory: Struct): KazanExpressSellerAdditionalCategory {
        return KazanExpressSellerAdditionalCategory(
            id = sellerCategory.getFieldsOrThrow("id").numberValue.toLong(),
            productAmount = sellerCategory.getFieldsOrThrow("productAmount").numberValue.toLong(),
            adult = sellerCategory.getFieldsOrThrow("adult").boolValue,
            eco = sellerCategory.getFieldsOrThrow("eco").boolValue,
            title = sellerCategory.getFieldsOrThrow("title").stringValue,
            seoMetaTag = sellerCategory.getFieldsOrThrow("seoMetaTag").stringValue,
            seoHeader = sellerCategory.getFieldsOrThrow("seoHeader").stringValue,
            children = sellerCategory.getFieldsOrThrow("children").listValue.valuesList.map { convertSellerCategory(it.structValue) },
            path = sellerCategory.getFieldsOrThrow("path").listValue.valuesList.map { it.numberValue.toLong() }
        )
    }

}
