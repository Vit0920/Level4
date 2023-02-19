package com.vkunitsyn.level4.utils

import java.util.regex.Pattern

object Validator {

    fun validatePassword(pass: String): Int {
        return if (pass.length >= Constants.MIN_PASS_LENGTH) {
            val pattern = Pattern.compile("[^a-zA-Z0-9]")
            val matcher = pattern.matcher(pass)
            val containsSpecialChar = matcher.find()
            if (containsSpecialChar) {
                Constants.STRONG_PASS
            } else {
                Constants.WEAK_PASS
            }
        } else {
            Constants.SHORT_PASS
        }
    }
}