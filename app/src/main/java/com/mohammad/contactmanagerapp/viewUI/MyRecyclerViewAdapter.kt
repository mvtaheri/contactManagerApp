package com.mohammad.contactmanagerapp.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mohammad.contactmanagerapp.R
import com.mohammad.contactmanagerapp.databinding.CardItemBinding
import com.mohammad.contactmanagerapp.room.User

class MyRecyclerViewAdapter(
    private val userList: List<User>,
    private val clickListener: (User)->Unit
): RecyclerView.Adapter<MyViewHoldere>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoldere {
      var layoutInflater=LayoutInflater.from(parent.context)
      val binding :CardItemBinding=DataBindingUtil.inflate(layoutInflater,
          R.layout.card_item,parent,false
          )
        return MyViewHoldere(binding)
    }

    override fun getItemCount(): Int {
      return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHoldere, position: Int) {
        holder.bind(userList[position],clickListener)
    }
}
class MyViewHoldere(val binding:CardItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(user:User,clickListener: (User) -> Unit){
        binding.nameTextView.text=user.name
        binding.emailTextView.text=user.email
        binding.listItemLayout.setOnClickListener(){
            clickListener(user)
        }
    }

}