package net.waymire.palaver.authorization.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableConfigurationProperties(JwtAuthConverterProperties::class)
@EnableJpaRepositories(basePackages = ["net.waymire.palaver.authorization"])
class ApplicationConfiguration {
}
