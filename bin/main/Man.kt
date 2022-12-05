import pt.isel.canvas.*

data class Man(val pos: Position, val dir: Direction, val push: Boolean = false)

fun convertKeyToDir(key: Int): Direction? {
    return when (key) {
        UP_CODE -> Direction.UP
        DOWN_CODE -> Direction.DOWN
        RIGHT_CODE -> Direction.RIGHT
        LEFT_CODE -> Direction.LEFT
        else -> null
    }
}

fun Man.drawMan(canvas: Canvas) {
    val manImg = when (dir) {
        Direction.DOWN -> if (push) "soko|160,104,40,52" else "soko|40,104,40,52"
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

fun Game.moveMan(dir: Direction?): Man {
    dir ?: return man
    val movedMan = when {
        man.verifyNextStep(dir, boxes) in listOf("empty", "target") -> man.takeStep(dir)

        man.verifyNextStep(dir, boxes) in listOf("box") &&
                man.takeStep(dir).verifyNextStep(dir, walls) !in listOf("wall", "box") -> man.takeStep(dir)

        else -> man.copy(dir = dir)
    }
    return if (toPushOrNotToPush(dir)) {
        movedMan.copy(push = true)
    } else {
        movedMan
    }
}


fun Man.takeStep(dir: Direction): Man {
    return when (dir) {
        Direction.UP -> Man(Position(pos.col, pos.line - 1), Direction.UP)
        Direction.DOWN -> Man(Position(pos.col, pos.line + 1), Direction.DOWN)
        Direction.LEFT -> Man(Position(pos.col - 1, pos.line), Direction.LEFT)
        Direction.RIGHT -> Man(Position(pos.col + 1, pos.line), Direction.RIGHT)
    }
}

fun Man.verifyNextStep(dir: Direction, boxes: List<Position>): String {
    return when (this.takeStep(dir).pos) {
        in walls -> "wall"
        in boxes -> "box"
        in targets -> "target"
        else -> "empty"
    }
}

fun Game.toPushOrNotToPush(dir: Direction) = "box" in listOf(
    man.takeStep(dir).verifyNextStep(dir, boxes),
    man.verifyNextStep(dir, boxes)
)

