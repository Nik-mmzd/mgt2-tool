package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class GenrePanel(private val loc: ResourceBundle, data: Genres): JPanel(GridBagLayout()) {
    val gloc: ResourceBundle = ResourceBundle.getBundle("locale.game", UTF8Control)
    val subgenres = listOf<Subgenre>(Subgenre.None) + data.genres.map { Subgenre.Genre(it) }

    val genre = JComboBox(data.genres.toTypedArray()).apply {
        val constraints = generateConstrains(0, 0, fill = GridBagConstraints.HORIZONTAL)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                val string = when(value) {
                    is Genre -> gloc.getString("genre.${value.name}")
                    else -> null
                }
                super.getListCellRendererComponent(list, string ?: value, index, isSelected, cellHasFocus)
                return this
            }
        }

        toolTipText = loc.getString("tooltip.genre")

        this@GenrePanel.add(this, constraints)
    }

    val subgenre = JComboBox<Subgenre>().apply {
        val genre = genre.selectedItem as Genre
        subgenres.filter { it is Subgenre.None || it is Subgenre.Genre && it.genre.name in genre.subgenres }.forEach(this::addItem)

        val constraints = generateConstrains(1, 0, fill = GridBagConstraints.HORIZONTAL)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                val string = when(value) {
                    is Subgenre -> gloc.getString("genre.${value.name}")
                    else -> null
                }
                super.getListCellRendererComponent(list, string ?: value, index, isSelected, cellHasFocus)
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