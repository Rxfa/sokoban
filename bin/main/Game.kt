import pt.isel.canvas.*

const val GRID_HEIGHT = 520
const val GRID_WIDTH = 400

data class Game(val dim:Dimension, val man:Man)
data class Dimension(val width:Int, val height:Int)
data class Position(val col:Int, val line:Int)
data class Man(val pos:Position, val dir:Direction, val push:Boolean = false)

enum class Direction {UP, RIGHT, DOWN, LEFT}

fun Game.draw(canvas: Canvas){
    canvas.erase()
    man.drawMan(canvas)
}

fun Man.drawMan(canvas:Canvas){
    canvas.drawImage(
        "soko|40,52,40,52",
        pos.col,
        pos.line,
        40,
        52
    )
}

fun Man.move(key: Int):Man {
    return when(key){
        UP_CODE -> Man(pos.copy(line=pos.line - 52), Direction.UP)
        RIGHT_CODE -> Man(pos.copy(col=pos.col + 40), Direction.RIGHT)
        LEFT_CODE -> Man(pos.copy(col=pos.col - 40), Direction.LEFT)
        DOWN_CODE -> Man(pos.copy(line=pos.line + 52), Direction.DOWN)
        else -> this
    }
}

fun main(){
    onStart {
        val gridDimension = Dimension(GRID_WIDTH, GRID_HEIGHT)
        val arena = Canvas(gridDimension.width, gridDimension.height, WHITE)
        var myGame = Game(gridDimension, Man(Position(50, 80), Direction.DOWN))
        myGame.draw(arena)
        arena.onKeyPressed{k ->
            myGame = Game(gridDimension, myGame.man.move(k.code))
            myGame.draw(arena)
            }
        }
    onFinish {  }
    }
