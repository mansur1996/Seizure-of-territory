package com.SeizureOfTerritory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.SeizureOfTerritory.R
import com.SeizureOfTerritory.databinding.ItemInnerBinding

class InnerAdapter(
    private val stageWidth: Int,
    private val array: IntArray,
    private val coveredArray: BooleanArray,
) : RecyclerView.Adapter<InnerAdapter.ViewHolder>(){
    inner class ViewHolder(private val itemInnerBinding: ItemInnerBinding) : RecyclerView.ViewHolder(itemInnerBinding.root){
        fun onBind(item: Int, coveredItem: Boolean) {
            itemInnerBinding.apply {
                ivInner.layoutParams.height = stageWidth/13
                ivInner.layoutParams.width = stageWidth/13
                if (coveredItem){
                    ivInner.setBackgroundResource(R.drawable.border_gradient_yellow);
                }else{
                    ivInner.setBackgroundResource(R.drawable.border_gradient_red);
                }
                setImage(item, ivInner)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(array[position], coveredArray[position])
    }

    override fun getItemCount(): Int = array.size

    private fun setImage(item: Int, imageView: ImageView) {
        when(item){
            1-> {
                imageView.setImageResource(R.mipmap.ic_ball1)
            }
            2-> {
                imageView.setImageResource(R.mipmap.ic_ball2)
            }
            3-> {
                imageView.setImageResource(R.mipmap.ic_ball3)
            }
            4-> {
                imageView.setImageResource(R.mipmap.ic_ball4)
            }
            5-> {
                imageView.setImageResource(R.mipmap.ic_ball5)
            }
            6-> {
                imageView.setImageResource(R.mipmap.ic_ball6)
            }
            7-> {
                imageView.setImageResource(R.mipmap.ic_ball7)
            }
            8-> {
                imageView.setImageResource(R.mipmap.ic_ball8)
            }
            9-> {
                imageView.setImageResource(R.mipmap.ic_ball9)
            }
            10-> {
                imageView.setImageResource(R.mipmap.ic_ball10)
            }
        }
    }
}