package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.parser.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.io.*
import java.nio.file.*
import java.util.*
import javax.swing.*
import javax.swing.border.*
import kotlin.system.*

class MainFrame(private val config: Config): JFrame() {
    private val loc: ResourceBundle = ResourceBundle.getBundle("locale.gui", UTF8Control)
    init {
        // check folder exists
        val gamePath = config.gamePath
        if (gamePath == null || !Files.exists(gamePath.resolve("Mad Games Tycoon 2_Data"))) {
            val chooser = JFileChooser().apply {
                currentDirectory = (gamePath ?: Paths.get(".")).toFile()
                dialogTitle = loc.getString("file.title")
                fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                isAcceptAllFileFilterUsed = false
            }

            var file: File? = null
            do {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    if (chooser.selectedFile.resolve("Mad Games Tycoon 2_Data").isDirectory)
                        file = chooser.selectedFile
                } else {
                    exitProcess(1)
                }
            } while (file == null)
            config.gamePath = file.toPath()
            config.save()
        }
    }

    val parser = Parser(config.gamePath!!)
    val genres = parser.loadGenres()
    var localizedTopics = parser.loadThemes(config.lang)

    init {
        // setup window
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

        val (props, themes) = when(val sub = genrePanel.subgenre.selectedItem) {
            is Genre -> (main.properties + sub.properties) to localizedTopics.filter { main.id in it.matches && sub.id in it.matches }
            else -> main.properties to localizedTopics.filter { main.id in it.matches }
        }

        topics.setListData(themes.map { it.name }.toTypedArray())
        targets.setListData(props.targets.map { loc.getString("target.${it.name}") }.toTypedArray())

        with(designDirection) {
            length.value = props.length
            depth.value = props.depth
            friendliness.value = props.friendliness
            innovation.value = props.innovation
            story.value = props.story
            characters.value = props.characters
            levels.value = props.levels
            missions.value = props.missions
        }

        with(projectDirection) {
            hardcore.value = props.hardcore
            cruelty.value = props.cruelty
            complexity.value = props.complexity
        }

        with(designPriority) {
            gameplay.value = props.gameplay
            graphics.value = props.graphics
            sound.value = props.sound
            control.value = props.control
        }
    }

    val genrePanel = GenrePanel(loc, config.lang, genres).apply {
        genre.addActionListener {
            subgenre.removeAllItems()
            val genre = genre.selectedItem as Genre
            when(matchingOnly.isSelected) {
                true -> subgenres.filter { it == null || it.id in genre.subgenres }
                false -> subgenres.filter { it?.id != genre.id }
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
                true -> subgenres.filter { it == null || it.id in genre.subgenres }
                false -> subgenres.filter { it?.id != genre.id }
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