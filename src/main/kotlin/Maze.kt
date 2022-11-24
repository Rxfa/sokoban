//package Maze

/**
 * Maze map textually described.
 * List of strings where each string is a map line.
 *
 * '#' -> Wall ; '.' -> Target ; 'M' -> Man ; 'B' -> Box
 */
val level1 = listOf(
    "  ##### ",
    "###   # ",
    "# B # ##",
    "# #  . #",
    "#    # #",
    "##B#.  #",
    " #M  ###",
    " #####  ",
)

val original1 = listOf(
    "    #####",
    "    #   #",
    "    #B  #",
    "  ###  B##",
    "  #  B B #",
    "### # ## #   ######",
    "#   # ## #####  ..#",
    "# B  B          ..#",
    "##### ### #M##  ..#",
    "    #     #########",
    "    #######",
)

/**
 * Type of each cell of a maze.
 */
enum class Type{ WALL, TARGET, MAN, BOX }

/**
 * Column and line of one position.
 * @property col Number of column (0 until width)
 * @property line Numer of line (0 until height)
 */
data class Position(val col:Int, val line:Int)

/**
 * Represents the position and type of each cell.
 * @property pos Position of cell
 * @property type The type of cell
 */
data class Cell(val pos: Position, val type: Type)

/**
 * Represents the maze information.
 * @property width Total width of maze.
 * @property height Total height of maze.
 * @property cells Position and type of each non-empty cell.
 */
data class Maze(val width: Int, val height:Int, val cells: List<Cell>)

/**
 * Get the position of the first cell of a given [type].
 * @receiver Maze with the cells to find.
 * @param type The type of cell to find.
 * @return Found cell position.
 */
fun Maze.positionOfType(type: Type): Position = cells.first{ it.type==type }.pos

/**
 * Get the positions of all cells of a given [type].
 * @receiver Maze with the cells to find.
 * @param type The type of cell to find.
 * @return All cell positions of given [type].
 */
fun Maze.positionsOfType(type: Type) = cells.filter{ it.type==type }.map{ it.pos }

/**
 * Convert a symbol from a cell in the map to its cell type.
 * @receiver Symbol of the cell.
 * @return The cell type.
 */
fun Char.toCellType(): Type? = when (this) {
    '#' -> Type.WALL
    '.' -> Type.TARGET
    'M' -> Type.MAN
    'B' -> Type.BOX
    else -> null
}

/**
 * Load a textually described map and return its representation.
 * @param maze Textually described map.
 * @return Representation of the map.
 */
fun loadMap(maze: List<String>): Maze {
    val cells = buildList {
        maze.forEachIndexed { idxLine, line ->
            line.forEachIndexed { idxCol, char ->
                char.toCellType()?.let { type ->
                    add( Cell(Position(idxCol, idxLine), type) )
                }
            }
        }
    }
    return Maze(maze.maxOf{ it.length }, maze.size, cells)
}

