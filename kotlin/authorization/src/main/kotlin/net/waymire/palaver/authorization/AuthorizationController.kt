package net.waymire.palaver.authorization

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController {
    private val logger by LoggerDelegate()

    @GetMapping("/authorizations")
    fun authorizations() {

    }
}

