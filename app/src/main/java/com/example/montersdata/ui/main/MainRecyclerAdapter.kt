package com.example.montersdata.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androiddata.data.Monster
import com.example.montersdata.R
import com.example.montersdata.util.PrefsHelper

class MainRecyclerAdapter(val context: Context,
                          val monsters: List<Monster>,
                          val itemListener: MonsterItemListener
):
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()

{
    override fun getItemCount() = monsters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutStyle = PrefsHelper.getItemType(parent.context)
        val layoutId = if (layoutStyle == "grid") {
            R.layout.monster_grid_item
        } else {
            R.layout.monster_list_item
        }
        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monster = monsters[position]
        with(holder) {
            nameText?.let {
                it.text = monster.monsterName
                it.contentDescription = monster.monsterName
            }
            ratingBar?.rating = monster.scariness.toFloat()
            Glide.with(context)
                .load(monster.thumbnailUrl)
                .into(monsterImage)
            holder.itemView.setOnClickListener{
                itemListener.onMonsterItemClick(monster)
            }
        }
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.nameText)
        val monsterImage = itemView.findViewById<ImageView>(R.id.monsterImage)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

    interface MonsterItemListener {
        fun onMonsterItemClick(monster: Monster)
    }
}