package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class GenrePanel(private val loc: ResourceBundle, data: Genres): JPanel(GridBagLayout()) {
    val genre = JComboBox(data.genres.toTypedArray()).apply {
        val constraints = generateConstrains(0, 0, fill = GridBagConstraints.HORIZONTAL)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                super.getListCellRendererComponent(list, (value as? Genre)?.name ?: value, index, isSelected, cellHasFocus)
                return this
            }
        }

        toolTipText = loc.getString("tooltip.genre")

        this@GenrePanel.add(this, constraints)
    }

    val subgenre = JComboBox((genre.selectedItem as Genre).subgenres.filter { it.matches }.toTypedArray()).apply {
        val constraints = generateConstrains(1, 0, fill = GridBagConstraints.HORIZONTAL)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                super.getListCellRendererComponent(list, (value as? Subgenre)?.name ?: value, index, isSelected, cellHasFocus)
                return this
            }
        }

        toolTipText = loc.getString("tooltip.subgenre")

        this@GenrePanel.add(this, constraints)
    }

    val matchingOnly = JCheckBox().apply {
        isSelected = true
        val constraints = generateConstrains(1, 1, fill = GridBagConstraints.HORIZONTAL)

        text = loc.getString("checkbox.matching")

        this@GenrePanel.add(this, constraints)
    }
}