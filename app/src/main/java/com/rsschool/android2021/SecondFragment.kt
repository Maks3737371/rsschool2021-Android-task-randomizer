package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(                  // Переопределяем onCreateView
        inflater: LayoutInflater,               // Класс, кот. умеет из содержимого layout-файла(xml) создать View-элемент
        container: ViewGroup?,                  // ViewGroup - класс кот сост. из View и других ViewGroup
        savedInstanceState: Bundle?             // Bundle - хранилище в кот. харнится пара ключ-значение для передачи м/у компонентами: фрагментами, активити
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)              // inflate - создание из содержимого layout-файла View-элемента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {                           // ПРИВЯЗЫВАЕМ ЛОГИКУ К ВЬЮХЕ
        super.onViewCreated(view, savedInstanceState)                                               /* Вызывается сразу после возврата onCreateView, но до восстановления любого сохраненного состояния в представлении.
                                                                                                    Это дает подклассам возможность инициализировать себя, как толькоони узнают, что их иерархия представлений полностью
                                                                                                    создана. Однако на этом этапе иерархия представления фрагмента не привязана к его родительскому элементу.*/
                                                                                                    // savedInstanceState - Если не равно нулю, этот фрагмент реконструируется из предыдущего сохраненного состояния

        result = view.findViewById(R.id.result)                                                     // присваиваем переменной вьюху по ее ИД
        backButton = view.findViewById(R.id.back)                                                   // присваиваем переменной вьюху по ее ИД
        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0                                             // Если в мин из bundle лежит null то min = 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0                                             // Если в мин из bundle лежит null то min = 0
        val random = generate(min, max)                                                             // Объявление функции генерации рандома
        result?.text = random.toString()                                                            // Преобразовываем результат в строку

        backButton?.setOnClickListener {                                                            // При нажатии на кнопку(back) будет вызываться это действие
            mainActivity().openFirstFragment(random)                                                // Открываем первый фрагмент
        }
    }


    private fun generate(min: Int, max: Int): Int {
        randomVal = (min..max).random()
        return randomVal
    }

    companion object {                                                  // сопутствующий объект для удобного доступа к членам класса внутри него
        @JvmStatic                                                      //// На JVM вы можете иметь члены сопутствующих объектов, сгенерированные как настоящие статические методы и поля, если вы используете @JvmStaticаннотацию
        fun newInstance(min: Int, max: Int) = SecondFragment().apply {
            arguments = bundleOf(SecondFragment.MIN_VALUE_KEY to min, SecondFragment.MAX_VALUE_KEY to max)
        }

        @JvmStatic
        var randomVal: Int = 0
        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}