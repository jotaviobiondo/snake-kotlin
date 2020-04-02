package io.github.jotaviobiondo


import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.Timer

class Game {

    // TODO: clock
    // TODO: score
    // TODO (maybe): increase/decrease snake velocity

    companion object {
        private const val GAME_NAME = "Snake"

        private const val BOARD_WIDTH = 45
        private const val BOARD_HEIGHT = 35

        private const val TIMER_DELAY = 100
    }

    val board = Board(BOARD_WIDTH, BOARD_HEIGHT)

    private val window: JFrame = JFrame(GAME_NAME)

    private val inputHandler = InputHandler()

    private val gameRenderer = GameRenderer(this)

    private val timer: Timer = Timer(TIMER_DELAY, this::run)

    var paused = false
        private set

    var gameOver = false
        private set

    init {
        setupWindow()
        setupListeners()
    }

    private fun setupWindow() {
        window.isResizable = false
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        window.add(gameRenderer.canvas.canvas)

        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }

    private fun setupListeners() {
        window.addKeyListener(inputHandler)
    }

    fun start() {
        gameOver = false

        timer.start()
    }

    fun restart() {
        timer.restart()
    }

    fun pause() {
        paused = !paused

        if (paused) {
            timer.stop()
        } else {
            timer.start()
        }
    }

    /**
     * Game loop
     */
    private fun run(e: ActionEvent) {
        update()
        render()
    }

    private fun update() {
        inputHandler.popFirstKey() {
            when (it) {
                KeyEvent.VK_UP -> board.turnSnakeUp()
                KeyEvent.VK_DOWN -> board.turnSnakeDown()
                KeyEvent.VK_RIGHT -> board.turnSnakeRight()
                KeyEvent.VK_LEFT -> board.turnSnakeLeft()
                // KeyEvent.VK_P -> pause()
                // KeyEvent.VK_ENTER ->
            }
        }

        board.tick()

        if (board.gameOver) {
            gameOver = true
        }
    }

    private fun render() {
        gameRenderer.render()
    }

}