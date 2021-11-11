package com.example.chatbot.actividades.asistente

import com.example.chatbot.actividades.asistente.constants.OPEN_COLE
import com.example.chatbot.actividades.asistente.constants.OPEN_GOOGLE
import com.example.chatbot.actividades.asistente.constants.OPEN_SEARCH
import com.example.chatbot.actividades.asistente.constants.OPEN_YOUTUBE

object BotResponse {
    fun basicResponses(message :String):String {
        val random=(0..2).random()
        val message=message.toLowerCase()
        return when{
           message.contains("hola") ->
            {
                when(random){
                    0->"Hola!"
                    1->"Hey"
                    2->"Namaste"
                    else ->"error"
                }
            }
            message.contains("que tal?") ->
            {
                when(random){
                    0->"Bien!"
                    1->"Tirando y tu?"
                    2->"Muy bien, y tu??"
                    else ->"error"
                }
            }
            message.contains("bien") ->
            {
                "Genial!"
            }
            message.contains("mal") ->
            {
                ":("
            }


            message.contains("lanzar") && message.contains("moneda")-> {
                var flip=(0..1).random()
                val  result= if(flip==0)"cara" else "cruz"
                "He lanzado una moneda y ha salido... $result"
            }
            message.contains("calcula")->{
                val equation: String?= message.substringAfter("calcula")
                return try {
                    val answer= SolveMath.solveMath(equation ?: "0")
                    answer.toString()
                }catch (e:Exception){
                    "No se me dan bien las matemáticas :( "
                }

            }
            message.contains("hora") && message.contains("?")->{
                Time.timestamp()
            }
            message.contains("abre") && message.contains("google") ->{
                OPEN_GOOGLE
            }
            message.contains("busca") ->{
                OPEN_SEARCH
            }
            message.contains("abre") && message.contains("colegio") ->{
                OPEN_COLE
            }
            message.contains("abre") && message.contains("youtube") ->{
                OPEN_YOUTUBE
            }
            else -> {
                when (random) {
                    0 -> "No te he entendido"
                    1 -> "Error 404"
                    2 -> "Lo siento, si quieres buscarlo en google prueba con ''busca...''"
                    else -> "error"
                }
            }
        }
    }
}