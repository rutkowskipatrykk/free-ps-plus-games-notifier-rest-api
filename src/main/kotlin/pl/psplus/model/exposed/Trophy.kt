package pl.psplus.model.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Trophy : Table() {
    val gameId = integer("gameId").references(Game.id)
    val name = text("name")
    val description = text("description")
    val imageSrc = text("imageSrc")
    val type = varchar("type", 255)
}

fun Trophy.toDTO(row: ResultRow) =
    pl.psplus.model.dto.Trophy(
        row[Trophy.gameId],
        row[Trophy.name],
        row[Trophy.description],
        row[Trophy.imageSrc],
        row[Trophy.type]
    )