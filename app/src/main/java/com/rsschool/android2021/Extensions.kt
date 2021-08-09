package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Fragment.mainActivity() = requireActivity() as MainActivity         // Возвращает текущий фрагмент

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }  // Скрываем клавиатуру

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}