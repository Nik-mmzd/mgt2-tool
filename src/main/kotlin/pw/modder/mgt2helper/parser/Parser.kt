package pw.modder.mgt2helper.parser

import java.nio.file.Path
import java.util.EnumSet
import kotlin.io.path.*

class Parser(game: Path) {
    private val gamePath = game.resolve(GAME_DATA_PATH)

    private fun String.parseIntArray(): Set<Int> {
        return REGEX_INT_ARRAY.findAll(this).map { it.value.toInt() }.toSet()
    }

    private fun String.parseTargetsArray(): Set<Genre.Targets> {
        return EnumSet.copyOf(REGEX_STRING_ARRAY.findAll(this).map { Genre.Targets.valueOf(it.value) }.toSet())
    }

    private fun Map<String, String>.getIntValue(key: String): Int = getValue(key).toInt()

    private fun parseGenre(strings: List<String>): Genre {
        val stringMap = strings.associate {
            val separatorChar = it.indexOf(']')
            it.substring(1, separatorChar - 1) to it.substring(separatorChar + 1, it.length)
        }

        return with(stringMap) {
            Genre(
                id = getIntValue("ID"),
                subjenres = getValue("GENRE COMB").parseIntArray(),
                properties = Genre.Properties(
                    targets = getValue("TGROUP").parseTargetsArray(),
                    gameplay = getIntValue("GAMEPLAY"),
                    graphics = getIntValue("GRAPHIC"),
                    sound = getIntValue("SOUND"),
                    control = getIntValue("CONTROL"),
                    length = getIntValue("FOCUS0"),
                    depth = getIntValue("FOCUS1"),
                    friendliness = getIntValue("FOCUS2"),
                    innovation = getIntValue("FOCUS3"),
                    story = getIntValue("FOCUS4"),
                    characters = getIntValue("FOCUS5"),
                    levels = getIntValue("FOCUS6"),
                    missions = getIntValue("FOCUS7"),
                    hardcore = getIntValue("ALIGN0"),
                    cruelty = getIntValue("ALIGN1"),
                    complexity = getIntValue("ALIGN2")
                ),
                names = filterKeys { it.startsWith("NAME") }
                    .map { (key, value) ->
                        key.split(' ').last() to value
                    }.toMap()
            )
        }
    }

    fun loadGenres(): List<Genre> {
        return buildList {
            val temp = mutableListOf<String>()

            gamePath.resolve("DATA").resolve("Genres.txt")
                .readLines(Charsets.UTF_8).forEach {
                    if (it.isEmpty()) {
                        if (temp.isNotEmpty()) {
                            add(parseGenre(temp))
                            temp.clear()
                        }
                    } else {
                        temp.add(it)
                    }
                }

            if (temp.isNotEmpty()) {
                add(parseGenre(temp))
                temp.clear()
            }
        }
    }

    fun loadThemes(lang: String = "EN"): List<Theme> {
        val matches = loadThemeMatches()

        return gamePath.resolve(lang).resolve("Themes_${lang}.txt")
            .readLines(Charsets.UTF_16LE)
            .mapIndexed { index, theme ->
                val pos = theme.indexOf('<')
                val name = if (pos == -1) {
                    theme
                } else {
                    theme.substring(0, pos - 1)
                }

                Theme(index, name, matches.getValue(index))
            }
    }

    private fun loadThemeMatches(): Map<Int, Set<Int>> {
        return gamePath.resolve(DATA_LANG).resolve("Themes_${DATA_LANG}.txt") // resolve file
            .readLines(Charsets.UTF_16LE)                                     // read all lines
            .mapIndexed { index, theme ->
                index to REGEX_INT_ARRAY.findAll(theme).map { it.value.toInt() }.toSet()
            }.toMap()
    }

    companion object {
        private const val GAME_DATA_PATH = "Mad Games Tycoon 2_Data/Extern/Text"
        private const val DATA_LANG = "GE"
        private val REGEX_INT_ARRAY = "<(\\d+)>".toRegex()
        private val REGEX_STRING_ARRAY = "<(\\w+)>".toRegex()
    }
}