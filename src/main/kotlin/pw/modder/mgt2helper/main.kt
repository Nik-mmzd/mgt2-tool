package pw.modder.mgt2helper

import com.formdev.flatlaf.*
import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.frames.*
import javax.swing.*

fun main() {
    FlatDarculaLaf.setup()
    val data = Genres.load()
    SwingUtilities.invokeAndWait {
        MainFrame(data)
    }
}