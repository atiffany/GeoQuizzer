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

    public int getRandomNumber(String[] arr) {
        int newRandom = new Random().nextInt(arr.length);
        return newRandom;
    }
    public int getWrongAnswer(int correct){
        int newRand = getRandomNumber(getAnswers());
        while (newRand == correct) {
            newRand = getRandomNumber(getAnswers());
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
        global = getRandomNumber(getQuestions());

        populate(R.id.question_text, getQuestions(), global);
        populate(R.id.answer_correct_text, getAnswers(), global);
        populate(R.id.answer_incorrect_text, getAnswers(), getWrongAnswer(global));
        displayScore(R.id.current_score_number, userScore);
        displayScore(R.id.highest_possible_score_number, attempts);
    }
    public void populateNewQuestionAndAnswer() {
        int global = getRandomNumber(getQuestions());

        populate(R.id.question_text, getQuestions(), global);
        populate(R.id.answer_correct_text, getAnswers(), global);
        populate(R.id.answer_incorrect_text, getAnswers(),getWrongAnswer(global));
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
}
