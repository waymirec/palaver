package net.waymire.palaver.authorization.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.waymire.palaver.authorization.LoggerDelegate
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class KeycloakLogoutHandler(val restTemplate: RestTemplate) : LogoutHandler {
    private val logger by LoggerDelegate()

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        authentication?.let { logoutFromKeycloak(it as OidcUser) }
    }
    
    private fun logoutFromKeycloak(user: OidcUser) {
        val endSessionEndpoint = "${user.issuer}/protocol/openid-connect/logout"
        val uriBuilder = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.idToken.tokenValue)

        val logoutResponse = restTemplate.getForEntity(uriBuilder.toUriString(), String::class.java)
        if (logoutResponse.statusCode.is2xxSuccessful)
            logger.info { "Successfully logged out." }
        else
            logger.info { "Unable to propagate logout to Keycloak." }
    }
}
