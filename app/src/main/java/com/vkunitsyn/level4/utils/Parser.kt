package com.vkunitsyn.level4.utils

import java.util.*

object Parser {

    //Parses the e-mail into user name
    fun parseEmail(eMail: String): String {
        val userNameArr = eMail.substringBefore('@').split(".")
        var userName = ""
        for (part in userNameArr) {
            userName += part.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                else it.toString()
            } + " "
        }
        return userName.trim()
    }
}