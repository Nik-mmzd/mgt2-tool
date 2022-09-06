package pw.modder.mgt2helper.parser.savegame

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import pw.modder.mgt2helper.parser.*
import java.io.InputStream
import java.nio.file.*
import kotlin.io.path.*

@Serializable
data class SaveGame(
    @SerialName("genres_TARGETGROUP") val genreTargets: Object<List<Boolean>>,
    @SerialName("genres_GAMEPLAY") val genreGameplay: Object<Int>,
    @SerialName("genres_GRAPHIC") val genreGraphics: Object<Int>,
    @SerialName("genres_SOUND") val genreSound: Object<Int>,
    @SerialName("genres_CONTROL") val genreControl: Object<Int>,
    @SerialName("genres_COMBINATION") val genreCombination: Object<List<Boolean>>,
    @SerialName("genres_FOCUS") val genreFocus: Object<List<Int>>,
    @SerialName("genres_ALIGN") val genreAlign: Object<List<Int>>,
) {
    @Serializable
    data class Object<T>(
        @SerialName("__type") val type: String,
        @SerialName("value") val values: List<T>
    ) {
        operator fun get(id: Int) = values[id]
        val size: Int
            get() = values.size
    }

    private fun createProperties(id: Int): Genre.Properties {
        return Genre.Properties(
            targets = Genre.Targets.values().filter { genreTargets[id][it.ordinal] }.toSet(),
            gameplay = genreGameplay[id],
            graphics = genreGraphics[id],
            sound = genreSound[id],
            control = genreControl[id],
            length = genreFocus[id][0],
            depth = genreFocus[id][1],
            friendliness = genreFocus[id][2],
            innovation = genreFocus[id][3],
            story = genreFocus[id][4],
            characters = genreFocus[id][5],
            levels = genreFocus[id][6],
            missions = genreFocus[id][7],
            hardcore = genreAlign[id][0],
            cruelty = genreAlign[id][1],
            complexity = genreAlign[id][2]
        )
    }

    fun updateFromSave(genres: List<Genre>): List<Genre> {
        return genres.map { orig ->
            orig.copy(
                properties = createProperties(orig.id),
                subgenres = genreCombination[orig.id].mapIndexedNotNull { index, b -> if (b) index else null }.toSet()
            )
        }
    }

    companion object {
        private val JSON = Json { ignoreUnknownKeys = true }

        @OptIn(ExperimentalSerializationApi::class)
        fun loadFrom(inputStream: InputStream): SaveGame {
            return JSON.decodeFromStream(serializer(), inputStream)
        }

        fun loadFrom(path: Path): SaveGame {
            return loadFrom(path.inputStream())
        }
    }
}
