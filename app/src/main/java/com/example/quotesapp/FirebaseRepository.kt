package com.example.quotesapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseRepository {
   val db = FirebaseDatabase.getInstance().getReference("quotes")
    fun addQuote(quote: Quote){
        val id = db.push().key!!
        db.child(id).setValue(quote.copy(id = id))
    }
    fun updateQuote(quote: Quote){
     db.child(quote.id).setValue(quote)
    }
    fun deleteQuote(id: String){
     db.child(id).removeValue()
    }
    fun getQuotes(onDataChange: (List<Quote>) -> Unit){
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quotelist = mutableListOf<Quote>()
                snapshot.children.forEach{
                    val quote = it.getValue(Quote::class.java)
                    quotelist.add(quote!!)
                }
                onDataChange(quotelist)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    }
