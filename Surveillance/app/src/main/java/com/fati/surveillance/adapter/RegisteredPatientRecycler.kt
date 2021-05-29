package com.fati.surveillance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fati.surveillance.R

class RegisteredPatientRecycler(
    private val patientNameArray: ArrayList<String>,
    private val patientNoArray: ArrayList<String>,
    private val patientYasArray: ArrayList<String>,
    private val patientIllArray: ArrayList<String>, )
    : RecyclerView.Adapter<RegisteredPatientRecycler.PatientHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.registered_patient_row, parent, false)
        return PatientHolder(view)

    }

    override fun getItemCount(): Int {
        return patientNoArray.count()
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        holder.rowPatientIllText?.text = patientIllArray[position]
        holder.rowPatientNameText?.text = patientNameArray[position]
        holder.rowPatientNoText?.text = patientNoArray[position]
        holder.rowPatientYasText?.text = patientYasArray[position]


    }

    class PatientHolder(view: View) : RecyclerView.ViewHolder(view) {

        var rowPatientIllText: TextView? = null
        var rowPatientNameText: TextView? = null
        var rowPatientNoText: TextView? = null
        var rowPatientYasText: TextView? = null

        init {
            rowPatientNameText = view.findViewById(R.id.row_patient_name)
            rowPatientNoText = view.findViewById(R.id.row_patient_no)
            rowPatientYasText = view.findViewById(R.id.row_patient_age)
            rowPatientIllText = view.findViewById(R.id.row_patient_ill)
        }

    }

}