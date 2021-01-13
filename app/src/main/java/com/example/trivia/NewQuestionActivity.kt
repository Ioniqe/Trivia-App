package com.example.trivia

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class NewQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)


        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            categorySpinner.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val question = findViewById<EditText>(R.id.questionNewEditText)
        val answer = findViewById<EditText>(R.id.answerNewEditText)
        val value = findViewById<EditText>(R.id.valueNewEditText)

        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val category = categorySpinner.selectedItem.toString()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Doriti salvarea inregistrarii?")
            .setPositiveButton(
                "da",
                DialogInterface.OnClickListener { dialog, which ->
                    run {
                        if(verifyFields()){
                            val intent = Intent()
                            intent.putExtra("question", question.text.toString())
                            intent.putExtra("answer", answer.text.toString())
                            intent.putExtra("value", value.text.toString())
                            intent.putExtra("category", category)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            )
            .setNegativeButton(
                "nu",
                DialogInterface.OnClickListener { dialog, which -> finish() })
        builder.create().show()

    }

    private fun verifyFields(): Boolean {
        val question = findViewById<EditText>(R.id.questionNewEditText)
        val answer = findViewById<EditText>(R.id.answerNewEditText)
        val value = findViewById<EditText>(R.id.valueNewEditText)

        val questionError = findViewById<TextView>(R.id.errorNewQuestion)
        val answerError = findViewById<TextView>(R.id.errorNewAnswer)
        val valueError = findViewById<TextView>(R.id.errorNewValue)

        var ok = true

        if(question.text.toString() == ""){
            ok = false
            questionError.text = "te rugam sa completezi campul"
        }else if (question.text.length < 5) {
            ok = false
            questionError.text = "textul trebuie sa fie mai lung de 5 caractere"
        } else {
            questionError.text = ""
        }

        if(answer.text.toString() == ""){
            ok = false
            answerError.text = "te rugam sa completezi campul"
        }else if (answer.text.length < 5) {
            ok = false
            answerError.text = "textul trebuie sa fie mai lung de 5 caractere"
        } else {
            answerError.text = ""
        }

        if(value.text.toString() == ""){
            ok = false
            valueError.text = "te rugam sa completezi campul"
        }else if (value.text.toString().toInt() !in 50..150) {
            ok = false
            valueError.text = "numarul trebuie sa fie intre 50 si 150"
        } else {
            valueError.text = ""
        }

        return ok
    }
}
