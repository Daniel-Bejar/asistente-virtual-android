package com.example.chatbot.actividades.asistente

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.actividades.asistente.constants.OPEN_COLE
import com.example.chatbot.actividades.asistente.constants.OPEN_GOOGLE
import com.example.chatbot.actividades.asistente.constants.OPEN_SEARCH
import com.example.chatbot.actividades.asistente.constants.OPEN_YOUTUBE
import com.example.chatbot.actividades.asistente.constants.RECEIVE_ID
import com.example.chatbot.actividades.asistente.constants.SEND_ID
import kotlinx.android.synthetic.main.activity_asistente.*
import kotlinx.coroutines.*

class AsistenteActivity : AppCompatActivity() {
    private lateinit var adapter: MessagingAdapter
    private val botlist = listOf("Jarvis","Constant","R2-D2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asistente)

        recyclerView()
        clickEvent()
        val random =(0..2). random()
        customMessage("Hola! Estás hablando con ${botlist[random]}, En que te puedo ayudar?")
    }
    private fun recyclerView(){
        adapter= MessagingAdapter()
        rv_messages.adapter= adapter
        rv_messages.layoutManager= LinearLayoutManager(applicationContext)
    }
    private fun sendMessage(){
        val message=et_message.text.toString()
        val timeStamp= Time.timestamp()

        if (message.isNotEmpty()){
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }
    private fun botResponse(message: String){
        val timestamp= Time.timestamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response= BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

                when(response){
                    OPEN_GOOGLE ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        val searchTerm: String?= message.substringAfter("busca")
                        site.data= Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    OPEN_YOUTUBE ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.youtube.com/")
                        startActivity(site)
                    }
                    OPEN_COLE ->{

                        val site=Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.fpcorredor.com/actualidad")
                        startActivity(site)
                    }

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun clickEvent(){
        btn_send.setOnClickListener {
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }
    private fun customMessage(message :String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timestamp = Time.timestamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}
