package dev.crashteam.overmind.listener

import dev.crashteam.kz.fetcher.FetchKazanExpressEvent
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component

@Component
class CategoryFetchEventHandler(
    private val conversionService: ConversionService,
) : FetchEventHandler {

    override fun handle(fetchKazanExpressEvent: List<FetchKazanExpressEvent>) {
        fetchKazanExpressEvent.map {
            //conversionService.convert(it.categoryFetch, )
        }
    }

    override fun isHandle(fetchKazanExpressEvent: FetchKazanExpressEvent): Boolean {
        return fetchKazanExpressEvent.hasCategoryFetch()
    }
}
