package com.example.projokes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.projokes.R
import com.example.projokes.databinding.ItemJokeBinding
import com.example.projokes.model.JokeCategory
import kotlinx.android.synthetic.main.item_joke.view.*

class JokesListAdapter(val jokesList: ArrayList<JokeCategory>) :
    RecyclerView.Adapter<JokesListAdapter.JokeViewHolder>(), JokeClickListener {

    class JokeViewHolder(var view: ItemJokeBinding) : RecyclerView.ViewHolder(view.root)

    fun updateJokeList(newJokesList: List<JokeCategory>) {
        jokesList.clear()
        jokesList.addAll(newJokesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemJokeBinding>(inflater, R.layout.item_joke, parent, false)
        return JokeViewHolder(view)
    }

    override fun getItemCount() = jokesList.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.view.joke = jokesList[position]
        holder.view.listener = this
    }

    override fun onJokeClicked(v: View) {
        val uuid = v.jokeId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.jokeUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}