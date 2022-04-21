package dev.crashteam.overmind.converter.thrift

import dev.crashteam.kz.fetcher.BrandFetch
import dev.crashteam.overmind.converter.DataConverter
import dev.crashteam.overmind.converter.thrift.model.KazanExpressBrandFetch
import org.springframework.stereotype.Component

@Component
class BrandFetchConverter : DataConverter<BrandFetch, KazanExpressBrandFetch> {

    override fun convert(source: BrandFetch): KazanExpressBrandFetch {
        val brandStruct = source.brand
        return KazanExpressBrandFetch(
            id = brandStruct.getFieldsOrThrow("id").stringValue.toLong(),
            name = brandStruct.getFieldsOrThrow("name").stringValue,
            image = brandStruct.getFieldsOrThrow("image").stringValue,
            description = brandStruct.getFieldsOrDefault("description", null).stringValue,
            categoryId = source.categoryId,
            productIds = source.productIdsList
        )
    }

}