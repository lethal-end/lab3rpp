package com.example.lab3rpp;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

class ActivityMenu extends AppCompatActivity {

    private DateFormat format = new SimpleDateFormat("HH:mm:ss "); //"yyyy.MM.dd 'at'
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        database.execSQL("DELETE FROM students");
        insertStartInfo();

        dbHelper.close();

        Button btn_openDB = findViewById(R.id.btn_openDB);
        Button btn_addItemDB = findViewById(R.id.btn_addItemDB);
        Button btn_replace = findViewById(R.id.btn_replace);


        btn_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenu.this, ActivityShowDatabase.class);
                startActivity(intent);
            }
        });

        btn_addItemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                Random random = new Random();
                int number;
                number = random.nextInt(name.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());
                database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\','" + data + "');");
                Toast toast = Toast.makeText(ActivityMenu.this, "Запись добавлена!", Toast.LENGTH_LONG);
                toast.show();

                name.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.execSQL("UPDATE students SET name = 'Иванов Иван Иванович' WHERE id = (SELECT max(id) FROM students)");
                Toast toast = Toast.makeText(ActivityMenu.this, "Запись успешно изменена!", Toast.LENGTH_LONG);
                toast.show();

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        name = new ArrayList<>();
        name.add("Кириченко Жанна Тимофеевна");
        name.add("Яшвили Александра Ипполитовна");
        name.add("Усилова Таисия Петровна");
        name.add("Юшкова Всеслава Петровна");
        name.add("Клименкова Эвелина Всеволодовна");
        name.add("Пушкин Александр Сергеевич");
        name.add("Багрова Светлана Данилевна");
        name.add("Ануфриева Владислава Игнатиевна");
        name.add("Соловаьева Ольга Яновна");
        name.add("Желвакова Бронислава Михеевна");
        name.add("Гарифуллин Алексей Аполлинариевич");
        name.add("Кая Рюрик Ипатович");
        name.add("Саянов Данила Филиппович");
        name.add("Балабанов Капитон Адамович");
        name.add("Кобзева Розалия Степановна");
        name.add("Касимов Соломон Дмитриевич");
        name.add("Братцев Демьян Арсениевич");
        name.add("Кочерёжкина Наталья Тимуровна");
        name.add("Диденкова Мирослава Потаповна");
        name.add("Садовничий Каролина Владленовна");
        name.add("Кадцын Феликс Игоревич");
        name.add("Корявова Агафья Ильевна");
        name.add("Расторгуева Изольда Игнатиевна");
        name.add("Полешко Порфирий Яковович");
        name.add("Макаркина Ева Ростиславовна");
        name.add("Вышегородских Потап Андроникович");

        Random random = new Random();
        int number;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(name.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            database.execSQL("INSERT INTO students(name, time) VALUES (\'" + name.get(number)+ "\',\'" + data + "\');");
            name.remove(number);
        }

    }
}
