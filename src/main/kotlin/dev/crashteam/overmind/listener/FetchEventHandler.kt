package dev.crashteam.overmind.listener

import dev.crashteam.kz.fetcher.FetchKazanExpressEvent

interface FetchEventHandler {

    fun handle(fetchKazanExpressEvent: List<FetchKazanExpressEvent>)

    fun isHandle(fetchKazanExpressEvent: FetchKazanExpressEvent): Boolean

}