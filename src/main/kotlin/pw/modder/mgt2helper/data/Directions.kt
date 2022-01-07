package pw.modder.mgt2helper.data

import kotlinx.serialization.Serializable

@Serializable
data class Directions(
    val length: Byte,
    val depth: Byte,
    val friendliness: Byte,
    val innovation: Byte,
    val story: Byte,
    val characters: Byte,
    val levels: Byte,
    val missions: Byte,
    val hardcore: Byte,
    val cruelty: Byte,
    val complexity: Byte
) {
    private constructor(focus: IntArray, hardcore: Int, cruelty: Int, complexity: Int):
            this(focus[0].toByte(), focus[1].toByte(), focus[2].toByte(), focus[3].toByte(),
                focus[4].toByte(), focus[5].toByte(), focus[6].toByte(), focus[7].toByte(),
                hardcore.toByte(), cruelty.toByte(), complexity.toByte())

    private fun getFocusArray(): ByteArray {
        val focus = ByteArray(8)
        focus[0] = length
        focus[1] = depth
        focus[2] = friendliness
        focus[3] = innovation
        focus[4] = story
        focus[5] = characters
        focus[6] = levels
        focus[7] = missions

        return focus
    }

    operator fun plus(other: Directions): Directions {
        val focus = getFocusArray()
        val otherFocus = other.getFocusArray()
        val result = IntArray(8)

        for (i in result.indices) {
            result[i] = (focus[i] + otherFocus[i]) / 2
        }
        var freeFocusPoints = 40 - result.sum()

        if (freeFocusPoints > 0) {
            for (i in result.indices) {
                if (freeFocusPoints > 0 && result[i] < 10) {
                    result[i]++
                    freeFocusPoints--
                }
            }
        }
        if (freeFocusPoints < 0) {
            for (i in result.indices) {
                if (freeFocusPoints < 0 && result[i] > 0) {
                    result[i]--
                    freeFocusPoints++
                }
            }
        }

        return Directions(result, (hardcore + other.hardcore) / 2, (cruelty + other.cruelty) / 2, (complexity + other.complexity) / 2)
    }
}
