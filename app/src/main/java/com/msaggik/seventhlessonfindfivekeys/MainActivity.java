package com.msaggik.seventhlessonfindfivekeys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // поля
    private TextView screen, coordinates;
    private float x; // координата касания по оси X
    private float y; // координата касания по оси Y
    private int[] coordinatesKeys; // массив координат 5 ключей
    private int interval = 50; // погрешность поиска

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка разметки к полям
        screen = findViewById(R.id.screen);
        coordinates = findViewById(R.id.coordinates);

        // генерация случайных координат для ключей
        generateRandomCoordinates();

        // обработка касания TextView
        screen.setOnTouchListener(listener);
    }

    // создание слушателя
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            // определение координат касания
            x = motionEvent.getX();
            y = motionEvent.getY();

            // определение типа касания
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: // нажатие
                    coordinates.setText("Нажатие " + x + ", " + y);
                    break;
                case MotionEvent.ACTION_MOVE: // движение
                    coordinates.setText("Движение " + x + ", " + y);
                    // определение поиска ключей
                    for (int i = 0; i < coordinatesKeys.length; i += 2) {
                        if (x >= (coordinatesKeys[i] - interval) && x <= (coordinatesKeys[i] + interval) &&
                                y >= (coordinatesKeys[i + 1] - interval) && y <= (coordinatesKeys[i + 1] + interval)) {
                            int keyNumber = i / 2 + 1; // вычисление номера ключа
                            Toast.makeText(MainActivity.this, "Найден " + keyNumber + "-й ключ", Toast.LENGTH_SHORT).show();
                            break; // если нашли ключ, выходим из цикла
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP: // отпускание
                    coordinates.setText("Отпускание " + x + ", " + y);
                    break;
            }

            return true;
        }
    };

    // метод для генерации случайных координат для ключей
    private void generateRandomCoordinates() {
        coordinatesKeys = new int[10]; // массив для хранения 5 пар координат (X, Y) для 5 ключей
        Random random = new Random();
        for (int i = 0; i < coordinatesKeys.length; i++) {
            coordinatesKeys[i] = random.nextInt(451) + 50; // генерация случайной координаты в диапазоне от 50 до 500 (включительно)
        }
    }
}
