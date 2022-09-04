package com.example.assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.models.pixabayitem.Hit
import kotlinx.android.synthetic.main.items.view.*

class ISAdapter : RecyclerView.Adapter<ISAdapter.SIViewHolder>() {
    class SIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallBack = object : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SIViewHolder {
        return SIViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SIViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(item.previewURL).into(iv_main)
            setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Hit) -> Unit)? = null
    fun setOnItemClickListner(listner: (Hit) -> Unit) {
        onItemClickListener = listner
    }


}