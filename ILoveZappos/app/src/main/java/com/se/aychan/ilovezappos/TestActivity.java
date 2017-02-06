package com.se.aychan.ilovezappos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private EditText searchET;
    private TextView resultTV;
    private Button searchBtn;

    //Retrofit Variables
    private ZapposService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        searchET = (EditText)findViewById(R.id.searchET);
        resultTV = (TextView)findViewById(R.id.resultTV);
        searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SearchQuery> result = service.listSearch(searchET.getText().toString(),KEY);
                result.enqueue(new Callback<SearchQuery>() {
                    @Override
                    public void onResponse(Call<SearchQuery> call, Response<SearchQuery> response) {
                        Log.d(TAG, String.valueOf(response.raw()));
                        Product[] array = response.body().getResults();
                        Product temp = array[0];
                        Log.d(TAG, temp.getProductName() + " " + temp.getBrandName());
                    }

                    @Override
                    public void onFailure(Call<SearchQuery> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
                resultTV.setText("helo!");
            }
        });
        createRetrofit();
    }

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
}
