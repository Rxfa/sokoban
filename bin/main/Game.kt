import pt.isel.canvas.*

const val BLOCK_HEIGHT = 52
const val BLOCK_WIDTH = 40
const val GRID_HEIGHT = BLOCK_HEIGHT * 10
const val GRID_WIDTH = BLOCK_WIDTH * 10

data class Game(
    val dim: Dimension,
    val man: Man,
    val boxes: List<Position>,
)

enum class Direction { UP, RIGHT, DOWN, LEFT }

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    walls.drawWall(canvas)
    targets.drawTargets(canvas)
    this.boxes.drawBoxes(canvas)
    this.man.drawMan(canvas)
}


fun main() {
    onStart {
        val gridDimension = Dimension(GRID_WIDTH, GRID_HEIGHT)
        val mapLayout = loadMap(level1)
        val arena = Canvas(mapLayout.width * BLOCK_WIDTH, mapLayout.height * BLOCK_HEIGHT, WHITE)
        val manCellPos = loadMap(level1).positionOfType(Type.MAN)
        val manStartingPosition = Man(manCellPos, Direction.RIGHT)
        var myGame = Game(gridDimension, manStartingPosition, boxes)
        myGame.draw(arena)
        arena.onKeyPressed { k ->
            val manNextPosition = if (!targets.containsAll(myGame.boxes))
                myGame.moveMan(convertKeyToDir(k.code))
            else
                myGame.man
            val boxNextPosition = if (myGame.boxes.contains(manNextPosition.pos) &&
                manNextPosition.verifyNextStep(manNextPosition.dir, myGame.boxes) !in listOf("wall", "box")
            ) {
                myGame.boxes.moveBoxes(manNextPosition)
            } else {
                myGame.boxes
            }
            myGame = Game(gridDimension, manNextPosition, boxNextPosition)
            myGame.draw(arena)
        }
    }
    onFinish { }
}
