package pw.modder.mgt2helper.data

import com.charleskorn.kaml.*
import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    val createdAt: Long,
    val genres: List<Genre>
) {
    companion object {
        fun load(): Genres {
            this::class.java.getResourceAsStream("/data.yml").use {
                return Yaml.default.decodeFromStream(serializer(), it ?: throw RuntimeException("data.yml not found!"))
            }
        }
    }
}