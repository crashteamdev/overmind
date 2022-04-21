package dev.crashteam.overmind.listener

import dev.crashteam.kz.fetcher.FetchKazanExpressEvent
import dev.crashteam.overmind.converter.thrift.model.KazanExpressProductFetch
import dev.crashteam.overmind.service.KazanExpressDataService
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component

@Component
class ProductFetchEventHandler(
    private val conversionService: ConversionService,
    private val kazanExpressDataService: KazanExpressDataService
) : FetchEventHandler {

    override fun handle(fetchKazanExpressEvent: List<FetchKazanExpressEvent>) {
        val kazanExpressProductFetches: List<KazanExpressProductFetch> = fetchKazanExpressEvent.map {
            conversionService.convert(
                it.productFetch,
                KazanExpressProductFetch::class.java
            )!!
        }
        kazanExpressDataService.saveProducts(kazanExpressProductFetches)
    }

    override fun isHandle(fetchKazanExpressEvent: FetchKazanExpressEvent): Boolean {
        return fetchKazanExpressEvent.hasProductFetch()
    }

}