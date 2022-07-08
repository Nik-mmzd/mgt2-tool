package pw.modder.mgt2helper.utils

import java.nio.file.*
import java.util.Properties
import kotlin.io.path.*

class Config() {
    private val path = getConfigPath("mgt2-tool")
        .also { Files.createDirectories(it) }
        .resolve("config.properties")

    private val config = Properties().also {
        path.reader(Charsets.UTF_8).use { reader ->
            it.load(reader)
        }
    }

    var gamePath: Path?
        get() {
            return Paths.get(config.getProperty("game.path") ?: return null)
        }
        set(value) {
            if (value != null)
                config.setProperty(config.getProperty("game.path"), value.toAbsolutePath().normalize().toString())
        }

    var lang: String
        get() = config.getProperty("language", "EN")
        set(value) {
            config.setProperty("language", value)
        }

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