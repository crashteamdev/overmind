package dev.crashteam.overmind.listener

import dev.crashteam.kz.fetcher.FetchKazanExpressEvent
import dev.crashteam.overmind.converter.thrift.model.KazanExpressBrandFetch
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component

@Component
class BrandFetchEventHandler(
    private val conversionService: ConversionService
) : FetchEventHandler {

    override fun handle(fetchKazanExpressEvent: List<FetchKazanExpressEvent>) {
        val kazanExpressBrandFetches = fetchKazanExpressEvent.map {
            conversionService.convert(it.brandFetch, KazanExpressBrandFetch::class.java)
        }
        // TODO: batch save to clickhouse
    }

    override fun isHandle(fetchKazanExpressEvent: FetchKazanExpressEvent): Boolean {
        return fetchKazanExpressEvent.hasBrandFetch()
    }

}