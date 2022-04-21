package dev.crashteam.overmind

import dev.crashteam.overmind.extensions.ClickhouseContainerExtension
import dev.crashteam.overmind.extensions.KafkaContainerExtension
import dev.crashteam.overmind.extensions.PostgresqlContainerExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration

@ExtendWith(ClickhouseContainerExtension::class, KafkaContainerExtension::class, PostgresqlContainerExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
    classes = [OvermindApplication::class],
    initializers = [AbstractIntegrationTest.Initializer::class]
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class AbstractIntegrationTest {

    object Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.kafka.bootstrap-servers=${KafkaContainerExtension.kafka.bootstrapServers}",
                "clickhouse.url=${ClickhouseContainerExtension.clickhouse.jdbcUrl}",
                "clickhouse.user=${ClickhouseContainerExtension.clickhouse.username}",
                "clickhouse.password=${ClickhouseContainerExtension.clickhouse.password}",
                "postgresql.url=${PostgresqlContainerExtension.postgresql.jdbcUrl}",
                "postgresql.user=${PostgresqlContainerExtension.postgresql.username}",
                "postgresql.password=${PostgresqlContainerExtension.postgresql.password}"
            ).applyTo(applicationContext.environment)
        }
    }

}