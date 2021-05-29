package com.fati.surveillance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.emailText
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn: AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth= FirebaseAuth.getInstance()
        supportActionBar?.title= "Giriş"

        kayitText.setOnClickListener {
            Log.d("Login", "thats okay")
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()

        }
    }

   fun parolaResetClicked(view: View) {
        if (sign_in_emailText.text.isEmpty()){
            Toast.makeText(
                applicationContext,
                "Lütfen E-mail Adresinizi Giriniz!",
                Toast.LENGTH_SHORT
            ).show()
        } else{
            val email = sign_in_emailText.text.toString()
            val alert = AlertDialog.Builder(this)
            alert
                .setTitle("Parola Sıfırlama")
                .setMessage("Parolanız Sıfırlanacaktır, Devam Etmek İstiyor Musunuz?")
                .setPositiveButton("Evet"){dialog, which ->
                    auth.sendPasswordResetEmail(email)
                    Toast.makeText(this, "Parloanız Başarıyla Sıfırlandı, E-Postanızı Kontrol Ediniz!", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("İptal"){dialog,which->

                }.show()
        }
    }

    fun girisClicked(view: View) {

        val email = sign_in_emailText.text.toString()
        val password = sign_in_passwordText.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Lütfen Boş Alanları Doldurunuz!",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Hoş Geldiniz!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    it.localizedMessage.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}