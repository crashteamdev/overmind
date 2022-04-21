package dev.crashteam.overmind.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "postgresql")
data class PostgresqlDbProperties(
    @field:NotEmpty
    val url: String? = null,
    @field:NotEmpty
    val user: String? = null,
    val password: String? = null,
)
