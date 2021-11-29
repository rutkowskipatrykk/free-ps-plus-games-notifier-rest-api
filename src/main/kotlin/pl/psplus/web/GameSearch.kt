package pl.psplus.web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.psplus.service.GameService

fun Route.gameSearch(gameService: GameService) {
    route("/search") {
        get("/{searched_game}") {
            val searchedGame = call.parameters["searched_game"]
            searchedGame?.let {
                val gameList = gameService.getGameByName(it)
                call.respond(gameList)
            }
        }
    }
}