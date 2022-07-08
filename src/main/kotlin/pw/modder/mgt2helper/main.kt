package pw.modder.mgt2helper

import com.formdev.flatlaf.*
import pw.modder.mgt2helper.frames.*
import pw.modder.mgt2helper.utils.*
import javax.swing.*

fun main() {
    FlatDarculaLaf.setup()
    SwingUtilities.invokeAndWait {
        MainFrame(Config())
    }
}