package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class FirstFragment :
    Fragment() {      // Создаем класс для первого фрагмента и наследуемся от Fragment

    private var generateButton: Button? = null      // объявление кнопки (Generate)
    private var previousResult: TextView? = null    // текстВьюха (Previous Result)
    private var minEditText: EditText? = null
    private var maxEditText: EditText? = null

    private var minInt: Int? = null
    private var maxInt: Int? = null

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
        minEditText = view.findViewById(R.id.min_value)
        maxEditText = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)                                 // Достаем аргументы, которые ложили в момент (пере)создания фрагмента
        previousResult?.text = resources.getString(R.string.previous_result_text, result)   // Достаём из ресурсов строку по имени + конкатенация числа
        // Выводим аргументы на экран

        generateButton?.setOnClickListener {                                         // При нажатии на кнопку будет вызываться это действие
            if (minEditText?.text?.isEmpty() == true) {
                minInt = null
                hideKeyboard()
                Snackbar.make(view, "Please, fill MIN data field", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (minEditText?.text.toString().toLongOrNull() == null) minInt = null
                minEditText?.text.toString().toLongOrNull()?.let {
                    if (it <= Int.MAX_VALUE && it >= 0) minInt = it.toInt()
                }
            }

            if (maxEditText?.text?.isEmpty() == true) {
                maxInt = null
                hideKeyboard()
                Snackbar.make(view, "Please, fill MAX data field", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (maxEditText?.text.toString().toLongOrNull() == null) maxInt = null
                maxEditText?.text.toString().toLongOrNull()?.let {
                    if (it <= Int.MAX_VALUE && it >= 0) maxInt = it.toInt()
                }
            }

            if (maxInt != null && minInt != null) {
                if (minInt!! > maxInt!!) {
                    hideKeyboard()
                    Snackbar.make(view, "Please, fill MAX num more than MIN", Snackbar.LENGTH_SHORT).show()
                } else {
                    mainActivity().openSecondFragment(minInt!!, maxInt!!)
                }
            }
        }
    }


    companion object {                                              // сопутствующий объект для удобного доступа к членам класса внутри него
        @JvmStatic                                                  //// На JVM вы можете иметь члены сопутствующих объектов, сгенерированные как настоящие статические методы и поля, если вы используете @JvmStatic аннотацию
        fun newInstance(previousResult: Int) = FirstFragment().apply {
            arguments = bundleOf(PREVIOUS_RESULT_KEY to previousResult)
        }


        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}