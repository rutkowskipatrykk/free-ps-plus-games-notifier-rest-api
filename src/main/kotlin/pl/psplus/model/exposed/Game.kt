package pl.psplus.model.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table


object Game : Table() {
    val id = integer("id")
    val developer = text("developer").nullable()
    val genre = varchar("genre", 50).nullable()
    val name = text("name")
    val platforms = text("platforms").nullable()
    val summary = text("summary").nullable()
    val timeToBeat = text("timeToBeat").nullable()
    val rating = double("rating").nullable()
    val cover = text("cover").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Game_ID")
}

fun Game.toDto(row: ResultRow) =
    pl.psplus.model.dto.Game(
        row[Game.id],
        row[Game.developer],
        row[Game.genre],
        row[Game.name],
        row[Game.platforms],
        row[Game.summary],
        row[Game.timeToBeat],
        row[Game.rating],
        row[Game.cover]
    )