package com.fati.surveillance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_log_in.*

class LogIn : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.title= "Kayıt"
        if (auth.currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        girisText.setOnClickListener {
            Log.d("Login", "thats okay")
            val intent2 = Intent(this, SignIn::class.java)
            startActivity(intent2)
            finish()
        }

    }






    fun kayitClicked(view: View) {

        val email = emailText.text.toString()
        val password = passwordText.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Lütfen Boş Alanları Doldurunuz!",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Hoş Geldiniz!", Toast.LENGTH_SHORT)
                            .show()
                             saveUserToFirebaseFirestore()

                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }.addOnFailureListener { exception ->
                    if (exception != null) {
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }
    }
    private fun saveUserToFirebaseFirestore() {


        val databaseUsername = usernameText.text.toString()
        val databaseEmail= emailText.text.toString()

        val userMap = hashMapOf<String, Any>()
        userMap.put("kullanıcı adı", databaseUsername)
        userMap.put("kullanıcı email",databaseEmail)
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(databaseUsername != null) {
            db.collection("User").document(databaseEmail)
                .set(userMap).addOnCompleteListener { task ->
                    if (task.isComplete && task.isSuccessful) {
                        Toast.makeText(applicationContext, "Başarılı", Toast.LENGTH_SHORT)
                            .show()
                        //  val action=Registration
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext,
                        e.localizedMessage.toString(),
                        Toast.LENGTH_SHORT).show()
                }
        }
    }

}
