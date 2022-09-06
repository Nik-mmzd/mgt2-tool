package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.parser.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*

class GenrePanel(private val loc: ResourceBundle, var lang: String, genres: List<Genre>): JPanel(GridBagLayout()) {
    var subgenres = listOf<Genre?>(null) + genres
        private set

    fun updateGenres(newGenres: List<Genre>) {
        subgenres = listOf<Genre?>(null) + newGenres
        val selected = genre.selectedIndex
        genre.removeAllItems()
        newGenres.forEach(genre::addItem)
        genre.selectedIndex = selected
        val selectedGenre = genre.selectedItem as Genre
        subgenre.removeAllItems()
        when(matchingOnly.isSelected) {
            true -> subgenres.filter { it == null || it.id in selectedGenre.subgenres }
            false -> subgenres.filter { it?.id != selectedGenre.id }
        }.forEach(subgenre::addItem)
    }

    val genre = JComboBox(genres.toTypedArray()).apply {
        val constraints = generateConstrains(0, 0)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                super.getListCellRendererComponent(list, (value as? Genre)?.names?.get(lang) ?: value, index, isSelected, cellHasFocus)
                return this
            }
        }

        toolTipText = loc.getString("tooltip.genre")
        setDefaultSize()

        this@GenrePanel.add(this, constraints)
    }

    val subgenre = JComboBox<Genre?>().apply {
        val genre = genre.selectedItem as Genre
        subgenres.filter { it == null || it.id in genre.subgenres }.forEach(this::addItem)

        val constraints = generateConstrains(1, 0)

        renderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(list: JList<*>, value: Any?, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
                val string = when(value) {
                    is Genre -> value.names[lang]
                    else -> loc.getString("genre.null")
                }
                super.getListCellRendererComponent(list, string ?: value, index, isSelected, cellHasFocus)
                return this
            }
        }

        toolTipText = loc.getString("tooltip.subgenre")
        setDefaultSize()

        this@GenrePanel.add(this, constraints)
    }

    val matchingOnly = JCheckBox().apply {
        isSelected = true
        val constraints = generateConstrains(1, 1)

        text = loc.getString("checkbox.matching")
        setDefaultSize()

        this@GenrePanel.add(this, constraints)
    }
}