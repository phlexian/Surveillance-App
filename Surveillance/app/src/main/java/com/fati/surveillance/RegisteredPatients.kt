package com.fati.surveillance

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fati.surveillance.adapter.RegisteredPatientRecycler
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_registered_patients.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.registered_patient_row.*
import kotlinx.android.synthetic.main.registered_patient_row.view.*
import java.lang.StringBuilder

class RegisteredPatients : Fragment() {

    private lateinit var db: FirebaseFirestore
    var patientNameFromFB: ArrayList<String> = ArrayList()
    var patientAgeFromFB: ArrayList<String> = ArrayList()
    var patientNoFromFB: ArrayList<String> = ArrayList()
    var patientIllFromDB: ArrayList<String> = ArrayList()
    var adapter: RegisteredPatientRecycler? = null


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Kayıtlı Hastalar"
        return inflater.inflate(R.layout.fragment_registered_patients, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        getDataFromFirestore()
        db.collection("Hasta").get()

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        adapter = RegisteredPatientRecycler(patientNameFromFB,
            patientNoFromFB,
            patientAgeFromFB,
            patientIllFromDB)
        recyclerView.adapter = adapter


        val database = FirebaseDatabase.getInstance().reference
        val getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sb = StringBuilder()
                for (i in snapshot.children) {
                    val klima = i.child("klima").getValue()
                    val nem = i.child("nem").getValue()
                    val isik = i.child("oda_isigi").getValue()
                    val perde = i.child("perde_motoru").getValue()
                    val sicaklik = i.child("sicaklik").getValue()
                    val yatak = i.child("yatak_motoru").getValue()
                    sb.append("\nSıcaklık: $sicaklik\t \t \tNem: $nem\t \t \tOda Işığı: $isik\n \nPerde: $perde\t \t \tKlima: $klima\t \t \tYatak: $yatak\n")
                }
                veri.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }


    private fun getDataFromFirestore() {


        db.collection("Hasta").orderBy("tarih",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(activity,
                    exception.localizedMessage.toString(),
                    Toast.LENGTH_SHORT)
                    .show()

            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        patientNoFromFB.clear()
                        patientNameFromFB.clear()
                        patientAgeFromFB.clear()
                        patientIllFromDB.clear()


                        val documents = snapshot.documents
                        for (document in documents) {
                            val hastaAdi = document.get("hastaAdı") as String
                            val hastaYas = document.get("hastaYaş") as String
                            val hastaNo = document.get("hastaNo") as String
                            val hastaHastalik = document.get("hastaHastalık") as String
                            val timestamp = document.get("tarih") as com.google.firebase.Timestamp
                            val tarih = timestamp.toDate()


                            patientNoFromFB.add(hastaNo)
                            patientAgeFromFB.add(hastaYas)
                            patientNameFromFB.add(hastaAdi)
                            patientIllFromDB.add(hastaHastalik)

                            adapter!!.notifyDataSetChanged()


                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){

        R.id.deletePatient ->{
            val intent = Intent(activity,DeletePatient::class.java)
            startActivity(intent)

        }
    }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
