package com.r4ziel.tiktactoegame

import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.r4ziel.tiktactoegame.databinding.FragmentGameBinding
import com.r4ziel.tiktactoegame.utilities.BlockClickListner
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.logging.Level.INFO

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private val viewModel: GameFragmentViewModel by viewModel()

    private lateinit var binding: FragmentGameBinding


    private val blockItemClickListner = object: BlockClickListner {
        override fun onBlockClicked(block: Block) {
            Toast.makeText(requireContext(), block.id.toString(), Toast.LENGTH_SHORT).show()

        }

    }


    private val tableAdapter = TableAdapter(blockListner = blockItemClickListner)

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
        viewModel.getBlockData()
        Log.i("GameFragment", "Should Generate Blocks Blocklist is: " + viewModel.blockLiveData.value?.size)
        observeViewModel()
    }

    override fun onStop() {
        super.onStop()
        viewModel.blockLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun observeViewModel() {
        viewModel.blockLiveData.observe(viewLifecycleOwner) {
            it?.let { blocks ->
                tableAdapter.update(blocks)
            }
        }
    }
}