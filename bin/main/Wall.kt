import pt.isel.canvas.*

val walls = loadMap(level1).positionsOfType(Type.WALL)

fun List<Position>.drawWall(canvas: Canvas) {
    forEach {
        canvas.drawImage(
            "soko|40,217,40,52",
            it.col * BLOCK_WIDTH,
            it.line * BLOCK_HEIGHT,
            BLOCK_WIDTH,
            BLOCK_HEIGHT
        )
    }
}

