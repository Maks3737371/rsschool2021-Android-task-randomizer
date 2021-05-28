package com.rsschool.android2021;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements FirstFragment.FirstFragmentInterface, SecondFragment.SecoundFragmentInterface {      // класс, унаследованный от AppCompatActivity используется для создания Activity
    private Boolean secondFragmentOn;
    private Integer randomVal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {                                  // переопределяем функцию создания активити
        super.onCreate(savedInstanceState);                                                         // создаем активити
        setContentView(R.layout.activity_main);                                                     // сюда помещаем тот Layout который мы будем видеть при запуске приложения
        openFirstFragment(0);                                                          // вызов функции открытия первого фрагмента
    }

    public void openFirstFragment(int previousNumber) {
        secondFragmentOn = false;                                                                   // флаг что второе окно не открыто
        randomVal = previousNumber;
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);                   // создаем новый первый фрагмент
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();     // getSupportFragmentManager - чтобы запускать фрагмент из Активити.
                                                                                                    // beginTransaction позволяет делать что-либо с фрагментами
        transaction.replace(R.id.container, firstFragment);                                         // через транзакцию заменяем второй фрагмент первым. Контейнер - вьюха в которой будет лежать наш фрагмент
        // TODO: invoke function which apply changes of the transaction                             // TODO
        transaction.commit();                                                                       // commit - осуществление транзакции
    }

    public void openSecondFragment(int min, int max) {                                             // функция открытия второго фрагмента
        // TODO: implement it
        secondFragmentOn = true;                                                                     // флаг активного второго окна
        //randomVal = (min..max).random()
        final Fragment secondFragment = SecondFragment.newInstance(min, max);                       // создаем новый второй фрагмент
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();     // getSupportFragmentManager - чтобы запускать фрагмент из Активити
        transaction.replace(R.id.container, secondFragment);                                        // Через транзакцию заменяем первый фрагмент вторым. Контейнер - вьюха в которой будет лежать наш фрагмент
        transaction.commit();                                                                       // commit - осуществление транзакции
    }

    @Override
    public void onBackPressed() {
        if (secondFragmentOn)
        {
            openFirstFragment(randomVal);
        }
        else super.onBackPressed();
    }
}