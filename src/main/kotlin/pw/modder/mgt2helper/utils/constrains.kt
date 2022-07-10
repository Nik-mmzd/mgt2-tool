package pw.modder.mgt2helper.utils

import java.awt.*

fun generateConstrains(x: Int, y: Int, width: Int = 1, fill: Int = GridBagConstraints.HORIZONTAL): GridBagConstraints {
    return GridBagConstraints().apply {
        gridx = x
        gridy = y
        gridwidth = width
        ipadx = 16
        ipady = 4
        weightx = 1.0
        weighty = 1.0
        this.fill = fill
    }
}