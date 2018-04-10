package com.abbytiffany.geoquizzer;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    int global = 0;
    int userScore = 0;
    int attempts = 0;
    String[] questions;
    String[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void getQuestions(){
        Resources arr = getResources();
        questions = arr.getStringArray(R.array.states_array);
    }
    public void getAnswers() {
        Resources arr = getResources();
        answers = arr.getStringArray(R.array.capitals_array);
    }

    public int getRandomNumber(int length) {
        int newRandom = new Random().nextInt(length);
        return newRandom;
    }
    public int getRightAnswer() {
        int newRand = getRandomNumber(2);
        return newRand;
    }
    public int getWrongAnswer(int correct) {
        int newRand = getRandomNumber(answers.length);
        while (newRand == correct) {
            newRand = getRandomNumber(answers.length);
        }
        return newRand;
    }

    public void populate(int resource, String[] arr, int randomNumber) {
        TextView currentRes = findViewById(resource);
        currentRes.setText(arr[randomNumber]);
    }
    public void displayScore(int resource, int score) {
        TextView currentScore = findViewById(resource);
        currentScore.setText(Integer.toString(score));
    }
    public void displayText(int resource, int textResource) {
        TextView currentScore = findViewById(resource);
        currentScore.setText(getResources().getText(textResource));
    }

    public void populateQuestionAndAnswer(View view) {
        getQuestions();
        getAnswers();
        global = getRandomNumber(questions.length);
        int correctAnswerNumber = getRightAnswer();
        if (correctAnswerNumber == 1) {
            populate(R.id.answer_text_1, answers, global);
            populate(R.id.answer_text_2, answers, getWrongAnswer(global));
        } else {
            populate(R.id.answer_text_2, answers, global);
            populate(R.id.answer_text_1, answers, getWrongAnswer(global));
        }

        populate(R.id.question_text, questions, global);

        displayText(R.id.current_score_title, R.string.current_score);
        displayScore(R.id.current_score_number, userScore);
        displayText(R.id.slash_symbol, R.string.slash_symbol);
        displayScore(R.id.highest_possible_score_number, attempts);

        displayText(R.id.question_text_constant, R.string.what_is);
        displayText(R.id.question_title, R.string.question_title);
        displayText(R.id.answer_title, R.string.answer_title);



    }

    public void answerWasCorrect(final View view) {
        view.setBackgroundResource(R.color.colorAccent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundResource(R.color.white);
                userScore++;
                attempts++;
                populateQuestionAndAnswer(view);
            }
        }, 500);
    }
    public void answerWasIncorrect(final View view) {
        view.setBackgroundResource(R.color.colorPrimaryLight);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundResource(R.color.white);
                attempts++;
                populateQuestionAndAnswer(view);
            }
        }, 500);
    }
    public void checkIfCorrect(View view) {
        if (((TextView)view).getText().toString().equals(answers[global])) {
            answerWasCorrect(view);
        } else {
            answerWasIncorrect(view);
        }
    }
    public void resetScore(View view){
        userScore = 0;
        attempts = 0;
        populateQuestionAndAnswer(view);
    }
}
