package com.example.crud

import java.util.*


data class person( var id : Int = getAutoId() ,var name : String , val email : String , var contact : String , var address : String){
    companion object{

        fun getAutoId ():Int{
            val random = Random()
            return random.nextInt(100)
        }

    }

}
