package com.example.trivia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calatour.rest_api.QuestionsAPI
import com.example.trivia.adapters.QuestionAdapter
import com.example.trivia.model.Category
import com.example.trivia.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.time.Instant
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var questionRecyclerView: RecyclerView
    private val dataSource = ArrayList<Question>() //the list where we will hold the info
    private lateinit var questionAdapter: QuestionAdapter

    private val questionsApi = QuestionsAPI.create()
    private val questionId = 1001

    private fun getQuestions(givenNumber: String) {
        questionsApi.getQuestions(givenNumber).enqueue(object : retrofit2.Callback<List<Question>> {
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                val errorTextView = findViewById<TextView>(R.id.questionsTextView)
                errorTextView.text = "Please check your internet connection. Server unreachable"
            }

            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                if (response.isSuccessful) {
                    dataSource.clear()
                    dataSource.addAll(response.body()!!)
                    questionAdapter.notifyDataSetChanged()
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addQuestionMenuOption) {
            val myIntent = Intent(this, NewQuestionActivity::class.java)
            startActivityForResult(myIntent, questionId)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == questionId) { //we are coming back from the new question activity
            if (resultCode == Activity.RESULT_OK) {
                val question = data!!.getStringExtra("question")
                val answer = data.getStringExtra("answer")
                val value = data.getStringExtra("value")
                val category = data.getStringExtra("category")

                val newQuestion = Question(
                    1, answer, question, value.toInt(),
                    "", Instant.now().toString(), "", 1, 1,
                    Category(1, category, "", "", 1)
                )

                dataSource.add(0, newQuestion)
                questionAdapter.notifyDataSetChanged()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(view: View?) {
        val numberOfQuestions = findViewById<EditText>(R.id.numberOfQuestionsEditText)
        val errorNumberOfQuestions = findViewById<TextView>(R.id.errorMessageTextView)

        errorNumberOfQuestions.setTextColor(getColor(R.color.colorRed))

        if (numberOfQuestions.text.toString() == "") {
            errorNumberOfQuestions.text = "You need to input a number!"
        } else if (numberOfQuestions.text.toString().toInt() < 1 || numberOfQuestions.text.toString().toInt() > 100) {
            errorNumberOfQuestions.text = "Invalid number"
        } else {
            errorNumberOfQuestions.text = ""

            val loadingQuestionsProgressBar =
                findViewById<ProgressBar>(R.id.loadingQuestionsProgressBar)

            loadingQuestionsProgressBar.visibility = View.VISIBLE
            questionRecyclerView.visibility = View.GONE

            getQuestions(numberOfQuestions.text.toString())

            Timer().schedule(1500) {
                Handler(mainLooper).post {
                    loadingQuestionsProgressBar.visibility = View.GONE
                    questionRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadButton = findViewById<Button>(R.id.downloadQuestionsButton)
        downloadButton.setOnClickListener(this)

        loadingQuestionsProgressBar.visibility = View.GONE

        //------------------------------------------------------

        questionAdapter = QuestionAdapter(this, dataSource)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        questionRecyclerView = findViewById(R.id.questionsListRecyclerView)
        questionRecyclerView.layoutManager = layoutManager
        questionRecyclerView.adapter = questionAdapter

        //--------------------------------------------------------
    }
}
