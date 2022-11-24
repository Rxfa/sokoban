import pt.isel.canvas.*

const val BLOCK_HEIGHT = 52
const val BLOCK_WIDTH = 40
const val GRID_HEIGHT = BLOCK_HEIGHT * 8
const val GRID_WIDTH = BLOCK_WIDTH * 8

data class Game(val dim:Dimension, val man:Man)

enum class Direction {UP, RIGHT, DOWN, LEFT}

fun Game.draw(canvas: Canvas, maze: Maze){
    canvas.erase()
    boxes.drawBoxes(canvas)
    targets.drawTargets(canvas)
    walls.drawWall(canvas)
    man.drawMan(canvas, manPos)
    /*if (this.man.isInLimits(maze.positionsOfType(Type.WALL)))
    else
        this.man.drawMan(canvas, this.man.pos)
*/
}
/*
fun List<Cell>.drawMap(canvas: Canvas, man: Man){
    this.forEach {
        val filename = when(it.type) {
            Type.WALL -> "soko|40,217,40,52"
            Type.BOX -> "soko|80,217,40,52"
            Type.TARGET -> "soko|0,217,40,52"
            Type.MAN -> when(man.dir){
                Direction.DOWN -> "soko|40,104,40,52"
                Direction.RIGHT -> "soko|40,52,40,52"
                Direction.LEFT -> "soko|40,156,40,52"
                Direction.UP -> "soko|40,0,40,52"
            }
        }
        if (it.type == Type.MAN)
            canvas.drawImage(filename, man.pos.col, man.pos.line, BLOCK_WIDTH, BLOCK_HEIGHT)
        else
            canvas.drawImage(filename, it.pos.col * BLOCK_WIDTH, it.pos.line * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT)

    }
}
*/
fun main(){
    onStart {
        val gridDimension = Dimension(GRID_WIDTH, GRID_HEIGHT)
        val mazeDesc = loadMap(level1)
        val arena = Canvas(mazeDesc.width * BLOCK_WIDTH, mazeDesc.height * BLOCK_HEIGHT, WHITE)
        //val manCellPos = mazeDesc.cells.filter { it.type == Type.MAN}.first().pos
        val manCellPos = loadMap(level1).positionOfType(Type.MAN)
        var myGame = Game(
            gridDimension,
            Man(Position(manCellPos.col * BLOCK_WIDTH, manCellPos.line * BLOCK_HEIGHT), Direction.UP)
        )
        myGame.draw(arena, mazeDesc)
        arena.onKeyPressed{k ->
            myGame = Game(gridDimension, myGame.man.move(k.code))
            myGame.draw(arena, mazeDesc)
            }
        }
    onFinish {  }
    }
