package com.example.trivia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.viewholders.QuestionViewHolder
import com.example.trivia.R
import com.example.trivia.model.Question

class QuestionAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Question>
) : RecyclerView.Adapter<QuestionViewHolder>() {

    // get a reference to the LayoutInflater service
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        var rowDesign = inflater.inflate(viewType, parent, false)
        return QuestionViewHolder(rowDesign)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bindData(dataSource.elementAt(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.question_design
    }

}