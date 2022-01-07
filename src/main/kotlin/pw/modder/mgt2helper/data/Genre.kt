package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val name: String,
    val targets: List<Targets>,
    val design: Design,
    val directions: Directions,
    val subgenres: List<String>,
    val topics: List<String>
) {
    operator fun plus(other: Genre): Genre {
        return Genre(
            "$name ${other.name}",
            targets.intersect(other.targets.toSet()).sorted(),
            design,
            directions + other.directions,
            subgenres.intersect(other.subgenres.toSet()).sorted(),
            topics.intersect(other.topics.toSet()).sorted()
        )
    }
}
