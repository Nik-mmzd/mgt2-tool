package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class DesignPriorityPanel(private val loc: ResourceBundle): JPanel(GridBagLayout()) {
    private fun createProgressBar(max: Int = 50, y: Int): JProgressBar {
        return ProgressBar().apply {
            maximum = max
            minimum = 0
            isStringPainted = true
            setDefaultSize()

            val constraints = generateConstrains(1, y)

            this@DesignPriorityPanel.add(this, constraints)
        }
    }
    private fun createLabel(name: String, y: Int, x: Int = 0) {
        JLabel(loc.getString("label.$name")).apply {
            val constraints = generateConstrains(x, y)

            setDefaultSize()
            this@DesignPriorityPanel.add(this, constraints)
        }
    }

    init {
        listOf("gameplay", "graphics", "sound", "control").forEachIndexed { index, name ->
            createLabel(name, index)
        }
    }

    val gameplay = createProgressBar(y = 0)
    val graphics = createProgressBar(y = 1)
    val sound = createProgressBar(y = 2)
    val control = createProgressBar(y = 3)
}