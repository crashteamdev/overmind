package dev.crashteam.overmind.extensions

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.ClickHouseContainer

class ClickhouseContainerExtension : BeforeAllCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext) {
        clickhouse.start()
    }

    override fun afterAll(context: ExtensionContext) {
        clickhouse.stop()
    }

    companion object {
        val clickhouse: ClickHouseContainer by lazy {
            ClickHouseContainer("yandex/clickhouse-server:latest")
        }
    }
}