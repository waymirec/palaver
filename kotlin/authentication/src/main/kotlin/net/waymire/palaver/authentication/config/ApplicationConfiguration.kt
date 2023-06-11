package net.waymire.palaver.authentication.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
@EnableConfigurationProperties(JwtAuthConverterProperties::class)
class ApplicationConfiguration {
    @Bean
    fun restTemplate() = RestTemplate()

    /*
    @Bean
    fun standardClientHttpRequestFactory() = HttpComponentsClientHttpRequestFactory().apply {
        val timeout = 5000
        setConnectTimeout(timeout)
    }
     */
}