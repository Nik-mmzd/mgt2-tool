package pw.modder.mgt2helper.frames

import pw.modder.mgt2helper.utils.*
import java.awt.GridBagLayout
import java.io.*
import java.util.ResourceBundle
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.filechooser.FileFilter

class SavePanel(private val loc: ResourceBundle): JPanel(GridBagLayout()) {
    val fileChooser = JFileChooser().apply {
        dialogTitle = loc.getString("file.title.save")
        fileSelectionMode = JFileChooser.FILES_ONLY
        isAcceptAllFileFilterUsed = true

        addChoosableFileFilter(object : FileFilter() {
            override fun accept(f: File): Boolean {
                if (f.isDirectory) return true

                return (f.name.startsWith("savegame", true) || f.name.startsWith("mp", true))
                        && f.extension.equals("txt", true)
            }

            override fun getDescription(): String {
                return loc.getString("file.desc.mgt2savetxt")
            }
        })

        fileFilter = choosableFileFilters.last()
    }

    val savePath = JTextField().apply {
        setDefaultSize()
        isEditable = false
        text = loc.getString("label.save.null")

        val constraints = generateConstrains(0, 0, 2)

        this@SavePanel.add(this, constraints)
    }
    val btnReset = JButton(loc.getString("btn.save.reset")).apply {
        setDefaultSize()

        val constraints = generateConstrains(1, 1, 1)

        this@SavePanel.add(this, constraints)
    }
    val btnLoad = JButton(loc.getString("btn.save.load")).apply {
        setDefaultSize()

        val constraints = generateConstrains(0, 1, 1)

        this@SavePanel.add(this, constraints)
    }
}