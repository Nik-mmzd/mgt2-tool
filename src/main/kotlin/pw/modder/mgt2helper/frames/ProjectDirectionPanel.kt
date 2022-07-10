package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class ProjectDirectionPanel(private val loc: ResourceBundle): JPanel(GridBagLayout()) {
    private fun createProgressBar(max: Int = 10, y: Int): JProgressBar {
        return ProgressBar().apply {
            maximum = max
            minimum = 0
            isStringPainted = true
            setDefaultSize()

            val constraints = generateConstrains(1, y)

            this@ProjectDirectionPanel.add(this, constraints)
        }
    }

    private fun createLabel(name: String, y: Int, x: Int = 0) {
        JLabel(loc.getString("label.$name")).apply {
            val constraints = generateConstrains(x, y)

            setDefaultSize()
            this@ProjectDirectionPanel.add(this, constraints)
        }
    }

    init {
        listOf("hardcore", "cruelty", "complexity").forEachIndexed { index, name ->
            createLabel(name, index)
        }
    }

    val hardcore = createProgressBar(y = 0)
    val cruelty = createProgressBar(y = 1)
    val complexity = createProgressBar(y = 2)
}
