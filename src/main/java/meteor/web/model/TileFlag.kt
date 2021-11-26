package meteor.web.model

data class TileFlag(
    val x: Int?,
    val y: Int?,
    val z: Int?,
    val flag: Int?,
    val region: Int?
) {
    var north = !isWalled(Direction.NORTH)
    var east = !isWalled(Direction.EAST)

    companion object {
        enum class Direction(val flag: Int) {
            NORTH(0x2),
            WEST(0x80),
            SOUTH(0x20),
            EAST(0x8)
        }
    }

    fun isObstacle(): Boolean {
        if (flag == 0) {
            return false
        }

        return check(0x100 or 0x20000 or 0x200000 or 0x1000000)
    }

    fun check(checkFlag: Int): Boolean {
        return flag != 0xFFFFFF && (flag!! and checkFlag != 0)
    }

    fun isWalled(direction: Direction): Boolean {
        if (flag == 0) {
            return false
        }

        return check(direction.flag)
    }
}
