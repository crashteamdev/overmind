package dev.crashteam.overmind.dao

import dev.crashteam.overmind.dao.model.ChKazanExpressBrand
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.yandex.clickhouse.ClickHouseArray
import ru.yandex.clickhouse.domain.ClickHouseDataType
import java.sql.PreparedStatement
import java.time.Instant
import java.time.ZoneId

@Repository
class CHBrandRepository(
    @Qualifier("clickHouseJdbcTemplate") private val jdbcTemplate: JdbcTemplate
) {

    fun saveBrands(brandFetchList: List<ChKazanExpressBrand>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO overmind.ke_brand " +
                    "(timestamp, brandId, name, description, image, categoryId, productIds)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)",
            BrandBatchPreparedStatementSetter(brandFetchList)
        )
    }

    internal class BrandBatchPreparedStatementSetter(
        private val brands: List<ChKazanExpressBrand>
    ) : BatchPreparedStatementSetter {

        override fun setValues(ps: PreparedStatement, i: Int) {
            val kazanExpressBrand: ChKazanExpressBrand = brands[i]
            var l = 1
            ps.setObject(
                l++, Instant.ofEpochSecond(
                    kazanExpressBrand.fetchTime.seconds,
                    kazanExpressBrand.fetchTime.nanos.toLong()
                ).atZone(ZoneId.of("UTC")).toLocalDateTime()
            )
            ps.setLong(l++, kazanExpressBrand.brandId)
            ps.setString(l++, kazanExpressBrand.name)
            ps.setString(l++, kazanExpressBrand.description)
            ps.setString(l++, kazanExpressBrand.image)
            ps.setLong(l++, kazanExpressBrand.categoryId)
            ps.setArray(l++, ClickHouseArray(ClickHouseDataType.UInt64, kazanExpressBrand.productIds))
        }

        override fun getBatchSize(): Int {
            return brands.size
        }

    }

}
