package net.waymire.palaver.authentication.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "jwt.auth.converter")
data class JwtAuthConverterProperties(var resourceId: String, var principalAttribute: String)

@Component
class JwtAuthConverter(
    private val properties: JwtAuthConverterProperties
) : Converter<Jwt, AbstractAuthenticationToken> {
    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    override fun convert(source: Jwt): AbstractAuthenticationToken? {
        val grantedAuthorities = jwtGrantedAuthoritiesConverter.convert(source) ?: setOf()
        val roles = extractResourceRoles(source)
        return JwtAuthenticationToken(source, grantedAuthorities.plus(roles), getPrincipalClaimName(source))
    }

    private fun getPrincipalClaimName(jwt: Jwt) = jwt.getClaimAsString(properties.principalAttribute)

    @Suppress("UNCHECKED_CAST")
    private fun extractResourceRoles(jwt: Jwt) : Set<GrantedAuthority> {
        val resourceAccess: Map<String, Any> = jwt.getClaim("resource_access") ?: return setOf()
        val resource = resourceAccess[properties.resourceId] as Map<String, Any>? ?: return setOf()
        val roles = resource["roles"] as List<String>? ?: return setOf()
        return roles.map { SimpleGrantedAuthority("ROLE_${it.uppercase()}") }.toSet()

    }
}
