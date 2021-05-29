package com.fati.surveillance

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_delete_patient.*

class DeletePatient : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_patient)
        supportActionBar?.title= "Hasta Silme"


        fetchUsers()
    }



    private fun fetchUsers(){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        println(auth.currentUser!!.email.toString())
        db.collection("User")
            .get()
            .addOnSuccessListener {documents ->
                for (document in documents)
                    Log.d("TAG", "${document.id} => ${document.data}")

                }.addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }}}
