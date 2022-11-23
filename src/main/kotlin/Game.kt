import pt.isel.canvas.*
import Maze.*

const val GRID_HEIGHT = 520
const val GRID_WIDTH = 400

data class Game(val dim:Dimension, val man:Man)
data class Dimension(val width:Int, val height:Int)
data class Position(val col:Int, val line:Int)
data class Man(val pos:Position, val dir:Direction, val push:Boolean = false)

enum class Direction {UP, RIGHT, DOWN, LEFT}

fun Game.draw(canvas: Canvas, maze: Maze){
    canvas.erase()
    //man.drawMan(canvas)
    println(maze.cells)
    maze.cells.drawMap(canvas, this.man)
}

fun List<Cell>.drawMap(canvas: Canvas, man: Man){
    this.forEach {
        val filename = when (it.type) {
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
            canvas.drawImage(filename, man.pos.col, man.pos.line, 40, 52)
        else
            canvas.drawImage(filename, it.pos.col * 40, it.pos.line * 52, 40, 52)

    }
}
/*
    canvas.drawImage("soko|120,217,40,52",x,x,x,x) // desenhar caixa escura
canvas.drawImage("soko|80,217,40,52",x,x,x,x) // desenhar caixa branca
canvas.drawImage("soko|40,217,40,52",x,x,x,x) // desenhar parede
canvas.drawImage("soko|0,217,40,52",x,x,x,x) // desenhar bola azul
canvas.drawImage("soko|40,0,40,52",x,x,x,x) // desenhar homem parado UP
canvas.drawImage("soko|40,52,40,52",x,x,x,x) // desenhar homem parado Right
canvas.drawImage("soko|40,104,40,52",x,x,x,x) // desenhar homem parado Down
canvas.drawImage("soko|40,156,40,52",x,x,x,x) // desenhar homem parado Left
canvas.drawImage("soko|160,0,40,52",x,x,x,x) // desenhar homem empurrar UP
canvas.drawImage("soko|160,52,40,52",x,x,x,x) // desenhar homem empurrar Right
canvas.drawImage("soko|160,104,40,52",x,x,x,x) // desenhar homem empurrar Down
canvas.drawImage("soko|160,156,40,52",x,x,x,x) //
 */
fun Man.drawMan(canvas:Canvas){
    val manDir = when(dir){
        Direction.DOWN -> "soko|40,104,40,52"
            Direction.RIGHT -> "soko|40,52,40,52"
            Direction.LEFT -> "soko|40,156,40,52"
            Direction.UP -> "soko|40,0,40,52"
    }
    canvas.drawImage(
        manDir,
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

fun Man.isInLimits(mp: List<Cell>):Boolean{
    val x=mp.map{it.pos} as List<Position>//listOf<Position>()
    return x.contains(this.pos)

} //map.map{it.pos}.contains(this.pos)

fun main(){
    onStart {
        val gridDimension = Dimension(GRID_WIDTH, GRID_HEIGHT)
        val mazeDesc = loadMap(level1)
        val arena = Canvas(mazeDesc.width * 40, mazeDesc.height * 52, WHITE)
        val manCellPos = mazeDesc.cells.filter { it.type == Type.MAN}.first().pos
        var myGame = Game(gridDimension, Man(Position(manCellPos.col * 40, manCellPos.line * 52), Direction.DOWN))
        myGame.draw(arena, mazeDesc)
        arena.onKeyPressed{k ->
            myGame = Game(gridDimension, myGame.man.move(k.code))
            myGame.draw(arena, mazeDesc)
            }
        }
    onFinish {  }
    }
