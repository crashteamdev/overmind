package dev.crashteam.overmind.config

import dev.crashteam.overmind.converter.DataConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ConversionServiceFactoryBean

@Configuration
@ComponentScan(
    basePackages = [
        "dev.crashteam.overmind.converter",
    ]
)
class ConverterConfig {

    @Bean
    @Primary
    fun conversionServiceFactoryBean(
        dataConverters: Set<DataConverter<*, *>>,
    ): ConversionServiceFactoryBean {
        val conversionServiceFactoryBean = ConversionServiceFactoryBean()
        conversionServiceFactoryBean.setConverters(dataConverters)

        return conversionServiceFactoryBean
    }
}
