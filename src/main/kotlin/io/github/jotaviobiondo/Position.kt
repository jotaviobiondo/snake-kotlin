package io.github.jotaviobiondo

data class Position(val x: Int, val y: Int) {

    fun isOutsideBoard(board: Board): Boolean = x < 0 || x > board.width || y < 0 || y > board.height

}