package com.r4ziel.tiktactoegame

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.r4ziel.tiktactoegame.databinding.ItemViewBlockBinding
import com.r4ziel.tiktactoegame.entities.Block
import com.r4ziel.tiktactoegame.utilities.BlockClickListener

/**
 * Created by Jarvis Charles on 5/26/23.
 */
class TableAdapter(
    private val blockListener: BlockClickListener
) : RecyclerView.Adapter<TableAdapter.ViewHolder>(){

    private var blockList: MutableList<Block> = mutableListOf()

    fun update(data: List<Block>) {
        blockList.clear()
        blockList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemViewBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val block = blockList[position]
        holder.bind(block)
    }

    override fun getItemCount(): Int = blockList.size

    inner class ViewHolder(private val binding: ItemViewBlockBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(block: Block) {

            binding.apply {
                binding.blockItemClick = blockListener
                binding.block = block
                tvXOrO.text = block.xOrO
                binding.blockView.setBackgroundColor(Color.BLACK)

                if (block.isClicked){
                    when (block.playerNumber) {
                        1 -> binding.blockView.setBackgroundColor(Color.BLUE)
                        2 -> binding.blockView.setBackgroundColor(Color.RED)

                    }
                }
            }
        }
    }
}