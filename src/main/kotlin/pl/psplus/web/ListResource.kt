package pl.psplus.web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import pl.psplus.service.GameService

fun Route.gameList(gameService: GameService) {

    route("/game-list") {
        get("/{date}") {
            val date = call.parameters["date"]?.toLong()
            date?.let {
                var gameList = gameService.getGamesByDate(date)
                call.respond(gameList)
            }

        }
    }
}
