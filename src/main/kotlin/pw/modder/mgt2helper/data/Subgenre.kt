package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Subgenre(
    val name: String,
    val directions: Directions,
    val matches: Boolean = false
)
