package com.example.crud
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLLiteHelper(context: Context) :  SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)  {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "person.db"
        private const val TBL_PERSON = "tbl_person"
        private const val ID = "ID"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val CONTACT = "contact"
        private const val ADDRESS = "address"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        var createTblPerson = ("CREATE TABLE " + TBL_PERSON + "("+ ID +" INTEGER PRIMARY KEY, "+ NAME + " TEXT, " + EMAIL +  " TEXT, "+ CONTACT +" TEXT , "+ ADDRESS +" TEXT ) ")
        p0?.execSQL(createTblPerson)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_PERSON")
        onCreate(p0)
    }

    fun insertPerson (person: person) : Long {

        var db = this.writableDatabase

        var convertValue = ContentValues()
        convertValue.put(ID,person.id)
        convertValue.put(NAME,person.name)
        convertValue.put(EMAIL,person.email)
        convertValue.put(CONTACT,person.contact)
        convertValue.put(ADDRESS,person.address)

        var  success = db.insert(TBL_PERSON,null,convertValue)

        db.close()

        return success

    }

    fun getAllPerson () : ArrayList<person>{
        var list : ArrayList<person > = ArrayList()

        var select = "SELECT * FROM $TBL_PERSON"

        var db = this.readableDatabase


        var cursor : Cursor?

        try {
            cursor = db.rawQuery(select,null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(select)
            return list
        }
        var id : Int
        var name : String
        var email : String
        var contact : String
        var address : String


        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                contact = cursor.getString(cursor.getColumnIndexOrThrow("contact"))
                address = cursor.getString(cursor.getColumnIndexOrThrow("address"))

                var pr = person(id,name,email,contact,address)
                list.add(pr)
            }while (cursor.moveToNext())

        }
        return list

    }

    fun UpdatePerson(person: person) : Int{
        var db = this.writableDatabase
        var convertValue = ContentValues()
        convertValue.put(ID,person.id)
        convertValue.put(NAME,person.name)
        convertValue.put(EMAIL, person.email)
        convertValue.put(CONTACT,person.contact)
        convertValue.put(ADDRESS, person.address)
        var success = db.update(TBL_PERSON,convertValue,"ID="+person.id,null)
        db.close()
        return success
    }
    fun DeletePerson(id : Int) : Int{
        var db = this.writableDatabase
        var convertValue = ContentValues()
        convertValue.put(ID,id)

        var success = db.delete(TBL_PERSON,"ID="+id,null)
        db.close()
        return success
    }
}