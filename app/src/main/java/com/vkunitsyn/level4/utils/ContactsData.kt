package com.vkunitsyn.level4.utils

import com.vkunitsyn.level4.model.Contact
import java.util.UUID

object ContactsData {

   fun getData() = arrayListOf<Contact>(
       Contact(UUID.randomUUID().toString(),
           "https://avas.at.ua/_ph/48/2/349087186.gif?1676009870/_ph/48/2/349087186.gif?1676009870",
           "Vasya", "Doctor","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avas.at.ua/_ph/15/2/454969051.jpg?1676009676",
           "Lisa", "Teacher","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avas.at.ua/_ph/48/2/582562437.gif?1676009878",
           "Sasha", "Designer","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avas.at.ua/_ph/48/2/8570991.jpg?1676009884",
           "Kolya", "Cook","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avas.at.ua/_ph/48/2/396391173.gif?1676010474",
           "Petya", "Clown","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avatarko.ru/img/kartinka/11/multfilm_robot_10393.jpg",
           "Anna", "Housewife","","",""
       ),
       Contact(UUID.randomUUID().toString(),
           "https://avatarko.ru/img/kartinka/33/kapyushon_robot_34631.jpg",
           "Alex", "Pilot","","",""
       ),
   )
}