package com.sheygam.java_221_31_10_18_cw;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn,stopBtn;
    private ProgressBar myProgress;
    private TextView resultTxt;
    private MyTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.start_btn);
        myProgress = findViewById(R.id.my_progress);
        resultTxt = findViewById(R.id.result_txt);
        stopBtn = findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        stopBtn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_btn){
//            myProgress.setVisibility(View.VISIBLE);
            task = new MyTask();
            task.execute(10);
        }else if(v.getId() == R.id.stop_btn){
            task.cancel(true);
        }
    }

    class MyTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            myProgress.setVisibility(View.VISIBLE);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            resultTxt.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... params) {
            if(params.length > 0){
                for (int i = 0; i < params[0]; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(isCancelled()){
                        break;
                    }
                    Log.d("MY_TAG", "doInBackground: " + i);
                    publishProgress(i);

                }
            }
            return "All done";
        }

        @Override
        protected void onPostExecute(String str) {
            myProgress.setVisibility(View.INVISIBLE);
            startBtn.setEnabled(true);
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            stopBtn.setEnabled(false);
        }

        @Override
        protected void onCancelled(String s) {
            myProgress.setVisibility(View.INVISIBLE);
            startBtn.setEnabled(true);
            Toast.makeText(MainActivity.this, s , Toast.LENGTH_SHORT).show();
            resultTxt.setText("Was canceled");
            stopBtn.setEnabled(false);
        }
    }
}
