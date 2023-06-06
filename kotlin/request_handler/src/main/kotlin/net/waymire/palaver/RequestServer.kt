package net.waymire.palaver

import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.ServerWebSocket
import org.reactivestreams.Publisher

val users = mutableMapOf<String, WebSocketSession>()

@ServerWebSocket("/ws/request")
@Secured(SecurityRule.IS_AUTHENTICATED)
class RequestWebSocket {
    fun onOpen(session: WebSocketSession): Publisher<String> {
       //
    }
}