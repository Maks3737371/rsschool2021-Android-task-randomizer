 package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.android2021.SecondFragment.Companion.newInstance
//import java.util.stream.IntStream.generate

 class FirstFragment : Fragment() {      // Создаем класс для первого фрагмента и наследуемся от Fragment

    private var generateButton: Button? = null      // объявление кнопки (Generate)
    private var previousResult: TextView? = null    // текстВьюха (Previous Result)

    override fun onCreateView(                      // Присоединение(onAttach) фрагмента к LayOut
        inflater: LayoutInflater,                   // Класс, кот. умеет из содержимого layout-файла(xml) создать View-элемент
        container: ViewGroup?,                      // ViewGroup - класс кот сост. из View и других ViewGroup
        savedInstanceState: Bundle?                 // Bundle - хранилище в кот. харнится пара ключ-значение для передачи м/у компонентами: фрагментами, активити
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)     // inflate - создание из содержимого layout-файла View-элемента
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {           // ПРИВЯЗЫВАЕМ ЛОГИКУ К ВЬЮХАМ
        super.onViewCreated(view, savedInstanceState)                               /* Вызывается сразу после возврата {@link #onCreateView (LayoutInflater, ViewGroup, Bundle)}, но до восстановления
                                                                                    любого сохраненного состояния в представлении. Это дает подклассам возможность инициализировать себя, как только
                                                                                    они узнают, что их иерархия представлений полностью создана. Однако на этом этапе иерархия представления фрагмента
                                                                                    не привязана к его родительскому элементу.*/
                                                                                    // savedInstanceState - Если не равно нулю, этот фрагмент реконструируется из предыдущего сохраненного состояния
        previousResult = view.findViewById(R.id.previous_result)                    // ниже
        generateButton = view.findViewById(R.id.generate)                           // ниже

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)                         // Достаем аргументы, которые ложили в момент (пере)создания фрагмента
        previousResult?.text = "Previous result: ${result.toString()}"              // Выводим аргументы на экран

        // TODO: val min = ...
        var min = 0
        view.findViewById<EditText>(R.id.min_value).doAfterTextChanged {                            // Находит первое представление-потомок с заданным идентификатором, само представление, если
                                                                                                    // идентификатор совпадает, или если идентификатор недействителен или в иерархии нет соответствующего представления.
                                                                                                    // doAfterTextChanged - выполняет действие, которое будет вызываться после изменения текста
            view.findViewById<EditText>(R.id.min_value).text.toString().toIntOrNull()?.let {        // преобразовываем в строку, потом в Int и достаем сохраненные данные
                min = it                                                                            // засовываем данные в предыд вью
            }
        }
        // TODO: val max = ...
        var max = 0
        view.findViewById<EditText>(R.id.max_value).doAfterTextChanged {                            // выше
            view.findViewById<EditText>(R.id.max_value).text.toString().toIntOrNull()?.let {        // выше
                max = it                                                                            // выше
            }
        }



        generateButton?.setOnClickListener {                                                        // При нажатии на кнопку будет вызываться это действие
            // TODO: send min and max to the SecondFragment
            val secondFragment: Fragment = newInstance(min, max)                                            // Фукнция которая будет создавать фрамент
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()         // beginTransaction позволяет делать что-либо с фрагментами
            transaction.replace(R.id.container, secondFragment)                                     // через транзакцию заменяем первый фрагмент вторым. Контейнер - вьюха в которой будет лежать наш фрагмент
            .commit()                                                                               // commit - осуществление транзакции
        }
    }

    companion object {                                              // сопутствующий объект для удобного доступа к членам класса внутри него

        @JvmStatic                                                  //// На JVM вы можете иметь члены сопутствующих объектов, сгенерированные как настоящие статические методы и поля, если вы используете @JvmStaticаннотацию
        fun newInstance(previousResult: Int): FirstFragment {       // Фукнция которая будет создавать фрамент
            val fragment = FirstFragment()                          // Обозначаем фрагменту что используется первый фрагмент
            val args = Bundle()                                     // ЗДЕСЬ ПЕРЕДАЮТСЯ АРГУМЕНТЫ ВО ФРАГМЕНТ
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)        // Вставляет значение типа int в отображение этого bundle, заменяя любое существующее значение для данного ключа
            fragment.arguments = args                               // передаем аргументам фрагмента хранилище
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}