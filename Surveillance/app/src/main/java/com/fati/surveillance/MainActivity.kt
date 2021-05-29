package com.fati.surveillance

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.registered_patient_row.*


class   MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {


    val FINE_STORAGE_RQ= 101
    lateinit var userProfileFragment: UserProfile
    lateinit var registrationFragment: Registration
    lateinit var docInfoFragment: DocInfo
    lateinit var registeredPatientsFragment: RegisteredPatients
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setSupportActionBar(findViewById(R.id.toolbar))
        //setSupportActionBar(toolbar)
        setContentView(R.layout.activity_main)
        init()
        auth = FirebaseAuth.getInstance()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
       // addPatient()

    //navbar butonları tıklama+
    private fun init() {
//menü butonu+
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//menü butonu-
        nav_view.setNavigationItemSelectedListener(this)


        userProfileFragment = UserProfile()
        registrationFragment = Registration()
        docInfoFragment = DocInfo()
        registeredPatientsFragment= RegisteredPatients()

        supportFragmentManager
            .beginTransaction().replace(R.id.fragment_container, userProfileFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
//nav buton tıklama+
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_exit -> {
                Toast.makeText(applicationContext, "Çıkış Başarılı!", Toast.LENGTH_LONG)
                    .show()

                auth.signOut()
                val intent = Intent(applicationContext, LogIn::class.java)
                startActivity(intent)
                finish()
            }

            R.id.nav_registration -> {

                supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_container, registrationFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }


            R.id.nav_userInfo -> {

                supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_container, userProfileFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

           /* R.id.nav_docInfo -> {

                supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_container, docInfoFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
*/
            R.id.nav_patients -> {

                supportFragmentManager
                    .beginTransaction().replace(R.id.fragment_container,registeredPatientsFragment )
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
//nav buton tıklama-
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
        super.onBackPressed()
    }
}

    //navbar butonları tıklama fonskiyonu+
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
    //navbar buton tıklama fonksiyonu-

/*


fun checkForPermissions(permission:String,name:String,requestCode: Int){

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        when {
            ContextCompat.checkSelfPermission(applicationContext,
                permission) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(applicationContext, "İzin Verildi", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name,requestCode)
            else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"($name) izni reddedildi",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext,"($name) izni alındı",Toast.LENGTH_SHORT).show()
            }

            when (requestCode) {

                FINE_STORAGE_RQ -> innerCheck("Depolama")

            }



        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Uygulamayı Kullanabilmek için Gerekli ($name) izinleri Sağlanamdı ")
            setTitle("İzin Gerekli")
            setPositiveButton("Tamam"){dialog,wich ->

                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
      }
        val dialog = builder.create()
        dialog.show()

    /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }else{
    }
*/
    }



}





//navbar butonları tıklama-

/*addPatientButton.setOnClickListener {

    var patientNo = patientNoText.text.toString().toInt()
    var patientName = patientNameText.text.toString()
    var patientAge= patientAgeText.text.toString().toInt()

    db.setValue(PatientProperties(patientNo,patientName,patientAge))

}
*/