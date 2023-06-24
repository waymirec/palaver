package net.waymire.palaver.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import org.keycloak.TokenVerifier
import org.keycloak.authorization.client.AuthzClient
import org.keycloak.representations.AccessToken
import org.keycloak.representations.idm.authorization.AuthorizationRequest
import org.keycloak.representations.idm.authorization.AuthorizationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val username: String, val password: String)

data class AuthResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    @JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("not-before-policy")
    val notBeforePolicy: Long,
    @JsonProperty("session_state")
    val sessionState: String? = null,
    @JsonProperty("scope")
    val scope: String? = null
)

private fun AuthorizationResponse.toDto() = AuthResponse(
    accessToken = this.token,
    expiresIn = this.expiresIn,
    refreshExpiresIn = this.refreshExpiresIn,
    refreshToken = this.refreshToken,
    tokenType = this.tokenType,
    scope = this.scope,
    sessionState = this.sessionState,
    notBeforePolicy = this.notBeforePolicy.toLong()
)

@RestController
class AuthenticationController {
    private val logger by LoggerDelegate()

    @PostMapping("/authenticate")
    fun login(@RequestBody login: LoginRequest): AuthResponse {
        val authClient = AuthzClient.create()
        val authRequest = AuthorizationRequest()
        val authResponse = authClient.authorization(login.username, login.password).authorize(authRequest)
        val token = TokenVerifier.create(authResponse.token, AccessToken::class.java).token

        logger.info { "Realm Roles: ${token.realmAccess.roles.joinToString()}" }

        token.resourceAccess.forEach { (k, v) -> logger.info { "Client '$k' => Roles '${v.roles.joinToString()}'"}}

        val requestingPartyToken = authClient.protection().introspectRequestingPartyToken(authResponse.token)
        logger.info { "Token status is ${requestingPartyToken.active}" }
        logger.info { "Permissions: ${requestingPartyToken.permissions.joinToString()}" }

        return authResponse.toDto()
    }
}

