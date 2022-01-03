package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val name: String,
    val targets: List<Targets>,
    val design: Design,
    val subgenres: List<Subgenre>,
    val topics: List<String>
)
