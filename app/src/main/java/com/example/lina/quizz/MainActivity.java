package com.example.lina.quizz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int counterRight = 0;
    int[] question1 = {R.id.Q1_1, R.id.Q1_2, R.id.Q1_3, R.id.Q1_4 };
    int[] question3 = {R.id.Q3_1, R.id.Q3_2, R.id.Q3_3, R.id.Q3_4 };
    int[] question4 = {R.id.Q4_1, R.id.Q4_2, R.id.Q4_3, R.id.Q4_4 };
    int[] question7 = {R.id.Q7_1, R.id.Q7_2, R.id.Q7_3, R.id.Q7_4, R.id.Q7_5, R.id.Q7_6, R.id.Q7_7, R.id.Q7_8};
    int[] otherQuestions = {R.id.Q2_1, R.id.Q5_1,R.id.Q6_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when Submit button is clicked
     */
    public void submitQuiz(View view)
    {
        String message;
        String checker = QuestionChecker();
        if(counterRight>6){
            message = getString(R.string.congratulation)+"\n";
            message += getString(R.string.earnings)+counterRight+getString(R.string.outOfPoints)+"\n";
            showToast(counterRight,message);
            composeEmail(message);
            counterRight = 0;
        }

        else {
            message =getString(R.string.earnings)+counterRight+getString(R.string.outOfPoints)+"\n";
            message+=getString(R.string.answerCorrectly)+"\n"+checker;
            showToast(counterRight,message);
            composeEmail(message);
            counterRight = 0;
        }
    }

    /**
     * This method show toast
     */
    public void showToast(int answers, String message)
    {
        String string = "";
        string += answers+"/7 points!";
        Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG);
        toast.show();
        composeEmail(message);
    }

    /**
     * This method send an intense to email
     */
    public void composeEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when Reset button is clicked
     */
    public void reset (View view)
    {
        counterRight = 0;
        for (int i = 0; i < question1.length; i++) {
            CheckBox checkbox = (CheckBox) findViewById(question1[i]);
            checkbox.setChecked(false);
        }

        for (int i = 0; i < question4.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(question3[i]);
            radioButton.setChecked(false);
        }

        for (int i = 0; i < question4.length; i++) {
            CheckBox checkbox = (CheckBox) findViewById(question4[i]);
            checkbox.setChecked(false);
        }

        for (int i = 0; i < question7.length; i++) {
            CheckBox checkbox = (CheckBox) findViewById(question7[i]);
            checkbox.setChecked(false);
        }

        for (int i = 0; i < otherQuestions.length; i++ ){
            TextView answer = (TextView) findViewById(otherQuestions[i]);
            answer.setText(null);
        }
    }


    /**
     * Checker for multiple answers questions
     */
    private String QuestionChecker() {
        String answers = "";
        answers += multipleQuestionChecker(4, 1 );
        answers += singleQuestionChecker(2);
        answers += multipleQuestionChecker(4, 3 );
        answers += multipleQuestionChecker(4, 4 );
        answers += singleQuestionChecker(5);
        answers += singleQuestionChecker(6);
        answers += multipleQuestionChecker(8, 7 );
       return answers;
    }

    /**
     * Checker for multiple answers questions
     * @param numOfAnswers - number of answers in the question
     * @param questionNum - question number
     */
    private String multipleQuestionChecker(int numOfAnswers, int questionNum){
        String feedback = "";
        int [] answers = new int[numOfAnswers];
        for (int i = 0; i < numOfAnswers; i++ ) {
            switch (questionNum){
                case 1: answers[i] = checkedStateQ1(i);
                    break;
                case 3: answers[i] = radioButtonClicked(i);
                    break;
                case 4: answers[i] = checkedStateQ4(i);
                    break;
                case 7: answers[i] = checkedStateQ7(i);
                    break;
            }
        }

        switch (questionNum){
            case 1:
                if((answers[1] ==0 && answers[3] ==0) && (answers[0] ==1 && answers[2] ==1) ){counterRight+=1;}
                else { feedback="Q1: Java, Linux\n";}
                break;
            case 3:
                if((answers[0] == 1 && answers[1] ==0) &&( answers[2] == 0 && answers[3] ==0)){counterRight+=1;}
                else { feedback="Q3: 2008\n";}
                break;
            case 4:
                if(answers[0] == 1 && answers[1] ==1 && answers[2] ==1 && answers[3] ==1){counterRight+=1;}
                else { feedback="Q4: "+getString(R.string.rightAnswer_Q4)+"\n";}
                break;
            case 7:
                if((answers[6] == 1 && answers[7] == 1)&&(answers[0] ==0&& answers[1] ==0 && answers[2] ==0 && answers[3] ==0 && answers[4] ==0 && answers[5]==0)){counterRight+=1;}
                else { feedback="Q7: "+getString(R.string.rightAnswer_Q7)+"\n";}
                break;
        }
        return feedback;

    }

    /**
     * This methods checked a state of checkBox
     * @param answerNum - answer number
     * @return 1 or 0
     */
    private int checkedStateQ1 (int answerNum){
        int i;
        CheckBox answer = (CheckBox)findViewById(question1[answerNum]);
        i = answer.isChecked()? 1: 0;
        return i;
    }
    private int checkedStateQ4 (int answerNum){
        int i = 0;
        CheckBox answer = (CheckBox)findViewById(question4[answerNum]);
        i = answer.isChecked()? 1: 0;
        return i;
    }
    private int checkedStateQ7 (int answerNum){
        int i = 0;
        CheckBox answer = (CheckBox)findViewById(question7[answerNum]);
        i = answer.isChecked()? 1: 0;
        return i;
    }
    private int radioButtonClicked(int answerNum) {
        int i =0;
        RadioButton answer = (RadioButton)findViewById(question3[answerNum]);
        i = answer.isChecked()?1:0;
        return i;
    }


    private String singleQuestionChecker(int questionNum){
        String s="";
        String feedback ="";
        TextView q;
        switch (questionNum){
            case 2:
                q = (TextView) findViewById(otherQuestions[0]);
                s = q.getText().toString();
                if (s.equalsIgnoreCase("Eclipse")){counterRight +=1;}
                else{feedback = "Q2: Eclipse\n";}
                break;
            case 5:
                q = (TextView) findViewById(otherQuestions[1]);
                s = q.getText().toString();
                if (s.equalsIgnoreCase(getString(R.string.rightAnswer_Q5))){counterRight +=1;}
                else{feedback= "Q5: " + getString(R.string.rightAnswer_Q5)+"\n";}
                break;
            case 6:
                q = (TextView) findViewById(otherQuestions[2]);
                s = q.getText().toString();
                if (s.equalsIgnoreCase(getString(R.string.rightAnswer_Q6))){counterRight +=1;}
                else{feedback = "Q6: "+getString(R.string.rightAnswer_Q6)+"\n";}
                break;
        }
        return feedback;
    }

}



