package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var sqlitHelper: SQLLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqlitHelper = SQLLiteHelper(this)
        add()
        getAllPerson()
        edit()
        delete()
    }
    fun add(){
        var btnAdd : Button = findViewById(R.id.btnAdd)
        var name = findViewById<TextView>(R.id.name)
        var email = findViewById<TextView>(R.id.email)
        var contact = findViewById<TextView>(R.id.contact)
        var address = findViewById<TextView>(R.id.address)
        btnAdd.setOnClickListener {
            var person = person(name = name?.text.toString(),email = email?.text.toString(),contact = contact?.text.toString(),address = address?.text.toString())
            sqlitHelper.insertPerson(person)
            getAllPerson()
        }
    }

    fun edit(){
        var btnAdd : Button = findViewById(R.id.edit)
        var id = findViewById<TextView>(R.id.id)
        var name = findViewById<TextView>(R.id.name)
        var email = findViewById<TextView>(R.id.email)
        var contact = findViewById<TextView>(R.id.contact)
        var address = findViewById<TextView>(R.id.address)

        btnAdd.setOnClickListener {
            var person = person(id = id?.text.toString().toInt(), name = name?.text.toString(),email = email?.text.toString(),contact = contact?.text.toString(),address = address?.text.toString())
            sqlitHelper.UpdatePerson(person)
            getAllPerson()
        }
    }

    fun delete(){
        var btnAdd : Button = findViewById(R.id.delete)
        var id = findViewById<TextView>(R.id.id)
        var name = findViewById<TextView>(R.id.name)
        var email = findViewById<TextView>(R.id.email)
        var contact = findViewById<TextView>(R.id.contact)
        var address = findViewById<TextView>(R.id.address)

        btnAdd.setOnClickListener {
            sqlitHelper.DeletePerson(id.text.toString().toInt())
            id.text = null
            name.text = null
            email.text = null
            contact.text = null
            address.text = null
            getAllPerson()
        }
    }

    fun getDataEdit(person: person){
      var id = findViewById<TextView>(R.id.id)
      var name = findViewById<TextView>(R.id.name)
      var email = findViewById<TextView>(R.id.email)
      var contact = findViewById<TextView>(R.id.contact)
      var address = findViewById<TextView>(R.id.address)

      id.text = person.id.toString()
      name.text = person.name
      email.text = person.email
      contact.text = person.contact
      address.text = person.address

  }

    fun getAllPerson (){
        var person = sqlitHelper.getAllPerson()
        var recyclerView : RecyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter : adatper = adatper(this)
        adapter.getList(person)
        recyclerView.adapter = adapter
    }

}