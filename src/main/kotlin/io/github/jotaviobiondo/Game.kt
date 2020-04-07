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

    var board: Board = Board(BOARD_WIDTH, BOARD_HEIGHT)
        private set

    private val window: JFrame = JFrame(GAME_NAME)

    private val inputHandler = InputHandler(
        KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_P, KeyEvent.VK_ENTER
    )

    private val gameRenderer = GameRenderer(this)

    private val timer: Timer = Timer(TIMER_DELAY, this::run)

    private var gameState = GameState.NOT_STARTED

    val isRunning: Boolean
        get() = gameState == GameState.RUNNING

    val isPaused: Boolean
        get() = gameState == GameState.PAUSED

    val isGameOver: Boolean
        get() = gameState == GameState.GAME_OVER


    init {
        setupWindow()
        setupListeners()
    }

    private fun setupWindow() {
        window.isResizable = false
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        window.add(gameRenderer.canvas.toAwtComponent())

        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }

    private fun setupListeners() {
        window.addKeyListener(inputHandler)
    }

    fun start() {
        newGame()

        timer.start()
    }

    private fun newGame() {
        board = Board(BOARD_WIDTH, BOARD_HEIGHT)
        gameState = GameState.RUNNING
    }

    /**
     * Game loop
     */
    private fun run(e: ActionEvent) {
        update()
        render()
    }

    private fun update() {
        handleInput()

        if (isRunning) {
            board.tick()
        }

        checkGameOver()
    }

    private fun handleInput() {
        inputHandler.popFirstKey() {
            when (it) {
                KeyEvent.VK_UP -> board.turnSnakeUp()
                KeyEvent.VK_DOWN -> board.turnSnakeDown()
                KeyEvent.VK_RIGHT -> board.turnSnakeRight()
                KeyEvent.VK_LEFT -> board.turnSnakeLeft()
                KeyEvent.VK_P -> pause()
                KeyEvent.VK_ENTER -> restartIfIsGameOver()
            }
        }
    }

    private fun pause() {
        gameState = when (gameState) {
            GameState.RUNNING -> GameState.PAUSED
            GameState.PAUSED -> GameState.RUNNING
            else -> gameState
        }
    }

    private fun restartIfIsGameOver() {
        if (isGameOver) {
            newGame()
        }
    }

    private fun checkGameOver() {
        if (board.gameOver) {
            gameState = GameState.GAME_OVER
        }
    }

    private fun render() {
        gameRenderer.render()
    }

}