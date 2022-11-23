package pw.modder.mgt2helper.parser

import java.util.*

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
        private val focus: List<Int>,
        val hardcore: Int,
        val cruelty: Int,
        val complexity: Int
    ) {
        constructor(
            targets: Set<Targets>,
            gameplay: Int,
            graphics: Int,
            sound: Int,
            control: Int,
            length: Int,
            depth: Int,
            friendliness: Int,
            innovation: Int,
            story: Int,
            characters: Int,
            levels: Int,
            missions: Int,
            hardcore: Int,
            cruelty: Int,
            complexity: Int
        ) : this(
            targets,
            gameplay,
            graphics,
            sound,
            control,
            listOf(length, depth, friendliness, innovation, story, characters, levels, missions),
            hardcore,
            cruelty,
            complexity
        )

        val length: Int
            get() = focus[0]
        val depth: Int
            get() = focus[1]
        val friendliness: Int
            get() = focus[2]
        val innovation: Int
            get() = focus[3]
        val story: Int
            get() = focus[4]
        val characters: Int
            get() = focus[5]
        val levels: Int
            get() = focus[6]
        val missions: Int
            get() = focus[7]

        operator fun plus(other: Properties): Properties {
            val result = buildList<Int> {
                focus.forEachIndexed { index, value ->
                    add(index, (value + other.focus[index]) / 2)
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
                focus = result,
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
