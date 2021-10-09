package pl.psplus

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import pl.psplus.model.exposed.GameAvailability
import pl.psplus.model.exposed.Trophy
import pl.psplus.model.exposed.Game
import pl.psplus.plugins.configureRouting
import java.util.*

fun initDB() {
    val config = HikariConfig("./hikari.properties")
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
    transaction {
        SchemaUtils.create(Game, GameAvailability, Trophy)
    }
}

fun main() {
    initDB()
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            gson()
        }
        configureRouting()
    }.start(wait = true)
}
