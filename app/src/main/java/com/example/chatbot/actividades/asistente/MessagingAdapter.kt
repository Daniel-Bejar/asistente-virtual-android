package com.example.chatbot.actividades.asistente

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.actividades.asistente.constants.RECEIVE_ID
import com.example.chatbot.actividades.asistente.constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {
    var messageslist= mutableListOf<Message>()
 inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     init {
         itemView.setOnClickListener {
//             to remove messages on the item clicked
             messageslist.removeAt(adapterPosition)
             notifyItemRemoved(adapterPosition)
         }
     }
 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent ,false))
    }
    override fun getItemCount(): Int {
        return messageslist.size
    }
    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageslist[position]
        when(currentMessage.id){
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }


    fun insertMessage(message: Message){
        this.messageslist.add(message)
        notifyItemInserted(messageslist.size)
        
    }
}