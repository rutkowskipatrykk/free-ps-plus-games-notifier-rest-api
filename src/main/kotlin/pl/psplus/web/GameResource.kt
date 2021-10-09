package pl.psplus.web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.psplus.service.GameService

fun Route.game(gameService: GameService) {

    route("/game") {
        get("/{id}") {
            val gameId = call.parameters["id"]?.toInt() ?: -1
            val gameDetails = gameService.getGamesDetails(gameId)
            call.respond(gameDetails)
        }
        get("/{id}/trophies") {
            val gameId = call.parameters["id"]?.toInt()
            gameId?.let {
                val trophies = gameService.getGameTrophies(gameId)
                call.respond(trophies)
            }
        }
    }

}