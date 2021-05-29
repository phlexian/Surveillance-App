package com.fati.surveillance

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.nav_header.*

class UserProfile : Fragment() {
    lateinit var db : FirebaseFirestore
    var auth = FirebaseAuth.getInstance()
private var fragmentInfo: RecyclerView? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Kullanıcı Bilgisi"

        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = auth.currentUser!!.email.toString()
        user_info_email_text.text = email
        getDataFromFirestore()




        password_reset_button.setOnClickListener {

            val alert = AlertDialog.Builder(requireActivity())
            alert
                .setTitle("Parola Sıfırlama")
                .setMessage("Parolanız Sıfırlanacaktır, Devam Etmek İstiyor Musunuz?")
                .setPositiveButton("Evet"){dialog, which ->
                    auth.sendPasswordResetEmail(email)
                    Toast.makeText(activity, "Parloanız Başarıyla Sıfırlandı, E-Postanızı Kontrol Ediniz!", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("İptal"){dialog,which->

                }
                .show()
        }

        send_email_button.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            Toast.makeText(activity, "Kullandığınız Mail Uygulamasını Seçiniz", Toast.LENGTH_SHORT).show()
            val to = "hastatakibi17@gmail.com"
            val addressees = arrayOf(to)
            intent.putExtra(Intent.EXTRA_EMAIL, addressees)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sorun Bildirimi (Surveillance v1.0)")
            intent.setType("message/rfc822")
            startActivity(Intent.createChooser(intent, "Select Email Sending App :"))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getDataFromFirestore() {

        db = FirebaseFirestore.getInstance()
        db.collection("User").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(activity,
                    exception.localizedMessage.toString(),
                    Toast.LENGTH_SHORT)
                    .show()

            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        val documents = snapshot.documents
                        for (document in documents) {
                            val username = document.get("kullanıcı adı") as String
                            user_info_text.text = username

                        }
                    }
                }
            }
        }
    }


}

