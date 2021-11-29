package pl.psplus.service

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import pl.psplus.model.dto.GameListItem
import pl.psplus.model.exposed.*

class GameService {

    suspend fun getGamesDetails(gameId: Int) = newSuspendedTransaction {
        Game.select {
            Game.id eq gameId
        }.map {
            Game.toDto(it)
        }.first()
    }

    suspend fun getGamesByDate(date: Long) = newSuspendedTransaction {
        (GameAvailability innerJoin Game).slice(Game.id, Game.name, Game.platforms, Game.rating, Game.cover)
            .select {
                (GameAvailability.startDate lessEq date) and (GameAvailability.endDate greaterEq date)
            }.map {
                GameListItem(it[Game.id], it[Game.name], it[Game.platforms], it[Game.rating], it[Game.cover])
            }
    }

    suspend fun getGameTrophies(gameId: Int) = newSuspendedTransaction {
        Trophy.select {
            Trophy.gameId eq gameId
        }.map {
            Trophy.toDTO(it)
        }
    }

    suspend fun getGameByName(gameName: String) = newSuspendedTransaction {
        (GameAvailability innerJoin Game).slice(
            Game.id,
            Game.name,
            Game.platforms,
            Game.rating,
            Game.cover,
            GameAvailability.startDate,
            GameAvailability.endDate
        ).select {
            Game.name like "%$gameName%"
        }.orderBy(GameAvailability.startDate).map {
            GameListItem(
                it[Game.id],
                it[Game.name],
                it[Game.platforms],
                it[Game.rating],
                it[Game.cover],
                it[GameAvailability.startDate],
                it[GameAvailability.endDate]
            )
        }
    }

}