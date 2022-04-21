package dev.crashteam.overmind

import dev.crashteam.overmind.dao.CHProductRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductRepositoryTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var chProductRepository: CHProductRepository

    @Test
    fun `test save products`() {
        //chProductRepository.saveProducts(listOf("431431"))
    }

}