import pt.isel.canvas.Canvas

val boxes = loadMap(level1).positionsOfType(Type.BOX)

fun List<Position>.drawBoxes(canvas:Canvas){
    this.forEach{
        canvas.drawImage(
            "soko|80,217,40,52" ,
            it.col * BLOCK_WIDTH,
            it.line * BLOCK_HEIGHT,
            BLOCK_WIDTH,
            BLOCK_HEIGHT
        )
    }
}
