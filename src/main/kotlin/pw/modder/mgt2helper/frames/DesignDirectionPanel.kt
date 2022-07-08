package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class DesignDirectionPanel(private val loc: ResourceBundle): JPanel(GridBagLayout()) {
    fun JProgressBar.setValue(new: Byte) {
        value = new.toInt()
        string = new.toString()
    }

    private fun createProgressBar(max: Int = 10, y: Int): JProgressBar {
        return JProgressBar().apply {
            maximum = max
            minimum = 0
            isStringPainted = true

            val constraints = generateConstrains(1, y)

            this@DesignDirectionPanel.add(this, constraints)
        }
    }
    private fun createLabel(name: String, y: Int, x: Int = 0) {
        JLabel(loc.getString("label.$name")).apply {
            val constraints = generateConstrains(x, y)

            this@DesignDirectionPanel.add(this, constraints)
        }
    }

    init {
        listOf("length", "depth", "friendliness", "innovation", "story", "characters", "levels", "missions").forEachIndexed { index, name ->
            createLabel(name, index)
        }
    }

    val length = createProgressBar(y = 0)
    val depth = createProgressBar(y = 1)
    val friendliness = createProgressBar(y = 2)
    val innovation = createProgressBar(y = 3)
    val story = createProgressBar(y = 4)
    val characters = createProgressBar(y = 5)
    val levels = createProgressBar(y = 6)
    val missions = createProgressBar(y = 7)
}