package pw.modder.mgt2helper.parser

import java.util.EnumSet

data class Genre(
    val id: Int,
    val names: Map<String, String>,
    val subgenres: Set<Int>,
    val properties: Properties
) {
    enum class Targets {
        KID, TEEN, ADULT, OLD, ALL
    }

    data class Properties(
        val targets: Set<Targets>,
        val gameplay: Int,
        val graphics: Int,
        val sound: Int,
        val control: Int,
        val length: Int,
        val depth: Int,
        val friendliness: Int,
        val innovation: Int,
        val story: Int,
        val characters: Int,
        val levels: Int,
        val missions: Int,
        val hardcore: Int,
        val cruelty: Int,
        val complexity: Int
    ) {
        operator fun plus(other: Properties): Properties {
            val focus = listOf(length, depth, friendliness, innovation, story, characters, levels, missions)
            val otherFocus = with(other) {
                listOf(length, depth, friendliness, innovation, story, characters, levels, missions)
            }

            val result = buildList<Int> {
                focus.forEachIndexed { index, value ->
                    add(index, (value + otherFocus[index]) / 2)
                }

                var freePoints = 40 - sum()

                forEachIndexed { index, value ->
                    if (freePoints == 0)
                        return@buildList

                    if (freePoints > 0) {
                        if (value < 10) {
                            set(index, value + 1)
                            freePoints--
                        }
                    } else {
                        if (value > 0) {
                            set(index, value - 1)
                            freePoints++
                        }
                    }
                }
            }

            return Properties(
                targets = EnumSet.copyOf(targets.intersect(other.targets)),
                gameplay = gameplay,
                graphics = graphics,
                sound = sound,
                control = control,
                length = result[0],
                depth = result[1],
                friendliness = result[2],
                innovation = result[3],
                story = result[4],
                characters = result[5],
                levels = result[6],
                missions = result[7],
                hardcore = (hardcore + other.hardcore) / 2,
                cruelty = (cruelty + other.cruelty) / 2,
                complexity = (complexity + other.complexity) / 2
            )
        }
    }

    override fun toString(): String {
        return names["EN"] ?: names.values.first()
    }
}
