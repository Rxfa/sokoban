import pt.isel.canvas.Canvas

val boxes = mapLayout.positionsOfType(Type.BOX)

/**
 * Draws a box for [Position] in [this], upsizing its coordinates and size to fit in the canvas.
 * Gets different images depending on if [Position] is in [targets].
 */
fun List<Position>.drawBoxes(canvas: Canvas) = forEach {
    val boxImg = if (it in targets) "soko|120,217,40,52" else "soko|80,217,40,52"
    canvas.drawImage(
        boxImg,
        it.col * BLOCK_WIDTH,
        it.line * BLOCK_HEIGHT,
        BLOCK_WIDTH,
        BLOCK_HEIGHT
    )
}

/**
 * Returns a [List<Position>] containing every [Position] different from the one of [man]
and the [Position] of the box in the same [Position] as [man],
moved in the same direction as the former.
 */
fun List<Position>.moveBox(man: Man): List<Position> {
    val (boxToMove, remainingBoxes) = partition { it == man.pos }
    return boxToMove.map { it.nextPosition(man.dir) } + remainingBoxes
}
