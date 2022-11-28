import pt.isel.canvas.*

const val BLOCK_HEIGHT = 52
const val BLOCK_WIDTH = 40
const val GRID_HEIGHT = BLOCK_HEIGHT * 8
const val GRID_WIDTH = BLOCK_WIDTH * 8

data class Game(
    val dim: Dimension,
    val man: Man,
    val boxes: List<Position>,
    val walls: List<Position>,
    val targets: List<Position>
)

enum class Direction { UP, RIGHT, DOWN, LEFT }

fun Game.draw(canvas: Canvas, maze: Maze) {
    canvas.erase()
    this.walls.drawWall(canvas)
    this.targets.drawTargets(canvas)
    this.boxes.drawBoxes(canvas)
    this.man.drawMan(canvas)
}


fun main() {
    onStart {
        val gridDimension = Dimension(GRID_WIDTH, GRID_HEIGHT)
        val mapLayout = loadMap(level1)
        val arena = Canvas(mapLayout.width * BLOCK_WIDTH, mapLayout.height * BLOCK_HEIGHT, WHITE)
        val manCellPos = loadMap(level1).positionOfType(Type.MAN)
        val manStartingPosition = Man(manCellPos, Direction.RIGHT, false)
        var myGame = Game(gridDimension, manStartingPosition, boxes, walls, targets)
        myGame.draw(arena, mapLayout)
        arena.onKeyPressed { k ->
            println("boxes: ${targets.containsAll(myGame.boxes)}")
            val manNextPosition = myGame.moveMan(convertKeyToDir(k.code))
            val boxNextPosition = if (myGame.boxes.contains(manNextPosition.pos) &&
                manNextPosition.verifyNextStep(
                    manNextPosition.dir,
                    myGame.walls,
                    myGame.targets,
                    myGame.boxes
                ) != "wall"
            ) {
                myGame.boxes.moveBoxes(manNextPosition)
            } else {
                myGame.boxes
            }
            myGame = Game(gridDimension, manNextPosition, boxNextPosition, walls, targets)
            myGame.draw(arena, mapLayout)
        }
    }
    onFinish { }
}
