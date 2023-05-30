package com.r4ziel.tiktactoegame

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.r4ziel.tiktactoegame.databinding.FragmentGameBinding
import com.r4ziel.tiktactoegame.entities.Block
import com.r4ziel.tiktactoegame.utilities.BlockClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.system.exitProcess

/**
 * The Main Game Fragment That Handles UI Elements.
 */
class GameFragment : Fragment() {

    private val viewModel: GameFragmentViewModel by viewModel()

    private lateinit var binding: FragmentGameBinding

    private val blockItemClickListener = object: BlockClickListener {
        override fun onBlockClicked(block: Block) {
            if (!block.isClicked && !viewModel.isGameOver() && !viewModel.isDrawGame()){
                Toast.makeText(requireContext(), block.id.toString(), Toast.LENGTH_SHORT).show()
                viewModel.updateTurn(block)
            }
        }
    }

    private val tableAdapter = TableAdapter(blockListener = blockItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.recyclerview.adapter = tableAdapter
        binding.recyclerview.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.isGameInProgress)
            viewModel.startGame()
        observeViewModel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.blockLiveData.removeObservers(viewLifecycleOwner)
        viewModel.isGameOverLiveData.removeObservers(viewLifecycleOwner)
        viewModel.isDrawLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun observeViewModel() {
        viewModel.blockLiveData.observe(viewLifecycleOwner) {
            it?.let { blocks ->
                tableAdapter.update(blocks)
            }
        }

        viewModel.isDrawLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showDialog(true)
                }
            }
        }

        viewModel.isGameOverLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showDialog(false)
                }
            }
        }
    }

    private fun showDialog(isDrawGame: Boolean) {
        if (isDrawGame) {
            Log.i("GameFragment", "Game Over: Draw" )
            val build = AlertDialog.Builder(activity)
            build.setTitle("Game Over")
            build.setMessage("The game ended in a draw..."+"\n\n"+"Do you want to play again")
            build.setPositiveButton("Ok") { _, _ ->
                viewModel.startGame()
            }
            build.setNegativeButton("Exit") { _, _ ->
                exitProcess(0)
            }
            val alertDialog: AlertDialog = build.create()
            alertDialog.window?.setGravity(Gravity.BOTTOM)
            alertDialog.show()
        } else {
            Log.i("GameFragment", "Game Over winner is: " + viewModel.winner)

            val build = AlertDialog.Builder(activity)
            build.setTitle("Game Over")
            build.setMessage("Player: "+viewModel.winner+ " is the winner..."+"\n\n"+"Do you want to play again")
            build.setPositiveButton("Ok") { _, _ ->
                viewModel.startGame()
            }
            build.setNegativeButton("Exit") { _, _ ->
                exitProcess(0)
            }
            val alertDialog: AlertDialog = build.create()
            alertDialog.window?.setGravity(Gravity.BOTTOM)
            alertDialog.show()
        }
    }
}