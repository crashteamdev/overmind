package dev.crashteam.overmind.service

import dev.crashteam.overmind.converter.ch.model.ChKazanExpressProductConverterResult
import dev.crashteam.overmind.converter.thrift.model.KazanExpressBrandFetch
import dev.crashteam.overmind.converter.thrift.model.KazanExpressProductFetch
import dev.crashteam.overmind.dao.CHBrandRepository
import dev.crashteam.overmind.dao.CHProductRepository
import dev.crashteam.overmind.dao.model.ChKazanExpressBrand
import dev.crashteam.overmind.dao.model.ChKazanExpressProduct
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import org.springframework.stereotype.Service

@Service
class KazanExpressDataService(
    private val chProductRepository: CHProductRepository,
    private val chBrandRepository: CHBrandRepository,
    private val conversionService: ConversionService
) {

    fun saveProducts(fetchs: List<KazanExpressProductFetch>) {
        val products: List<ChKazanExpressProduct> = fetchs.flatMap { product ->
            conversionService.convert(product, ChKazanExpressProductConverterResult::class.java)!!.result
        }
        chProductRepository.saveProducts(products)
    }

    fun saveBrands(fetchs: List<KazanExpressBrandFetch>) {
        val brands = fetchs.map { brand ->
            conversionService.convert(brand, ChKazanExpressBrand::class.java)!!
        }
        chBrandRepository.saveBrands(brands)
    }

}
