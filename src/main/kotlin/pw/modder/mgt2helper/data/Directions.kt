package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Directions(
    val length: Byte,
    val depth: Byte,
    val friendliness: Byte,
    val innovation: Byte,
    val story: Byte,
    val characters: Byte,
    val levels: Byte,
    val missions: Byte,
    val hardcore: Byte,
    val cruelty: Byte,
    val complexity: Byte
)
