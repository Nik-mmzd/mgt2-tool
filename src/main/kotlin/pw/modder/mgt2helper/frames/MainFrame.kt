package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*
import javax.swing.border.*

class MainFrame(private val data: Genres): JFrame() {
    private val loc: ResourceBundle = ResourceBundle.getBundle("locale.gui", UTF8Control)
    init {
        title = loc.getString("title")
        defaultCloseOperation = EXIT_ON_CLOSE
        val ss = Toolkit.getDefaultToolkit().screenSize
        setLocation((ss.getWidth() - 640).toInt() / 2, (ss.getHeight() - 480*2).toInt() / 2)
        setSize(640, 480*2)
        contentPane = JPanel(GridBagLayout())
        isResizable = true
        isVisible = true
    }

    private fun printData() {
        val main = genrePanel.genre.selectedItem as? Genre ?: return

        val genre = when(val sub = genrePanel.subgenre.selectedItem) {
            is Subgenre.Genre -> main + sub.genre
            is Subgenre.None -> main
            else -> return
        }

        topics.setListData(genre.topics.map { loc.getString("theme.${it}") }.toTypedArray())
        targets.setListData(genre.targets.map { loc.getString("target.${it.name.lowercase()}") }.toTypedArray())

        with(designDirection) {
            length.setValue(genre.directions.length)
            depth.setValue(genre.directions.depth)
            friendliness.setValue(genre.directions.friendliness)
            innovation.setValue(genre.directions.innovation)
            story.setValue(genre.directions.story)
            characters.setValue(genre.directions.characters)
            levels.setValue(genre.directions.levels)
            missions.setValue(genre.directions.missions)
        }

        with(projectDirection) {
            hardcore.setValue(genre.directions.hardcore)
            cruelty.setValue(genre.directions.cruelty)
            complexity.setValue(genre.directions.complexity)
        }

        with(designPriority) {
            gameplay.value = genre.design.gameplay.toInt()
            graphics.value = genre.design.graphics.toInt()
            sound.value = genre.design.sound.toInt()
            control.value = genre.design.control.toInt()
        }
    }

    val genrePanel = GenrePanel(loc, data).apply {
        genre.addActionListener {
            subgenre.removeAllItems()
            val genre = genre.selectedItem as Genre
            when(matchingOnly.isSelected) {
                true -> subgenres.filter { it is Subgenre.None || it.name in genre.subgenres }
                false -> subgenres.filter { it.name != genre.name }
            }.forEach(subgenre::addItem)

            printData()
        }

        subgenre.addActionListener {
            printData()
        }

        matchingOnly.addActionListener {
            subgenre.removeAllItems()
            val genre = genre.selectedItem as Genre
            when(matchingOnly.isSelected) {
                true -> subgenres.filter { it is Subgenre.None || it.name in genre.subgenres }
                false -> subgenres.filter { it.name != genre.name }
            }.forEach(subgenre::addItem)
        }

        border = TitledBorder(loc.getString("label.genre"))

        val constraints = generateConstrains(0, 0, 2, GridBagConstraints.HORIZONTAL).apply {
            this.anchor = GridBagConstraints.NORTH
        }

        contentPane.add(this, constraints)
    }

    private val targets = JList<String>().apply {
        val constraints = generateConstrains(0, 1)

        val pane = JScrollPane(this).apply {
            border = TitledBorder(loc.getString("label.targetGroups"))
        }

        contentPane.add(pane, constraints)
    }

    private val topics = JList<String>().apply {
        val constraints = generateConstrains(1, 1)

        val pane = JScrollPane(this).apply {
            border = TitledBorder(loc.getString("label.topics"))
        }
        contentPane.add(pane, constraints)
    }

    val designDirection = DesignDirectionPanel(loc).apply {
        border = TitledBorder(loc.getString("label.designDirection"))

        val constraints = generateConstrains(0, 2, 2)

        contentPane.add(this, constraints)
    }

    val projectDirection = ProjectDirectionPanel(loc).apply {
        border = TitledBorder(loc.getString("label.projectDirection"))

        val constraints = generateConstrains(0, 3, 2)

        contentPane.add(this, constraints)
    }

    val designPriority = DesignPriorityPanel(loc).apply {
        border = TitledBorder(loc.getString("label.designPriority"))

        val constraints = generateConstrains(0, 4, 2)

        contentPane.add(this, constraints)
    }

    init {
        printData()
        pack()
    }
}