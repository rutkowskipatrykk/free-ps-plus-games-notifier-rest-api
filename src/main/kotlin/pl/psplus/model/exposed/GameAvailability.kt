package pl.psplus.model.exposed

import org.jetbrains.exposed.sql.Table

object GameAvailability: Table() {
    val gameId = integer("gameId").references(Game.id)
    val region = text("region")
    val startDate = long("startDate")
    val endDate = long("endDate")
}