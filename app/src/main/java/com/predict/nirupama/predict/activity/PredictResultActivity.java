package com.predict.nirupama.predict.activity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.predict.nirupama.predict.R;
import com.predict.nirupama.predict.model.PredictAPIResponseModel;
import com.predict.nirupama.predict.model.ResultIntentModel;
import com.predict.nirupama.predict.util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PredictResultActivity extends AppCompatActivity {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    LinearLayout mRootLayout;
    ResultIntentModel mModel;
    TextView day1TextView, day2TextView, day3TextView, day4TextView, day5TextView;
    TextView day1DelayTextView, day2DelayTextView, day3DelayTextView, day4DelayTextView, day5DelayTextView;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_result);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mRootLayout = findViewById(R.id.resultRoot);
        if(getIntent().hasExtra("key")){
            mModel = (ResultIntentModel) getIntent().getSerializableExtra("key");
        }
        else{
            Utils.showSnackBar(mRootLayout, "Something Went Wrong");
            goBackAfterOneSecond();
        }
        setupUI();
    }

    private void setupUI() {
        mImageView = findViewById(R.id.result_image);
        day1TextView = findViewById(R.id.textView_day1);
        day2TextView = findViewById(R.id.textView_day2);
        day3TextView = findViewById(R.id.textView_day3);
        day4TextView = findViewById(R.id.textView_day4);
        day5TextView = findViewById(R.id.textView_day5);

        day1DelayTextView = findViewById(R.id.textView_day1_delay);
        day2DelayTextView = findViewById(R.id.textView_day2_delay);
        day3DelayTextView = findViewById(R.id.textView_day3_delay);
        day4DelayTextView = findViewById(R.id.textView_day4_delay);
        day5DelayTextView = findViewById(R.id.textView_day5_delay);

        LinkedHashMap<String, String> resultMap = (LinkedHashMap<String, String>) getResult(mModel.getModel().getDelays());
        String [] keyArray = resultMap.keySet().toArray(new String[resultMap.keySet().size()]);
        String [] valueArray = resultMap.values().toArray((new String[resultMap.values().size()]));
        mImageView.setImageResource(mModel.getImage());
        day1TextView.setText(keyArray[0]);
        day2TextView.setText(keyArray[1]);
        day3TextView.setText(keyArray[2]);
        day4TextView.setText(keyArray[3]);
        day5TextView.setText(keyArray[4]);

        day1DelayTextView.setText(valueArray[0]);
        day2DelayTextView.setText(valueArray[1]);
        day3DelayTextView.setText(valueArray[2]);
        day4DelayTextView.setText(valueArray[3]);
        day5DelayTextView.setText(valueArray[4]);
    }

    private Map<String, String> getResult(List<String> delays) {
        Map<String, String> delayDates = new LinkedHashMap<>();
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        for(int i=1;i<6;i++) {
            c.add(Calendar.DATE, 1);
            Date currentDatePlusOne = c.getTime();
            delayDates.put(DATE_FORMAT.format(currentDatePlusOne), delays.get(i-1));
        }
        return delayDates;
    }

    private void goBackAfterOneSecond() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, 1000);
    }
    
}
