package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.parser.*
import pw.modder.mgt2helper.parser.savegame.*
import pw.modder.mgt2helper.utils.*
import java.awt.*
import java.awt.event.*
import java.io.*
import java.nio.file.*
import java.util.*
import javax.swing.*
import kotlin.io.path.*
import kotlin.system.*

class MainFrame(private val config: Config): JFrame() {
    private val loc: ResourceBundle = ResourceBundle.getBundle("locale.gui", UTF8Control)
    init {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                config.matchingOnly = genrePanel.matchingOnly.isSelected
                config.selectedGenre = genrePanel.genre.selectedIndex
                config.selectedSubgenre = genrePanel.subgenre.selectedIndex
                config.save()
                super.windowClosing(e)
            }
        })

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
    private val genresBase = parser.loadGenres()
    var genres = genresBase
    var localizedTopics = parser.loadThemes(config.lang)

    init {
        // setup window
        title = loc.getString("title")
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane = JPanel(GridBagLayout())
        isResizable = true
        isVisible = true
        minimumSize = Dimension(360, 800)
    }

    private fun printData() {
        val main = genrePanel.genre.selectedItem as? Genre ?: return

        val (props, themes) = when(val sub = genrePanel.subgenre.selectedItem) {
            is Genre -> (main.properties + sub.properties) to localizedTopics.filter { main.id in it.matches && sub.id in it.matches }
            else -> main.properties to localizedTopics.filter { main.id in it.matches }
        }

        topics.setListData(themes.map { it.name }.sorted().toTypedArray())
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
            if (genre.selectedItem == null)
                return@addActionListener
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

        border = BorderFactory.createTitledBorder(loc.getString("label.genre"))

        val constraints = generateConstrains(0, 0, 2).apply {
            this.anchor = GridBagConstraints.NORTH
        }

        contentPane.add(this, constraints)
    }

    private val targets = JList<String>().apply {
        val constraints = generateConstrains(0, 1, fill = GridBagConstraints.BOTH).apply {
            weighty = 100.0
        }

        val pane = JScrollPane(this).apply {
            border = BorderFactory.createTitledBorder(loc.getString("label.targetGroups"))
            minimumSize = Dimension(50, 120)
            preferredSize = Dimension(120, 240)
        }

        contentPane.add(pane, constraints)
    }

    private val topics = JList<String>().apply {
        val constraints = generateConstrains(1, 1, fill = GridBagConstraints.BOTH).apply {
            weighty = 100.0
        }

        val pane = JScrollPane(this).apply {
            border = BorderFactory.createTitledBorder(loc.getString("label.topics"))
            minimumSize = Dimension(50, 120)
            preferredSize = Dimension(120, 240)
        }
        contentPane.add(pane, constraints)
    }

    val designDirection = DesignDirectionPanel(loc).apply {
        border = BorderFactory.createTitledBorder(loc.getString("label.designDirection"))

        val constraints = generateConstrains(0, 2, 2)

        contentPane.add(this, constraints)
    }

    val projectDirection = ProjectDirectionPanel(loc).apply {
        border = BorderFactory.createTitledBorder(loc.getString("label.projectDirection"))

        val constraints = generateConstrains(0, 3, 2)

        contentPane.add(this, constraints)
    }

    val designPriority = DesignPriorityPanel(loc).apply {
        border = BorderFactory.createTitledBorder(loc.getString("label.designPriority"))

        val constraints = generateConstrains(0, 4, 2)

        contentPane.add(this, constraints)
    }
    val save = SavePanel(loc).apply {
        border = BorderFactory.createTitledBorder(loc.getString("label.save"))

        val constraints = generateConstrains(0, 5, 2)

        contentPane.add(this, constraints)

        btnReset.addActionListener {
            genres = genresBase
            genrePanel.updateGenres(genres)
            config.lastSave = null
            savePath.text = loc.getString("label.save.null")
            printData()
        }

        btnLoad.addActionListener {
            if (fileChooser.showOpenDialog(this@MainFrame) == JFileChooser.APPROVE_OPTION) {
                loadSave(fileChooser.selectedFile.toPath())
            }
        }
    }

    private fun loadSave(path: Path) {
        val save = try {
            SaveGame.loadFrom(path).also {
                config.lastSave = path
                save.savePath.text = path.fileName.toString()
            }
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                this@MainFrame,
                e.stackTraceToString(),
                loc.getString("file.error.save.title"),
                JOptionPane.ERROR_MESSAGE
            )
            return
        }
        genres = save.updateFromSave(genresBase)
        printData()
        genrePanel.updateGenres(genres)
    }

    init {
        config.lastSave?.run {
            loadSave(this)
        }
        save.fileChooser.currentDirectory = (if (System.getProperty("os.name", "unknown").startsWith("win", true)) {
            Paths.get(System.getenv("LOCALAPPDATA")).parent
                .resolve("locallow").resolve("eggcode").resolve("Mad Games Tycoon 2")
                .takeIf { it.isDirectory() }
        } else {
            config.gamePath?.parent?.parent
                ?.resolve("compatdata/1342330/pfx/drive_c/users/steamuser/AppData/LocalLow/Eggcode/Mad Games Tycoon 2")
                ?.takeIf { it.isDirectory() }
        } ?: Paths.get(".")).toFile()
        genrePanel.matchingOnly.isSelected = config.matchingOnly
        if (config.selectedGenre in 0 until genrePanel.genre.itemCount)
            genrePanel.genre.selectedIndex = config.selectedGenre
        if (config.selectedSubgenre in 0 until genrePanel.subgenre.itemCount)
            genrePanel.subgenre.selectedIndex = config.selectedSubgenre
        printData()
        pack()
    }
}