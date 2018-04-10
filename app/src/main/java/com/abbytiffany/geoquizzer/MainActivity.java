package com.abbytiffany.geoquizzer;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    int global = 0;
    int userScore = 0;
    int attempts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String[] getQuestions(){
        Resources arr = getResources();
        String[] questions = arr.getStringArray(R.array.states_array);
        return questions;
    }
    public String[] getAnswers() {
        Resources arr = getResources();
        String[] answers = arr.getStringArray(R.array.capitals_array);
        return answers;
    }

    public int getRandomNumber(int length) {
        int newRandom = new Random().nextInt(length);
        return newRandom;
    }
    public int getRightAnswer() {
        int newRand = getRandomNumber(2);
        Log.v( "random num", Integer.toString(newRand));
        return newRand;
    }
    public int getWrongAnswer(int correct) {
        int newRand = getRandomNumber(getAnswers().length);
        while (newRand == correct) {
            newRand = getRandomNumber(getAnswers().length);
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

    public void populateFirstQuestionAndAnswer(View view) {
        global = getRandomNumber(getQuestions().length);
        int correctAnswerNumber = getRightAnswer();
        if (correctAnswerNumber == 1) {
            populate(R.id.answer_text_1, getAnswers(), global);
            populate(R.id.answer_text_2, getAnswers(), getWrongAnswer(global));
        } else {
            populate(R.id.answer_text_2, getAnswers(), global);
            populate(R.id.answer_text_1, getAnswers(), getWrongAnswer(global));
        }

        populate(R.id.question_text, getQuestions(), global);
        displayScore(R.id.current_score_number, userScore);
        displayScore(R.id.highest_possible_score_number, attempts);
    }
    public void populateNewQuestionAndAnswer() {
        global = getRandomNumber(getQuestions().length);
        int correctAnswerNumber = getRightAnswer();
        if (correctAnswerNumber == 1) {
            populate(R.id.answer_text_1, getAnswers(), global);
            populate(R.id.answer_text_2, getAnswers(), getWrongAnswer(global));
        } else {
            populate(R.id.answer_text_2, getAnswers(), global);
            populate(R.id.answer_text_1, getAnswers(), getWrongAnswer(global));
        }

        populate(R.id.question_text, getQuestions(), global);
        displayScore(R.id.current_score_number, userScore);
        displayScore(R.id.highest_possible_score_number, attempts);
    }
    public void answerWasCorrect(final View view) {
        view.setBackgroundResource(R.color.colorAccent);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundResource(R.color.white);
                userScore++;
                attempts++;
                populateNewQuestionAndAnswer();
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
                populateNewQuestionAndAnswer();
            }
        }, 500);
    }
    public void checkIfCorrect(View view) {
        String input = ((TextView)view).getText().toString();
        String answer = getAnswers()[global];
        Log.v("Answers: ", input);
        Log.v("Actual: ", answer);
        if (((TextView)view).getText().toString().equals(getAnswers()[global])) {
            answerWasCorrect(view);
        } else {
            answerWasIncorrect(view);
        }
    }
    public void resetScore(View view){
        userScore = 0;
        attempts = 0;
        populateNewQuestionAndAnswer();
    }
}
