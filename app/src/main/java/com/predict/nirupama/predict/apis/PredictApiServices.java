package com.predict.nirupama.predict.apis;

import android.content.Context;
import android.util.Log;

import com.predict.nirupama.predict.manager.NetworkManager;
import com.predict.nirupama.predict.model.PredictAPIResponseModel;
import com.predict.nirupama.predict.util.Constant;

import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class PredictApiServices {

    Context mContext;
    WeakReference<PredictAPICallBack> predictAPICallBackWeakReference;
    private static final String TAG = "PredictApiServices";
    public PredictApiServices(PredictAPICallBack callBack, Context context){
        predictAPICallBackWeakReference = new WeakReference<PredictAPICallBack>(callBack);
        mContext = context;
    }

    public void getPrediction(String lineId){
        String URL = Constant.END_POINT;
        PredictServices predictServices = NetworkManager.getInstance().createService(PredictServices.class, URL);
        Call<PredictAPIResponseModel> responseBodyCall = predictServices.getPrediction("?line=" + lineId);
        Log.d(TAG, "URL: " + URL);
        responseBodyCall.enqueue(new Callback<PredictAPIResponseModel>() {
            @Override
            public void onResponse(Call<PredictAPIResponseModel> call, Response<PredictAPIResponseModel> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "Response:" + response.toString());
                    predictAPICallBackWeakReference.get().onPredictSuccess(response.body());
                }
                else{
                    predictAPICallBackWeakReference.get().onPredictFailure("Failed call in response");
                }
            }

            @Override
            public void onFailure(Call<PredictAPIResponseModel> call, Throwable t) {
                Log.d(TAG, "Failed:", t);
                predictAPICallBackWeakReference.get().onPredictFailure("Failed call");
            }
        });
    }

    public interface PredictAPICallBack{
        void onPredictSuccess(PredictAPIResponseModel responseBody);
        void onPredictFailure(String message);
    }

    public interface PredictServices{
        @GET
        Call<PredictAPIResponseModel> getPrediction(@Url String URL);
    }
}
