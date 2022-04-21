package dev.crashteam.overmind.dao

import com.google.protobuf.util.Timestamps
import dev.crashteam.overmind.dao.model.ChKazanExpressProduct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.yandex.clickhouse.ClickHouseArray
import ru.yandex.clickhouse.domain.ClickHouseDataType
import java.sql.PreparedStatement
import java.time.Instant
import java.time.ZoneId
import java.util.stream.Collectors

@Repository
class CHProductRepository(
    @Qualifier("clickHouseJdbcTemplate") private val jdbcTemplate: JdbcTemplate
) {

    fun saveProducts(productFetchList: List<ChKazanExpressProduct>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO overmind.ke_product " +
                    "(timestamp, fetchTime, productId, skuId, title, rating, categoryPath, reviewsAmount," +
                    " totalOrdersAmount, totalAvailableAmount, availableAmount, charityCommission, attributes," +
                    " tags, photoKey, characteristics, sellerId, sellerAccountId, sellerTitle, sellerLink," +
                    " sellerRegistrationDate, sellerRating, sellerReviewsCount, sellerOrders, sellerOfficial, sellerContacts, " +
                    " isEco, isPerishable, hasVerticalPhoto, showKitty, bonusProduct, adultCategory, colorPhotoPreview, fullPrice, " +
                    " purchasePrice, charityProfit, barcode, vatType, vatAmount, vatPrice)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            ProductBatchPreparedStatementSetter(productFetchList)
        )
    }

    internal class ProductBatchPreparedStatementSetter(
        private val products: List<ChKazanExpressProduct>
    ) : BatchPreparedStatementSetter {

        override fun setValues(ps: PreparedStatement, i: Int) {
            val kazanExpressProduct: ChKazanExpressProduct = products[i]
            var l = 1
            ps.setObject(
                l++, Instant.ofEpochSecond(
                    kazanExpressProduct.fetchTime.seconds,
                    kazanExpressProduct.fetchTime.nanos.toLong()
                ).atZone(ZoneId.of("UTC")).toLocalDateTime()
            )
            ps.setLong(l++, Timestamps.toMillis(kazanExpressProduct.fetchTime))
            ps.setLong(l++, kazanExpressProduct.productId)
            ps.setLong(l++, kazanExpressProduct.skuId)
            ps.setString(l++, kazanExpressProduct.title)
            ps.setObject(l++, kazanExpressProduct.rating)
            ps.setArray(
                l++,
                ClickHouseArray(ClickHouseDataType.UInt64, kazanExpressProduct.categoryPaths.toTypedArray())
            )
            ps.setInt(l++, kazanExpressProduct.reviewsAmount)
            ps.setLong(l++, kazanExpressProduct.totalOrdersAmount)
            ps.setLong(l++, kazanExpressProduct.totalAvailableAmount)
            ps.setLong(l++, kazanExpressProduct.availableAmount)
            ps.setInt(l++, kazanExpressProduct.charityCommission)
            ps.setArray(l++, ClickHouseArray(ClickHouseDataType.String, kazanExpressProduct.attributes.toTypedArray()))
            ps.setArray(l++, ClickHouseArray(ClickHouseDataType.String, kazanExpressProduct.tags.toTypedArray()))
            ps.setString(l++, kazanExpressProduct.photoKey)
            ps.setObject(
                l++,
                kazanExpressProduct.characteristics.stream().collect(Collectors.toMap({ it.type }, { it.title }))
            )
            ps.setLong(l++, kazanExpressProduct.sellerId)
            ps.setLong(l++, kazanExpressProduct.sellerAccountId)
            ps.setString(l++, kazanExpressProduct.sellerTitle)
            ps.setString(l++, kazanExpressProduct.sellerLink)
            ps.setObject(l++,
                Instant.ofEpochSecond(kazanExpressProduct.sellerRegistrationDate / 1000).atZone(ZoneId.of("UTC"))
                    .toLocalDateTime()
            )
            ps.setObject(l++, kazanExpressProduct.sellerRating)
            ps.setInt(l++, kazanExpressProduct.sellerReviewsCount)
            ps.setLong(l++, kazanExpressProduct.sellerOrders)
            ps.setBoolean(l++, kazanExpressProduct.sellerOfficial)
            ps.setObject(l++, kazanExpressProduct.sellerContacts)
            ps.setBoolean(l++, kazanExpressProduct.isEco)
            ps.setBoolean(l++, kazanExpressProduct.isPerishable)
            ps.setBoolean(l++, kazanExpressProduct.hasVerticalPhoto)
            ps.setBoolean(l++, kazanExpressProduct.showKitty)
            ps.setBoolean(l++, kazanExpressProduct.bonusProduct)
            ps.setBoolean(l++, kazanExpressProduct.adultCategory)
            ps.setBoolean(l++, kazanExpressProduct.colorPhotoPreview)
            ps.setLong(l++, kazanExpressProduct.fullPrice)
            ps.setLong(l++, kazanExpressProduct.purchasePrice)
            ps.setLong(l++, kazanExpressProduct.charityProfit)
            ps.setString(l++, kazanExpressProduct.barCode)
            ps.setString(l++, kazanExpressProduct.vatType)
            ps.setLong(l++, kazanExpressProduct.vatAmount)
            ps.setLong(l++, kazanExpressProduct.vatPrice)
        }

        override fun getBatchSize(): Int {
            return products.size
        }

    }

}
