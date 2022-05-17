package dev.crashteam.overmind.listener

import dev.crashteam.kz.fetcher.FetchKazanExpressEvent
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class KazanExpressFetchEventListener(
    private val fetchEventHandler: List<FetchEventHandler>
) {

    @KafkaListener(
        topics = ["\${overmind.ke-topic-name}"],
        autoStartup = "true",
        containerFactory = "keListenerContainerFactory"
    )
    fun receive(
        @Payload messages: List<ByteArray>,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
        ack: Acknowledgment
    ) {
        try {
            List(messages.size) { i ->
                log.info { "Received message with partition-offset=${partitions[i].toString() + "-" + offsets[i]}" }
                FetchKazanExpressEvent.parseFrom(messages[i])
            }.groupBy { entry -> fetchEventHandler.find { it.isHandle(entry) } }
                .forEach { (handler, entries) ->
                    handler?.handle(entries)
                }
            ack.acknowledge()
        } catch (e: Exception) {
            log.error(e) { "Exception during handling KE fetch events" }
            throw e
        }
    }

}