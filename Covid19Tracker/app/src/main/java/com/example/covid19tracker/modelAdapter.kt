package com.example.covid19tracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class modelAdapter(context: Context, private val modelList: List<Model>):
    ArrayAdapter<Model>(context, R.layout.testing, modelList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        println(modelList[position].name +" "+ modelList[position].active)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.testing, parent, false)

        val state = view.findViewById<TextView>(R.id.state)
        val active = view.findViewById<TextView>(R.id.active)
        val cured = view.findViewById<TextView>(R.id.cured)
        val death = view.findViewById<TextView>(R.id.death)
        val total = view.findViewById<TextView>(R.id.total)
        val incactive = view.findViewById<TextView>(R.id.incactive)
        val incurred = view.findViewById<TextView>(R.id.incurred)
        val incdeath = view.findViewById<TextView>(R.id.incdeath)

        val model = modelList[position]

        state.text = model.name
        active.text = model.active
        cured.text = model.cured
        death.text = model.death
        total.text = model.total
        incactive.text = model.incAct
        incurred.text = model.incRec
        incdeath.text = model.incDth

        return view
    }
}