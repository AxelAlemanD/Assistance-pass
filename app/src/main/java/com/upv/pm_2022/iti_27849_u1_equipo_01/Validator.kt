package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.widget.EditText

/**
 * Stores the methods to validate different types of input components
 */
object Validator {

    /***
     * Validate that the field contain a value, otherwise it shows a warning
     */
    fun validateField(field: EditText): Boolean{
        var isValid = true
        if(field.text.toString().isEmpty()) {
            field.setError("Please, enter a value")
            field.requestFocus();
            isValid = false
        }
        return isValid
    }

}