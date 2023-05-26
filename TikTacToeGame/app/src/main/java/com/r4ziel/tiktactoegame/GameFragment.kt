package com.r4ziel.tiktactoegame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.r4ziel.tiktactoegame.databinding.FragmentGameBinding
import com.r4ziel.tiktactoegame.utilities.BlockClickListner
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private val viewModel: GameFragmentViewModel by viewModel()

    private lateinit var binding: FragmentGameBinding

    private val blockItemClickListner = object: BlockClickListner {
        override fun onBlockClicked(block: Block) {
            TODO("Not yet implemented")
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }


    }


    fun runGame() {

    }
}