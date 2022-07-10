package pw.modder.mgt2helper.utils

import java.awt.*
import javax.swing.*

private const val HEIGHT = 24
private const val WIDTH = 40

fun JComponent.setDefaultSize() {
    minimumSize = Dimension(WIDTH, HEIGHT)
    preferredSize = Dimension(WIDTH * 5, HEIGHT)
}