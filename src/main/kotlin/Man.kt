import pt.isel.canvas.*

/**
 * Represents man [Position], [Direction] and if it is facing a box.
 */
data class Man(val pos: Position, val dir: Direction, val push: Boolean = false)

/**
 * Returns a [Direction] or null depending on the given [key].
 */
fun convertKeyToDir(key: Int) = when (key) {
    UP_CODE -> Direction.UP
    DOWN_CODE -> Direction.DOWN
    RIGHT_CODE -> Direction.RIGHT
    LEFT_CODE -> Direction.LEFT
    else -> null
}

/**
 * Draws a man, upsizing its coordinates and size to fit in the canvas.
 * Gets different images depending on its [Position], [Direction], and if it's facing a box.
 */
fun Man.drawMan(canvas: Canvas) {
    val manImg = when (dir) {
        Direction.DOWN -> if (push) "soko|160,104,40,52" else "soko|40,110,40,52"
        Direction.RIGHT -> if (push) "soko|160,52,40,52" else "soko|40,52,40,52"
        Direction.LEFT -> if (push) "soko|160,156,40,52" else "soko|40,156,40,52"
        Direction.UP -> if (push) "soko|160,0,40,52" else "soko|40,0,40,52"
    }
    canvas.drawImage(
        manImg,
        pos.col * BLOCK_WIDTH,
        pos.line * BLOCK_HEIGHT,
        BLOCK_WIDTH,
        BLOCK_HEIGHT
    )
}

/**
 * Returns a [Man],
with its properties depending on a given [dir] and the type of cell it will face one or two steps ahead.
 */
fun Game.moveMan(dir: Direction?): Man {
    dir ?: return man
    val movedMan = when {
        man.isFacing(dir, boxes) in listOf("empty", "target") -> man.takeStep(dir)
        man.isFacing(dir, boxes) == "box" &&
                man.takeStep(dir).isFacing(dir, boxes) !in listOf("wall", "box") -> man.takeStep(dir)

        else -> man.copy(dir = dir)
    }
    return if (isFacingBox(dir)) movedMan.copy(push = true) else movedMan
}

/**
 * Returns a [Man], changing its [Position] and [Direction] depending on a given [dir].
 */
fun Man.takeStep(dir: Direction) = Man(pos.nextPosition(dir), dir)

/**
 * Returns a string, according to the type of cell [this] is facing,
given a [dir] and the [Position] of every box, target ,and wall in the map.
 */
fun Man.isFacing(dir: Direction, updatedBoxes: List<Position>) = when (takeStep(dir).pos) {
    in walls -> "wall"
    in updatedBoxes -> "box"
    in targets -> "target"
    else -> "empty"
}

/**
 * Returns True if [this.man] will be facing a box one or two steps ahead, when turned to a given [dir].
 */
fun Game.isFacingBox(dir: Direction) =
    "box" in listOf(man.isFacing(dir, boxes), man.takeStep(dir).isFacing(dir, boxes))
