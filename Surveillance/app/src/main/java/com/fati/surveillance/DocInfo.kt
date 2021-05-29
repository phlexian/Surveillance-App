package com.fati.surveillance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user_info.*

class DocInfo : Fragment() {
    lateinit var db: FirebaseFirestore
lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fetchUsers()

        return inflater.inflate(R.layout.fragment_doc_info, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       /* var getData = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val sb = StringBuilder()
                for (i in snapshot.children){
                    val username = i.child( "kullanıcı adı").getValue()
                    val email = i.child("kullanıcı email").getValue()
                    sb.append("${i.key} $username $email \n")
                }
                user_info_text.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }*/


        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchUsers(){
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val currentEmail= auth.currentUser?.email.toString()
        db.collection("User").get()
            .addOnSuccessListener {
                for (document in it)
                    Log.d("TAG", "${document.id} => ${document.data}")

            }.addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }}}