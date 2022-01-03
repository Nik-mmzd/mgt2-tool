package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Design(
    val gameplay: Byte,
    val graphics: Byte,
    val sound: Byte,
    val control: Byte
)
