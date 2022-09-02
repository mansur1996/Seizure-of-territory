package com.SeizureOfTerritory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.SeizureOfTerritory.databinding.ItemMainBinding

class MainAdapter(
    private val stageWidth: Int,
    private val matrix: Array<IntArray>,
    private val coveredMatrixPart: Array<BooleanArray>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(private val itemMainBinding: ItemMainBinding) :
        RecyclerView.ViewHolder(itemMainBinding.root) {
        fun onBind(array: IntArray, coveredArray: BooleanArray) {
            itemMainBinding.rvInner.adapter = InnerAdapter(stageWidth, array, coveredArray)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(matrix[position], coveredMatrixPart[position])
    }

    override fun getItemCount(): Int = matrix.size
}