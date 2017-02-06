package com.se.aychan.ilovezappos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
    TestActivity to test the GET & POST of the Square Retrofit ReST Software

 */
public class TestActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected String KEY = "b743e26728e16b81da139182bb2094357c31d331";

    //Views
    private EditText searchET;
    private TextView resultTV;
    private ImageView productImg;

    //Retrofit Variables
    private ZapposService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initializeViews();
        createRetrofit();
    }
    /*
    link View variables to Layout Views
     */
    void initializeViews(){
        searchET = (EditText)findViewById(R.id.searchET);
        resultTV = (TextView)findViewById(R.id.resultTV);
        productImg = (ImageView)findViewById(R.id.productIV);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        assert searchBtn != null;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Call<SearchQuery> result = service.listSearch(searchET.getText().toString(),KEY);
                result.enqueue(new Callback<SearchQuery>() {
                    @Override
                    public void onResponse(Call<SearchQuery> call, Response<SearchQuery> response) {
                        Log.d(TAG, String.valueOf(response.raw()));
                        if(response.isSuccessful()){
                            // TODO: 2/5/17 Spin Animation
                            Product[] array = response.body().getResults();
                            displayProduct(array[0]);
                        }else{
                            Log.d(TAG, "Inefficient Query");
                        }

                    }
                    @Override
                    public void onFailure(Call<SearchQuery> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
            }
        });
    }
    /*
    Given one product, display onto screen
     */
    void displayProduct(Product product) {
        // TODO: 2/5/17 Async Activity to first show spinner
        Log.d(TAG, product.getBrandName() + " " + product.getProductName() + " " + product.getProductUrl() + " " + product.getThumbnailImageUrl());
        String productName = null;
        try {
            productName = URLDecoder.decode(product.getBrandName() + " " + product.getProductName(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resultTV.setText(Html.fromHtml(productName));
        //Set thumbnail image onto display
        new DownloadImageTask(productImg).execute(product.getThumbnailImageUrl());

    }
    /*
    Build retrofit2 Object
     */
    void createRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.zappos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ZapposService.class);

    }
    /*
    Interface which is used to obtain values from API
     */
    public interface ZapposService{
        @GET("Search")
        Call<SearchQuery> listSearch(@Query("term") String term, @Query("key") String key);
        //       @GET("Search?term=nike&key=b743e26728e16b81da139182bb2094357c31d331")
        //        Call<SearchQuery> listSearch();
    }

    /*
       AsyncTask which loads URL from online onto ImageView
    */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
