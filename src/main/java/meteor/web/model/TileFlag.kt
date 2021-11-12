package meteor.web.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
class TileFlag(
    val x: Int?,
    val y: Int?,
    val z: Int?,
    val flag: Int?,
    val region: Int?,
    val regionX: Int?,
    val regionY: Int?,
) {
    val north = !obstacle && !isWalled(Direction.NORTH)
    val east = !obstacle && !isWalled(Direction.EAST)
    val west = !obstacle && !isWalled(Direction.WEST)
    val south = !obstacle && !isWalled(Direction.SOUTH)
    val obstacle: Boolean
        get() {
            if (flag == 0) {
                return false
            }

            return check(0x100 or 0x20000 or 0x200000 or 0x1000000)
        }

    companion object {
        enum class Direction(val flag: Int) {
            NORTH(0x2),
            WEST(0x80),
            SOUTH(0x20),
            EAST(0x8)
        }
    }
    fun check(checkFlag: Int): Boolean {
        return flag!! and checkFlag != 0
    }

    fun isWalled(direction: Direction): Boolean {
        if (flag == 0) {
            return false
        }

        return check(direction.flag)
    }
}
