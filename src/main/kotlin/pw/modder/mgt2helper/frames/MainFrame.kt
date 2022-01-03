package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.util.*
import javax.swing.*
import javax.swing.border.*

// 0 genre, subjecnre
// 1 target groups
// 2 design direction
// 3 project direction
// 4 design priority
// 5 topics

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
        val sub = genrePanel.subgenre.selectedItem as? Subgenre ?: return
        val subData = data.genres.firstOrNull { it.name == sub.name }

        topics.setListData(
            when(subData) {
                null -> main.topics
                else -> subData.topics.intersect(main.topics.toSet())
            }.sorted().toTypedArray()
        )

        targets.setListData(
            when(subData) {
                null -> main.targets
                else -> main.targets.filter { it in subData.targets }
            }.map { it.name }.toTypedArray()
        )

        with(designDirection) {
            length.setValue(sub.directions.length)
            depth.setValue(sub.directions.depth)
            friendliness.setValue(sub.directions.friendliness)
            innovation.setValue(sub.directions.innovation)
            story.setValue(sub.directions.story)
            characters.setValue(sub.directions.characters)
            levels.setValue(sub.directions.levels)
            missions.setValue(sub.directions.missions)
        }

        with(projectDirection) {
            hardcore.setValue(sub.directions.hardcore)
            cruelty.setValue(sub.directions.cruelty)
            complexity.setValue(sub.directions.complexity)
        }

        with(designPriority) {
            gameplay.value = main.design.gameplay.toInt()
            graphics.value = main.design.graphics.toInt()
            sound.value = main.design.sound.toInt()
            control.value = main.design.control.toInt()
        }
    }

    val genrePanel = GenrePanel(loc, data).apply {
        genre.addActionListener {
            subgenre.removeAllItems()
            when(matchingOnly.isSelected) {
                true -> (genre.selectedItem as Genre).subgenres.filter { it.matches }
                false -> (genre.selectedItem as Genre).subgenres
            }.forEach(subgenre::addItem)

            printData()
        }

        subgenre.addActionListener {
            printData()
        }

        matchingOnly.addActionListener {
            subgenre.removeAllItems()
            when(matchingOnly.isSelected) {
                true -> (genre.selectedItem as Genre).subgenres.filter { it.matches }
                false -> (genre.selectedItem as Genre).subgenres
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
        pack()
    }
}