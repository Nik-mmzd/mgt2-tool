package pw.modder.mgt2helper.utils

import javax.swing.JProgressBar

class ProgressBar(): JProgressBar() {
    override fun setValue(n: Int) {
        super.setValue(n)
        string = n.toString()
    }
}