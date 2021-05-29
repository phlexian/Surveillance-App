package com.fati.surveillance

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_registration.*


class Registration : Fragment() {


    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }


    // Database veri ekleme+
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Hasta Ekle"
        auth = FirebaseAuth.getInstance()


        add_patient_button.setOnClickListener {

            if (patientAgeText.text.isEmpty() || patientIllText.text.isEmpty() || patientNameText.text.isEmpty() || patientNoText.text.isEmpty()) {
                Toast.makeText(activity, "Lütfen Boş Alanları Doldurunuz!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val hastaNo = patientNoText.text.toString()
                val hastaYas = patientAgeText.text.toString().toInt()

                if (hastaYas in 0..120) {
                    Toast.makeText(activity, "Kaydediliyor..", Toast.LENGTH_SHORT).show()
                    val patientMap = hashMapOf<String, Any>()
                    patientMap.put("hastaAdı",
                        patientNameText.text.toString())     //Yazılar Sola Yatık Yapılacaksa Böyle Kullanılabilir --->  patientMap.put("hastaAdı", ("Hasta Adı: ${patientNameText.text}"))
                    patientMap.put("hastaNo", ("No:${hastaNo}"))
                    patientMap.put("hastaYaş", ("Yaş: ${patientAgeText.text}"))
                    patientMap.put("hastaHastalık", ("Hastalık: ${patientIllText.text}"))
                    patientMap.put("tarih", Timestamp.now())
                    patientMap.put("ekleyen kullanıcı email", auth.currentUser!!.email.toString())

                    db.collection("Hasta").document(hastaNo)
                        .set(patientMap)
                        .addOnCompleteListener { task ->
                            if (task.isComplete && task.isSuccessful) {
                                val alert = AlertDialog.Builder(requireActivity())
                                alert
                                    .setTitle("Hasta Kaydı")
                                    .setMessage("Hasta Başarıyla Kaydedildi!")
                                    .setPositiveButton("Tamam") { dialog, which ->

                                    }
                                    .show()
                                //  val action=Registration
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(activity,
                                e.localizedMessage.toString(),
                                Toast.LENGTH_SHORT).show()
                        }
                }else{Toast.makeText(activity, "Lütfen Geçerli Bir Yaş Giriniz!", Toast.LENGTH_SHORT)
                    .show()
                }
            }
        }

        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                reminder_button.visibility = View.VISIBLE
                reminder_button.setOnClickListener {

                    Toast.makeText(activity, "Takvim Uygulaması Açılıyor", Toast.LENGTH_SHORT)
                        .show()


                    val hastaAdi = patientNameText.text.toString()
                    val hastalik = patientIllText.text.toString()
                    val intent = Intent(Intent.ACTION_INSERT)
                    intent.setData(CalendarContract.Events.CONTENT_URI)
                    intent.putExtra(Intent.EXTRA_EMAIL, auth.currentUser!!.email.toString())
                    intent.putExtra(CalendarContract.Events.TITLE,
                        "$hastaAdi\n$hastalik Randevusu")
                    startActivity(intent)
                }
            } else {
                reminder_button.visibility = View.INVISIBLE
            }

        }


    }

}

/*
görsel ekleme butonu
        addImageButton.setOnClickListener {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)
        }
*/
/*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null)

            selectedPicture = data.data

        try {
            if (selectedPicture != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = activity?.contentResolver?.let {
                        ImageDecoder.createSource(it,
                            selectedPicture!!)
                    }
                    val bitmap = source?.let { ImageDecoder.decodeBitmap(it) }
                    patientImageView.setImageBitmap(bitmap)

                } else {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,
                        selectedPicture)
                    patientImageView.setImageBitmap(bitmap)

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
*/
/*  private fun checkBoxClick(b: Boolean) {

      checkBoxNo.setOnClickListener { view ->
          if (view is CheckBox) {
              if (view.isChecked) {
                  checkBoxClick(true)

                  Toast.makeText(activity, "isChecked", Toast.LENGTH_SHORT).show()
                  patientNoText.visibility = View.INVISIBLE

              }else {
                  checkBoxClick(false)
              }
          }
      }
  }*/