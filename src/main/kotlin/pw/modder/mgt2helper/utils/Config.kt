package pw.modder.mgt2helper.utils

import java.nio.file.*
import java.util.Properties
import kotlin.io.path.*

class Config() {
    private val path = getConfigPath("mgt2-tool")
        .also { Files.createDirectories(it) }
        .resolve("config.properties")

    private val config = Properties().also {
        if (Files.isRegularFile(path)) {
            path.reader(Charsets.UTF_8).use { reader ->
                it.load(reader)
            }
        }
    }

    var gamePath: Path?
        get() {
            return Paths.get(config.getProperty("game.path") ?: return null)
        }
        set(value) {
            if (value != null)
                config.setProperty("game.path", value.toAbsolutePath().normalize().toString())
        }

    var lang: String
        get() = config.getProperty("language", "EN")
        set(value) {
            config.setProperty("language", value)
        }

    var selectedGenre: Int
        get() = config.getProperty("selected.genre", "0").toInt()
        set(value) { config.setProperty("selected.genre", value.toString()) }
    var selectedSubgenre: Int
        get() = config.getProperty("selected.subgenre", "0").toInt()
        set(value) { config.setProperty("selected.subgenre", value.toString()) }
    var matchingOnly: Boolean
        get() = config.getProperty("matching", "true").toBoolean()
        set(value) { config.setProperty("matching", value.toString()) }

    var lastSave: Path?
        get() = config.getProperty("save.path", null)
            ?.takeIf { it.isNotEmpty() && it != "null" }
            ?.let { Paths.get(it) }
            ?.takeIf { it.exists() }
        set(value) { config.setProperty("save.path", value?.toAbsolutePath()?.normalize().toString()) }

    fun save(comment: String = "Mad Games Tycoon 2 Helper Tool config") {
        path.writer(Charsets.UTF_8).use {
            config.store(it, comment)
        }
    }

    private companion object {
        private fun getConfigPath(name: String): Path {
            val os = System.getProperty("os.name", "unknown").lowercase()
            return when {
                os.startsWith("win") -> Paths.get(System.getenv("APPDATA"))
                os.contains("linux") -> getLinuxConfigPath()
                os.contains("osx") || os.startsWith("mac")  -> Paths.get(System.getProperty("user.home"), "Library", "Application Support")
                else -> Paths.get(System.getProperty("user.home"), ".")
            }.resolve(name)
        }

        private fun getLinuxConfigPath(): Path {
            val xdg = System.getenv("XDG_DATA_HOME")
            if (xdg != null)
                return Paths.get(xdg)
            val home = System.getProperty("user.home")
            if (home != null)
                return Paths.get(home, ".local", "share")

            return Paths.get(".")
        }
    }
}