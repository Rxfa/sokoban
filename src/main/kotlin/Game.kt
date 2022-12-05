/**
 * The code comments for this assignment were written in Kdoc.
 * We tried to write it in line with the language best practices
as set by the developers in the official documentation.
 */
import pt.isel.canvas.*

const val BLOCK_HEIGHT = 52
const val BLOCK_WIDTH = 40

val mapLayout = loadMap(level1)

/**
 * Represents map width and height by taking respectively [width] and [height] as properties.
 */
data class Dimension(val width: Int, val height: Int)

/**
 * Possible directions for man.
 */
enum class Direction { UP, RIGHT, DOWN, LEFT }

/**
 * Represents [Man] and [boxes] in the map,
upsizing their coordinates and size, depending on the [Dimension] of the map.
 * @property dim Dimension of the map
 * @property man map representation of man
 * @property boxes list of box positions
 */
data class Game(val dim: Dimension, val man: Man, val boxes: List<Position>)

/**
 * Draws the map according to the positions of every stationary cell(namely [walls] and [targets]) and moving cells,
with the former being present in [Game].
 */
fun Game.draw(canvas: Canvas) {
    canvas.erase()
    walls.drawWall(canvas)
    targets.drawTargets(canvas)
    this.boxes.drawBoxes(canvas)
    this.man.drawMan(canvas)
}

/**
 * Invokes [moveMan] (and returns its output) with the direction corresponding to [key] as an argument
if every target has a box at its position. Else it returns [this.man].
 */
fun Game.manNextPosition(key: Int) =
    if (!targets.containsAll(boxes))
        moveMan(convertKeyToDir(key))
    else
        man

/**
 * Invokes and returns [moveBox] with [this.boxes] as the receiver and [man] as an argument
if one of [this.boxes] is at the same position as [man] **and** [man] is not facing a wall or another box.
Else it returns [this.boxes].
 */
fun Game.boxNextPosition(man: Man) =
    if (boxes.contains(man.pos) && man.isFacing(man.dir, boxes) !in listOf("wall", "box"))
        boxes.moveBox(man)
    else
        boxes

/**
 * Returns a [Position] by incrementing/decrementing properties of [this] based on the given [dir].
 */
fun Position.nextPosition(dir: Direction) = when (dir) {
    Direction.UP -> Position(col, line - 1)
    Direction.DOWN -> Position(col, line + 1)
    Direction.LEFT -> Position(col - 1, line)
    Direction.RIGHT -> Position(col + 1, line)
}

/**
 * Main function.
 */
fun main() {
    onStart {
        val grid = Dimension(mapLayout.width, mapLayout.height)
        val arena = Canvas(mapLayout.width * BLOCK_WIDTH, mapLayout.height * BLOCK_HEIGHT, WHITE)
        var myGame = Game(grid, Man(mapLayout.positionOfType(Type.MAN), Direction.RIGHT), boxes)
        myGame.draw(arena)
        arena.onKeyPressed { key ->
            val manNextPosition = myGame.manNextPosition(key.code)
            val boxNextPosition = myGame.boxNextPosition(manNextPosition)
            myGame = Game(grid, manNextPosition, boxNextPosition)
            myGame.draw(arena)
        }
    }
    onFinish { }
}
