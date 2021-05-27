package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(                  // Присоединение(onAttach) фрагмента к LayOut
        inflater: LayoutInflater,               // Класс, кот. умеет из содержимого layout-файла(xml) создать View-элемент
        container: ViewGroup?,                  // ViewGroup - класс кот сост. из View и других ViewGroup
        savedInstanceState: Bundle?             // Bundle - хранилище в кот. харнится пара ключ-значение для передачи м/у компонентами: фрагментами, активити
    ): View? {
        return inflater.inflate(
            R.layout.fragment_second,
            container,
            false
        )              // inflate - создание из содержимого layout-файла View-элемента
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {                           // ПРИВЯЗЫВАЕМ ЛОГИКУ К ВЬЮХАМ
        super.onViewCreated(view, savedInstanceState)                                               /* Вызывается сразу после возврата {@link #onCreateView (LayoutInflater, ViewGroup, Bundle)}, но до восстановления
                                                                                                    любого сохраненного состояния в представлении. Это дает подклассам возможность инициализировать себя, как только
                                                                                                    они узнают, что их иерархия представлений полностью создана. Однако на этом этапе иерархия представления фрагмента
                                                                                                    не привязана к его родительскому элементу.*/
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val random = generate(min, max)
        result?.text = random.toString()


        backButton?.setOnClickListener {                                // При нажатии на кнопку(back) будет вызываться это действие
            // TODO: implement back
            parentFragmentManager.beginTransaction().replace(
                R.id.container, // beginTransaction позволяет делать что-либо с фрагментами(мы заменяем второй фрагмент на первый)
                FirstFragment.newInstance(random)
            )                      // первому фрагменту передаем результат рандома
                .commit()                                               // commit - осуществление транзакции
        }


    }


    private fun generate(
        min: Int,
        max: Int
    ): Int {                     // функция генерации случайного числа в заданных пределах, возвращает рандомное число
        // TODO: generate random number
        return (min..max).random()
    }




    companion object {                                                  // сопутствующий объект для удобного доступа к членам класса внутри него

        @JvmStatic                                                      //// На JVM вы можете иметь члены сопутствующих объектов, сгенерированные как настоящие статические методы и поля, если вы используете @JvmStaticаннотацию
        fun newInstance(min: Int, max: Int): SecondFragment {           // Фукнция которая будет создавать фрамент
            val fragment = SecondFragment()                             // Обозначаем фрагменту что используется первый фрагмент
            val args = Bundle()                                         // ЗДЕСЬ ПЕРЕДАЮТСЯ АРГУМЕНТЫ ВО ФРАГМЕНТ

            // TODO: implement adding arguments
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        /*override fun onDestroyView(){
            super.onDestroyView()
            backButton = null
            result = null
        }*/

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}