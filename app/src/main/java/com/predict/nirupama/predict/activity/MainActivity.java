package com.predict.nirupama.predict.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.predict.nirupama.predict.R;
import com.predict.nirupama.predict.adapter.CustomSpinnerAdapter;
import com.predict.nirupama.predict.apis.PredictApiServices;
import com.predict.nirupama.predict.model.PredictAPIResponseModel;
import com.predict.nirupama.predict.model.ResultIntentModel;
import com.predict.nirupama.predict.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PredictApiServices.PredictAPICallBack, Utils.SnackBarCallBacks {

    private static final int RETRY_PREDICT = 100;
    public static PinpointManager pinpointManager;
    FrameLayout mRootLayout;
    ProgressDialog mProgressDialog;

    public class Route {
        int image;
        String id;

        public Route(int image, String id) {
            this.image = image;
            this.id = id;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Spinner mSpinner;
    private Map<Integer, Route> routeMap = new HashMap<>();
    private Route selectedRoute;
    private Button mPredictButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("MainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        setupUI();
    }

    private void setupUI() {
        mRootLayout = findViewById(R.id.main_content);
        mSpinner = findViewById(R.id.spinnerTrains);
        mSpinner.setAdapter(new CustomSpinnerAdapter(this, getMap()));
        selectedRoute = routeMap.get(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = routeMap.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPredictButton = findViewById(R.id.predict_button);
        mPredictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPredictApi();
            }
        });
    }

    private void callPredictApi() {
        AWSMobileClient.getInstance().initialize(this).execute();

        PinpointConfiguration config = new PinpointConfiguration(
                MainActivity.this,
                AWSMobileClient.getInstance().getCredentialsProvider(),
                AWSMobileClient.getInstance().getConfiguration()
        );
        pinpointManager = new PinpointManager(config);
        pinpointManager.getSessionClient().startSession();
        pinpointManager.getAnalyticsClient().submitEvents();
        mProgressDialog = Utils.createProgressDialog(this);
        mProgressDialog.show();
        PredictApiServices predictApiServices = new PredictApiServices(this, this);
        predictApiServices.getPrediction(selectedRoute.id);
    }


    @Override
    public void onPredictSuccess(PredictAPIResponseModel responseBody) {
        mProgressDialog.dismiss();
        Intent intent = new Intent(this, PredictResultActivity.class);
        ResultIntentModel model = new ResultIntentModel();
        model.setImage(selectedRoute.image);
        model.setModel(responseBody);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", model);
        intent.putExtras(bundle);

        startActivity(intent);
        pinpointManager.getSessionClient().stopSession();
    }


    @Override
    public void onActionClicked(int requestCode) {
        if (requestCode == RETRY_PREDICT) {
            callPredictApi();
        }
    }


    @Override
    public void onPredictFailure(String message) {
        mProgressDialog.dismiss();
        Utils.showSnackBarWithAction(mRootLayout, "Something went wrong", "Try Again", this, RETRY_PREDICT);
    }


    private Map<Integer, Route> getMap() {
        String route1Id = "1";
        String route2Id = "2";
        String route3Id = "3";
        String route4Id = "4";
        String route5Id = "5";
        String route6Id = "6";
        String route7Id = "7";
        String routeAId = "A";
        String routeBId = "B";
        String routeCId = "C";
        String routeDId = "D";
        String routeEId = "E";
        String routeFId = "F";
        String routeGId = "G";
        String routeLId = "L";
        String routeMId = "M";
        String routeNId = "N";
        String routeQId = "Q";
        String routeRId = "R";
        String routeWId = "W";

        routeMap.put(0, new Route(R.drawable.route_1, route1Id));
        routeMap.put(1, new Route(R.drawable.route_2, route2Id));
        routeMap.put(2, new Route(R.drawable.route_3, route3Id));
        routeMap.put(3, new Route(R.drawable.route_4, route4Id));
        routeMap.put(4, new Route(R.drawable.route_5, route5Id));
        routeMap.put(5, new Route(R.drawable.route_6, route6Id));
        routeMap.put(6, new Route(R.drawable.route_7, route7Id));
        routeMap.put(7, new Route(R.drawable.route_a, routeAId));
        routeMap.put(8, new Route(R.drawable.route_b, routeBId));
        routeMap.put(9, new Route(R.drawable.route_c, routeCId));
        routeMap.put(10, new Route(R.drawable.route_d, routeDId));
        routeMap.put(11, new Route(R.drawable.route_e, routeEId));
        routeMap.put(12, new Route(R.drawable.route_f, routeFId));
        routeMap.put(13, new Route(R.drawable.route_g, routeGId));
        routeMap.put(14, new Route(R.drawable.route_l, routeLId));
        routeMap.put(15, new Route(R.drawable.route_m, routeMId));
        routeMap.put(16, new Route(R.drawable.route_n, routeNId));
        routeMap.put(17, new Route(R.drawable.route_q, routeQId));
        routeMap.put(18, new Route(R.drawable.route_r, routeRId));
        routeMap.put(19, new Route(R.drawable.route_w, routeWId));

        return routeMap;
    }
}
