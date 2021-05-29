package com.fati.surveillance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fati.surveillance.R

class CustomAdapter(

    private val patientNoArray: ArrayList<String>,
) : RecyclerView.Adapter<CustomAdapter.PatientHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_delete_patient, parent, false)
        return PatientHolder(view)

    }

    override fun getItemCount(): Int {
        return patientNoArray.count()
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {

        holder.rowPatientNoText?.text = patientNoArray[position]


    }

    class PatientHolder(view: View) : RecyclerView.ViewHolder(view) {

        var rowPatientNoText: TextView? = null
    }
}