package dev.crashteam.overmind.config

import dev.crashteam.overmind.config.properties.ClickHouseDbProperties
import dev.crashteam.overmind.config.properties.PostgresqlDbProperties
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import ru.yandex.clickhouse.ClickHouseDataSource
import ru.yandex.clickhouse.settings.ClickHouseQueryParam
import java.util.*
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    fun clickHouseDataSource(clickHouseDbProperties: ClickHouseDbProperties): ClickHouseDataSource {
        val info = Properties()
        info.setProperty(ClickHouseQueryParam.USER.key, clickHouseDbProperties.user)
        info.setProperty(ClickHouseQueryParam.PASSWORD.key, clickHouseDbProperties.password)
        info.setProperty(ClickHouseQueryParam.COMPRESS.key, clickHouseDbProperties.compress.toString())
        info.setProperty(
            ClickHouseQueryParam.CONNECT_TIMEOUT.key,
            clickHouseDbProperties.connectionTimeout.toString()
        )
        return ClickHouseDataSource(clickHouseDbProperties.url, info)
    }

    @Bean
    @Autowired
    fun clickHouseJdbcTemplate(clickHouseDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(clickHouseDataSource)
    }

    @Bean
    fun postgresqlDataSource(postgresqlDbProperties: PostgresqlDbProperties): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("org.postgresql.Driver")
        dataSourceBuilder.url(postgresqlDbProperties.url)
        dataSourceBuilder.username(postgresqlDbProperties.user)
        dataSourceBuilder.password(postgresqlDbProperties.password)
        return dataSourceBuilder.build()
    }

    @Bean
    fun postgresqlJdbcTemplate(postgresqlDataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(postgresqlDataSource)
    }

    @Bean
    @ConfigurationProperties(prefix = "clickhouse.liquibase")
    fun clickhouseLiquibaseProperties(): LiquibaseProperties {
        return LiquibaseProperties()
    }

    @Bean
    fun clickhouseLiquibase(
        clickHouseDataSource: ClickHouseDataSource,
        clickhouseLiquibaseProperties: LiquibaseProperties
    ): SpringLiquibase {
        return springLiquibase(clickHouseDataSource, clickhouseLiquibaseProperties)
    }

    @Bean
    @ConfigurationProperties(prefix = "postgresql.liquibase")
    fun postgresqlLiquibaseProperties(): LiquibaseProperties {
        return LiquibaseProperties()
    }

    @Bean
    fun secondaryLiquibase(
        postgresqlDataSource: DataSource,
        postgresqlLiquibaseProperties: LiquibaseProperties
    ): SpringLiquibase {
        return springLiquibase(postgresqlDataSource, postgresqlLiquibaseProperties)
    }

    private fun springLiquibase(dataSource: DataSource, properties: LiquibaseProperties): SpringLiquibase {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = dataSource
        liquibase.changeLog = properties.changeLog
        liquibase.contexts = properties.contexts
        liquibase.defaultSchema = properties.defaultSchema
        liquibase.isDropFirst = properties.isDropFirst
        liquibase.setShouldRun(properties.isEnabled)
        liquibase.labels = properties.labels
        liquibase.setChangeLogParameters(properties.parameters)
        liquibase.setRollbackFile(properties.rollbackFile)
        return liquibase
    }

}