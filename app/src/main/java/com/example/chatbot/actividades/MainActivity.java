package com.example.chatbot.actividades;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.chatbot.R;
import com.example.chatbot.actividades.agenda.AgendaActivity;
import com.example.chatbot.actividades.alarma.AlarmaActivity;
import com.example.chatbot.actividades.asistente.AsistenteActivity;
import com.example.chatbot.actividades.calendario.CalendarioActivity;
import com.example.chatbot.actividades.notas.NotasActivity;
import com.example.chatbot.actividades.tareas.TareasActivity;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnasistente = findViewById(R.id.btnasistente);

        btnasistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AsistenteActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnnotas = findViewById(R.id.btnnotas);

        btnnotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, NotasActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnalarma = findViewById(R.id.btnalarma);

        btnalarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AlarmaActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btninfo = findViewById(R.id.btninfo);

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btntareas = findViewById(R.id.btntareas);

        btntareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, TareasActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btncalendario = findViewById(R.id.btncalendario);

        btncalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, CalendarioActivity.class);
                startActivity(intent);
            }
        });

    }



}