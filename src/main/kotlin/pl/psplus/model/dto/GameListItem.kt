package pl.psplus.model.dto

data class GameListItem(
    val id: Int,
    val name: String,
    val platforms: String?,
    val rating: Double?,
    val cover: String?
)