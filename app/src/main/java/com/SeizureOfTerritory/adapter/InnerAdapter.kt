package com.SeizureOfTerritory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.ItemInnerBinding
import com.SeizureOfTerritory.utils.Utils

class InnerAdapter(
    private val stageWidth: Int,
    private val array: IntArray,
    private val coveredArray: BooleanArray,
) : RecyclerView.Adapter<InnerAdapter.ViewHolder>() {
    inner class ViewHolder(private val itemInnerBinding: ItemInnerBinding) :
        RecyclerView.ViewHolder(itemInnerBinding.root) {
        fun onBind(ball: Int, isCovered: Boolean) {
            itemInnerBinding.apply {
                ivInner.layoutParams.height = stageWidth / 13
                ivInner.layoutParams.width = stageWidth / 13
                if (isCovered) {
                    ivInner.setBackgroundResource(R.drawable.border_gradient_black);
                } else {
                    ivInner.setBackgroundResource(R.drawable.border_gradient_red);
                }
                Utils.setBallToImage(ball, ivInner)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInnerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(array[position], coveredArray[position])
    }

    override fun getItemCount(): Int = array.size

}