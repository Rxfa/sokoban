import pt.isel.canvas.Canvas

val targets = loadMap(level1).positionsOfType(Type.TARGET)

fun List<Position>.drawTargets(canvas: Canvas){
    this.forEach{
        canvas.drawImage(
            "soko|0,217,40,52",
            it.col * BLOCK_WIDTH,
            it.line * BLOCK_HEIGHT,
            BLOCK_WIDTH,
            BLOCK_HEIGHT
        )
    }
}