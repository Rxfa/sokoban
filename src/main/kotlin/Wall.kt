import pt.isel.canvas.*

val walls = mapLayout.positionsOfType(Type.WALL)

/**
 * Draws a wall for [Position] in [this], upsizing its coordinates and size to fit in the canvas.
 */
fun List<Position>.drawWall(canvas: Canvas) = forEach {
    canvas.drawImage(
        "soko|40,218,40,52",
        it.col * BLOCK_WIDTH,
        it.line * BLOCK_HEIGHT,
        BLOCK_WIDTH,
        BLOCK_HEIGHT
    )
}
