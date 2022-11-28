import pt.isel.canvas.Canvas

val boxes = loadMap(level1).positionsOfType(Type.BOX)

fun List<Position>.drawBoxes(canvas: Canvas) {
    this.forEach {
        val boxImg = if (targets.contains(it)) "soko|120,217,40,52" else "soko|80,217,40,52"
        canvas.drawImage(
            boxImg,
            it.col * BLOCK_WIDTH,
            it.line * BLOCK_HEIGHT,
            BLOCK_WIDTH,
            BLOCK_HEIGHT
        )
    }
}

fun List<Position>.moveBoxes(man: Man): List<Position> {
    return if (contains(man.pos) && man.verifyNextStep(man.dir, walls, targets, boxes) != "wall") {
        val boxToBeMoved = first { it == man.pos }
        listOf(
            boxToBeMoved.nextPosition(man.dir),
            this.first { it != man.pos }
        )
    } else {
        this
    }
}

fun Position.nextPosition(dir: Direction) = when (dir) {
    Direction.UP -> Position(col, line - 1)
    Direction.DOWN -> Position(col, line + 1)
    Direction.LEFT -> Position(col - 1, line)
    Direction.RIGHT -> Position(col + 1, line)
}
