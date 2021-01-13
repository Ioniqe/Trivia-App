package com.example.calatour.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia.R
import com.example.trivia.model.Question

class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var valueTextView: TextView
    private lateinit var createdAtTextView: TextView
    private lateinit var categoryTitleTextView: TextView

    init {
        questionTextView = itemView.findViewById(R.id.questionContentTextView)
        answerTextView = itemView.findViewById(R.id.answerContentTextView)
        valueTextView = itemView.findViewById(R.id.valueContentTextView)
        createdAtTextView = itemView.findViewById(R.id.createdAtContentTextView)
        categoryTitleTextView = itemView.findViewById(R.id.categoryTitleContentTextView)
    }

    fun bindData(data: Question) {
        questionTextView.text = data.question
        answerTextView.text = data.answer
        valueTextView.text = data.value.toString()
        createdAtTextView.text = data.created_at
        categoryTitleTextView.text = data.category.title
    }
}