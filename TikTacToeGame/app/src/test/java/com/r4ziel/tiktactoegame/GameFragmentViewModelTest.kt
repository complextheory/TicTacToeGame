package com.r4ziel.tiktactoegame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.r4ziel.tiktactoegame.entities.Block
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertNotNull

/**
 * Created by Jarvis Charles on 5/28/23.
 */

@RunWith(JUnit4::class)
class GameFragmentViewModelTest{

    private lateinit var viewModel: GameFragmentViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private var blocklist = mutableListOf<Block>()
    private var blockLiveData = MutableLiveData<List<Block>>()
    private var player1BlockList = mutableListOf<Int>()
    private var player2BlockList = mutableListOf<Int>()
    private var drawGameBlockList = mutableListOf<Int>()
    private var playerTurn = 1
    private var winner = 0
    private var isGameInProcess = false
    private var isDrawLiveData =  MutableLiveData<Boolean>()
    private var isGameOverLiveData =  MutableLiveData<Boolean>()
    private var counter = 1

    companion object {
        private const val BLOCK_LIST_KEY = "BLOCK_LIST_KEY"
        private const val PLAYER_1_LIST_KEY = "PLAYER_1_LIST_KEY"
        private const val PLAYER_2_LIST_KEY = "PLAYER_2_LIST_KEY"
        private const val DRAW_GAME_LIST_KEY = "DRAW_GAME_LIST_KEY"
        private const val PLAYER_TURN_KEY = "PLAYER_TURN_KEY"
        private const val IS_GAME_OVER_KEY = "IS_GAME_OVER_KEY"
        private const val IS_DRAW_KEY = "IS_DRAW_KEY"
        private const val IS_GAME_IN_PROGRESS_KEY = "IS_GAME_IN_PROGRESS_KEY"
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        blockLiveData.postValue(generateBlocks())
        blockLiveData.postValue(blocklist)
        winner = 0
        playerTurn = 1
        isDrawLiveData.postValue(false)
        isGameOverLiveData.postValue(false)

    }

    private fun generateBlocks(): List<Block> {
        player1BlockList.clear()
        player2BlockList.clear()
        drawGameBlockList.clear()
        blocklist.clear()

        do {
            blocklist.add(Block("", counter, false, 0))
            counter ++
        } while (counter < 10)

        //Reset Values For Next Game
        playerTurn = 1
        counter = 1

        return blocklist
    }

    @Test
    fun gameFragmentViewModel_GameIsStarted_ValuesAreSet() {
        playerTurn = 1

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        viewModel.startGame()

        isGameInProcess = viewModel.isGameInProgress

        assertNotNull(blockLiveData.value)
        assert(isGameOverLiveData.value == false)
        assert(isDrawLiveData.value == false)
        assert(isGameInProcess)
    }

    @Test
    fun gameFragmentViewModel_BlocksAreGenerated_AllListsAreCleared() {
        playerTurn = 1

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        viewModel.generateBlocks()

        assert(player1BlockList.isEmpty())
        assert(player2BlockList.isEmpty())
        assert(drawGameBlockList.isEmpty())
        assert(blocklist.size == 9)
    }

    @Test
    fun gameFragmentViewModel_PlayerTurnIs1_SavedStateIsSaved_PlayerTurnIsUpdated() {
        playerTurn = 1
        blocklist[2].isClicked = true

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        viewModel.updateTurn(blocklist[2])

        assert(savedStateHandle.get<Int>(PLAYER_TURN_KEY) == 2)
    }

    @Test
    fun gameFragmentViewModel_PlayerTurnIs2_SavedStateIsSaved_PlayerTurnIsUpdated() {
        playerTurn = 2
        blocklist[2].isClicked = true

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        viewModel.updateTurn(blocklist[2])

        assert(savedStateHandle.get<Int>(PLAYER_TURN_KEY) == 1)
    }

    @Test
    fun gameFragmentViewModel_IsDrawBlockListFull_BothPlayerListsFull_GameIsDraw() {
        playerTurn = 1
        blocklist[2].isClicked = true

        player1BlockList = mutableListOf(1, 3, 4, 8, 9)
        player2BlockList = mutableListOf(2, 5, 6, 7)

        do {
            drawGameBlockList.add(counter)
            counter++
        }while (counter < 10)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assert(viewModel.isDrawGame())
    }

    @Test
    fun gameFragmentViewModel_IsDrawBlockListNotFull_BothPlayerListsFull_GameIsNotDraw() {
        playerTurn = 1
        blocklist[2].isClicked = true

        player1BlockList = mutableListOf(1, 3, 4, 8, 9)
        player2BlockList = mutableListOf(2, 5, 6, 7)

        do {
            drawGameBlockList.add(counter)
            counter++
        }while (counter < 9)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assert(!viewModel.isDrawGame())
    }

    @Test
    fun gameFragmentViewModel_Player1Wins_IsDrawGameFragmentFull_GameIsNotDraw() {
        playerTurn = 1
        blocklist[2].isClicked = true

        player1BlockList = mutableListOf(2, 4, 5, 8, 9)
        player2BlockList = mutableListOf(1, 3, 6, 7)

        do {
            drawGameBlockList.add(counter)
            counter++
        }while (counter < 9)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assert(!viewModel.isDrawGame())
    }

    @Test
    fun gameFragmentViewModel_Player2Wins_GameIsOver() {
        playerTurn = 1
        player1BlockList = mutableListOf(4, 6, 9, 7)
        player2BlockList = mutableListOf(5, 1, 2, 3)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsTopHorizontalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(1, 2, 3)
        player2BlockList = mutableListOf(5, 7)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsMiddleHorizontalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(4, 5, 6)
        player2BlockList = mutableListOf(9, 7)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsBottomHorizontalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(7, 8, 9)
        player2BlockList = mutableListOf(4, 7)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsLeftVerticalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(1, 4, 7)
        player2BlockList = mutableListOf(5, 9)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsMiddleVerticalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(2, 5, 8)
        player2BlockList = mutableListOf(4, 7)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }

    @Test
    fun gameFragmentViewModel_Player1WinsRightVerticalRow_GameIsOver() {
        playerTurn = 2
        player1BlockList = mutableListOf(3, 6, 9)
        player2BlockList = mutableListOf(4, 7)

        savedStateHandle = SavedStateHandle(mapOf(
            BLOCK_LIST_KEY to blocklist,
            PLAYER_1_LIST_KEY to player1BlockList,
            PLAYER_2_LIST_KEY to player2BlockList,
            DRAW_GAME_LIST_KEY to drawGameBlockList,
            PLAYER_TURN_KEY to playerTurn,
            IS_GAME_OVER_KEY to isGameOverLiveData.value,
            IS_DRAW_KEY to isDrawLiveData.value,
            IS_GAME_IN_PROGRESS_KEY to isGameInProcess
        ))

        viewModel = GameFragmentViewModel(savedState = savedStateHandle)

        assertTrue(viewModel.winner())
    }
}