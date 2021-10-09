package pl.psplus.model.dto

import pl.psplus.model.exposed.Game
import pl.psplus.model.exposed.Trophy
import pl.psplus.model.exposed.Trophy.references

data class Trophy(
    val gameId: Int,
    val name: String,
    val description: String,
    val imageSrc: String,
    val type: String
)