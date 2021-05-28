package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

 class FirstFragment : Fragment() {      // Создаем класс для первого фрагмента и наследуемся от Fragment

     private var generateButton: Button? = null      // объявление кнопки (Generate)
     private var previousResult: TextView? = null    // текстВьюха (Previous Result)
     var minEditText: EditText? = null
     var maxEditText: EditText? = null
     private var mListener: FirstFragmentInterface? = null

     //var minInt = minEditText?.text.toString().toInt()
     //var maxInt = maxEditText?.text.toString().toInt()

     override fun onAttach(context: Context) {
         super.onAttach(context)
        mListener = context as FirstFragmentInterface
     }

     override fun onDetach() {
         super.onDetach()
        mListener = null
     }

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

         val result =
             arguments?.getInt(PREVIOUS_RESULT_KEY)                         // Достаем аргументы, которые ложили в момент (пере)создания фрагмента
         previousResult?.text =
             "Previous result: ${result.toString()}"              // Выводим аргументы на экран

         // TODO: val min = ...
         // TODO: val max = ...

         generateButton?.setOnClickListener {                                         // При нажатии на кнопку будет вызываться это действие
             // TODO: send min and max to the SecondFragment
             when (true) {
                 (minEditText?.text.toString() == "" || maxEditText?.text.toString() == "")
                 -> Toast.makeText(context, "Please, fill all data fields", Toast.LENGTH_SHORT).show()  // если хоть одно поле пустое - выводим тост*/

                 (minEditText?.text.toString() != "" || maxEditText?.text.toString() != "")
                 -> {
                     when (true) {
                         (minEditText?.text.toString().toInt() > minEditText?.text.toString()
                             .toInt())
                         -> Toast.makeText(context, "Minimum value must be less than Max", Toast.LENGTH_SHORT)
                             .show()              // делаем тост что мин зн-е должно быть больше максимального
                         else
                         -> {
                             mListener?.openSecondFragment(minEditText?.text.toString().toInt(), maxEditText?.text.toString().toInt())
                         }
                     }
                 }
             }
         }
     }

     interface FirstFragmentInterface {
         fun openSecondFragment(min: Int, max: Int)
     }


    companion object {                                              // сопутствующий объект для удобного доступа к членам класса внутри него
        @JvmStatic                                                  //// На JVM вы можете иметь члены сопутствующих объектов, сгенерированные как настоящие статические методы и поля, если вы используете @JvmStatic аннотацию
        fun newInstance(previousResult: Int): FirstFragment         // Фукнция которая будет создавать фрамент
        {
            val fragment = FirstFragment()                          // Обозначаем фрагменту что используется первый фрагмент
            val args = Bundle()                                     // ЗДЕСЬ ПЕРЕДАЮТСЯ АРГУМЕНТЫ ВО ФРАГМЕНТ
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)        // Вставляет значение типа int в отображение этого bundle, заменяя любое существующее значение для данного ключа
            fragment.arguments = args                               // передаем аргументам фрагмента хранилище
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

     override fun onDestroyView() {
         generateButton = null
         previousResult = null
         minEditText = null
         maxEditText = null
         super.onDestroyView()
     }
}