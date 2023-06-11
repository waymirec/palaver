package net.waymire.palaver.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

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
    val sessionState: String,
    @JsonProperty("scope")
    val scope: String
)

@RestController
class AuthenticationController(
    private val restTemplate: RestTemplate,
    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private val jwtIssuerUri: String
) {
    @PostMapping("/authenticate")
    fun login(@RequestBody request: LoginRequest): AuthResponse {
        return requestAuth(request) ?: throw BadCredentialsException("invalid credentials")
    }

    private fun requestAuth(request: LoginRequest): AuthResponse? {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "password")
        body.add("client_id", "login-app")
        body.add("username", request.username)
        body.add("password", request.password)

        val entity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(body, headers)

        val response = restTemplate.exchange("$jwtIssuerUri/protocol/openid-connect/token", HttpMethod.POST, entity, AuthResponse::class.java)
        return response.body
    }
}

