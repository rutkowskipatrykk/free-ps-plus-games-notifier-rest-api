package pl.psplus.plugins

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import pl.psplus.model.dto.GameListItem
import pl.psplus.model.exposed.*

fun Application.configureRouting() {
    // Starting point for a Ktor app:
    routing {
        get("/game-details/{id}") {
            val gameId = call.parameters["id"]?.toInt() ?: -1
            val gameDetails = newSuspendedTransaction {
                Game.select {
                    Game.id eq gameId
                }.map {
                    Game.toDto(it)
                }.first()
            }
            call.respond(gameDetails)
        }
        get("/trophies/{game_id}") {
            val gameId = call.parameters["game_id"]?.toInt()
            gameId?.let {
                val trophies = newSuspendedTransaction {
                    Trophy.select {
                        Trophy.gameId eq gameId
                    }.map {
                        Trophy.toDTO(it)
                    }
                }
                call.respond(trophies)
            }
        }
        get("/game-list-by-date/{date}") {
            var gameList = mutableSetOf<GameListItem>()
            val date = call.parameters["date"]?.toLong()
            date?.let {
                newSuspendedTransaction {
                    (GameAvailability innerJoin Game).slice(Game.name).select {
                        (GameAvailability.startDate lessEq date) and (GameAvailability.endDate greaterEq date)
                    }.forEach {
                        gameList.add(GameListItem(1, it[Game.name]))
                    }
                }
                call.respond(gameList)
            }

        }
    }
}
