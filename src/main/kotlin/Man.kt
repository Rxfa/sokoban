import pt.isel.canvas.*


data class Man(val pos:Position, val dir:Direction, val push:Boolean = false)

val manPos = loadMap(level1).positionOfType(Type.MAN)

fun Man.drawMan(canvas: Canvas, position:Position){
    val manDir = when(dir){
        Direction.DOWN -> if(!this.push) "soko|40,104,40,52" else "soko|160,104,40,52"
        Direction.RIGHT -> if(!this.push) "soko|40,52,40,52" else "soko|160,52,40,52"
        Direction.LEFT -> if(!this.push) "soko|40,156,40,52" else "soko|160,156,40,52"
        Direction.UP -> if(!this.push) "soko|40,0,40,52" else "soko|160,0,40,52"
    }
    canvas.drawImage(
        manDir,
        pos.col,
        pos.line,
        BLOCK_WIDTH,
        BLOCK_HEIGHT
    )
}

fun Man.move(key: Int):Man {
    val nextStep = when(key){
        UP_CODE -> Man(pos.copy(line=pos.line - BLOCK_HEIGHT), Direction.UP)
        RIGHT_CODE -> Man(pos.copy(col=pos.col + BLOCK_WIDTH), Direction.RIGHT)
        LEFT_CODE -> Man(pos.copy(col=pos.col - BLOCK_WIDTH), Direction.LEFT)
        DOWN_CODE -> Man(pos.copy(line=pos.line + BLOCK_HEIGHT), Direction.DOWN)
        else -> this
    }
    return this.isInLimits(walls, nextStep)
}
fun Man.isInLimits(walls: List<Position>, nextPosition: Man): Man{
    val manCopy = nextPosition.pos.copy(col=nextPosition.pos.col/BLOCK_WIDTH, line=nextPosition.pos.line/BLOCK_HEIGHT)
    return if (!walls.contains(manCopy))
        nextPosition
        else if(manCopy in boxes) this.copy(dir=nextPosition.dir, push=true)
        else this.copy(dir=nextPosition.dir)
}
// AVANÇAR DOIS QUADRADOS PARA VER SE A CAIXA BATE NUMA PAREDE OU NOUTRA CAIXA
// N CONSEGUE EMPURRAR DUAS CAIXAS
/*
fun Man.pushBox(walls: List<Position>, nextPosition: Position, dir:Direction):Man {
    val twoSteps = 2
    val newPosition = when(dir){
        Direction.UP -> this.pos.copy(line=pos.line - twoSteps * BLOCK_HEIGHT)
        Direction.LEFT -> this.pos.copy(col=pos.col + twoSteps * BLOCK_WIDTH)
        Direction.RIGHT -> this.pos.copy(col=pos.col - twoSteps * BLOCK_WIDTH)
        Direction.DOWN -> this.pos.copy(line=pos.line + twoSteps * BLOCK_HEIGHT)
    }
    if
}

canvas.drawImage("soko|160,0,40,52",x,x,x,x) // desenhar homem empurrar UP
canvas.drawImage("soko|160,52,40,52",x,x,x,x) // desenhar homem empurrar Right
canvas.drawImage("soko|160,104,40,52",x,x,x,x) // desenhar homem empurrar Down
canvas.drawImage("soko|160,156,40,52",x,x,x,x) // desenhar homem empurrar Left
 */
