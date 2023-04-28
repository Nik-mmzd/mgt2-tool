package pw.modder.mgt2helper.parser

import kotlin.test.*

class PropertiesTest {
    private fun Genre.Properties.verifyDesignDirections(): Boolean {
        val points = length + depth + friendliness + innovation + story + characters + levels + missions
        assertEquals(Genre.Properties.FOCUS_POINTS, points)
        return true
    }

    private fun Genre.Properties.verifyDesignPriorities(): Boolean {
        val points = gameplay + graphics + sound + control
        assertEquals(100, points)
        return true
    }

    private val RPG = Genre.Properties(
        setOf(Genre.Targets.ADULT, Genre.Targets.OLD),
        40, 30, 15, 15,
        8, 5, 0, 2, 8, 8, 5, 4,
        2, 6,7
    )

    private val ACTION = Genre.Properties(
        setOf(Genre.Targets.TEEN, Genre.Targets.ADULT),
        10, 40, 30, 20,
        3, 2, 7, 6, 4, 4, 10, 4,
        5, 7, 9
    )

    private val ADVENTURE = Genre.Properties(
        setOf(Genre.Targets.ADULT, Genre.Targets.OLD),
        40, 30, 15, 15,
        2, 1, 5, 1, 10, 9, 7, 5,
        5, 5, 7
    )

    private val `RPG ACTION` = Genre.Properties(
        setOf(Genre.Targets.ADULT),
        40, 30, 15, 15,
        6, 4, 2, 3, 7, 7, 7, 4,
        3, 6, 7
    )

    private val `RPG ADVENTURE` = Genre.Properties(
        setOf(Genre.Targets.ADULT, Genre.Targets.OLD),
        40, 30, 15, 15,
        7, 4, 2, 1, 9, 8, 5, 4,
        3, 5, 7
    )

    @Test
    fun `Properties combining`(): Unit {
        with(ACTION + ACTION) {
            verifyDesignPriorities()
            verifyDesignDirections()
            assertEquals(ACTION, this)
        }
        with(RPG + ACTION) {
            verifyDesignPriorities()
            verifyDesignDirections()
            assertEquals(`RPG ACTION`, this)
        }
        with(RPG + ADVENTURE) {
            verifyDesignPriorities()
            verifyDesignDirections()
            assertEquals(`RPG ADVENTURE`, this)
        }
    }
}