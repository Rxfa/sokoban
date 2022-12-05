import pt.isel.canvas.Canvas

val targets = mapLayout.positionsOfType(Type.TARGET)

/**
 * Draws a target for [Position] in [this], upsizing its coordinates and size to fit in the canvas.
 */
fun List<Position>.drawTargets(canvas: Canvas) = forEach {
    canvas.drawImage(
        "soko|0,217,40,52",
        it.col * BLOCK_WIDTH,
        it.line * BLOCK_HEIGHT,
        BLOCK_WIDTH,
        BLOCK_HEIGHT
    )
}
