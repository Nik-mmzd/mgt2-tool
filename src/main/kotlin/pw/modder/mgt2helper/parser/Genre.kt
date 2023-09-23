package pw.modder.mgt2helper.parser

import java.util.*
import kotlin.math.*

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

        // add first, add half of second, convert to float, divide by 1.5, round to int, goto hell
        private fun eggcodeAverage(first: Int, second: Int): Int {
            // (first + second) / 2 // way too easy
            return first.plus(second.div(2))
                .toFloat()
                .div(1.5f)
                .roundToInt()
        }
        operator fun plus(other: Properties): Properties {
            val result = buildList<Int> {
                focus.forEachIndexed { index, value ->
                    add(index, eggcodeAverage(value, other.focus[index]))
                }

                var freePoints = FOCUS_POINTS - sum()

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
                targets = EnumSet.noneOf(Targets::class.java).apply { addAll(targets.intersect(other.targets)) },
                gameplay = gameplay,
                graphics = graphics,
                sound = sound,
                control = control,
                focus = result,
                hardcore = eggcodeAverage(hardcore, other.hardcore),
                cruelty = eggcodeAverage(cruelty, other.cruelty),
                complexity = eggcodeAverage(complexity, other.complexity)
            )
        }

        companion object {
            internal const val FOCUS_POINTS = 40
        }
    }

    override fun toString(): String {
        return names["EN"] ?: names.values.first()
    }
}
