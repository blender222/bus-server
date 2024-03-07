package com.ashtar

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    val clientId = environment.config.property("tdx.clientId").getString()
    val clientSecret = environment.config.property("tdx.clientSecret").getString()
    routing {
        get("/token") {
            val client = HttpClient(CIO)
            try {
                val response = client.submitForm(
                    url = "https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token",
                    formParameters = parameters {
                        append("grant_type", "client_credentials")
                        append("client_id", clientId)
                        append("client_secret", clientSecret)
                    }
                )
                call.respondText(response.body<String>(), ContentType.Application.Json)
            } catch (e: Exception) {
                println(e)
            } finally {
                client.close()
            }
        }
    }
}
