package com.se.aychan.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.se.aychan.ilovezappos.databinding.ActivityProductDetailsBinding;

import java.io.InputStream;

public class ProductDetails extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;
    private String TAG = getClass().getSimpleName();
    // Views
    private ImageView imageView;
    private FloatingActionButton fab;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/9/17 pretty sure these product details are correct, but the ones in recyclerview are now for some reason
        super.onCreate(savedInstanceState);

        final Product product = getIntent().getParcelableExtra("product");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        binding.setProduct(product);

        imageView = (ImageView) findViewById(R.id.product_img);

        spinner = (Spinner)findViewById(R.id.spinner);
        String[] array = new String[]{"1", "2", "3", "4", "5","6","7","8","9","10","10+"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array);
        spinner.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.productFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartSingleton.getInstance().addToCart(product);
                fab.hide();
                new AnimatePurchase(fab,v).execute();

            }
        });
        new DownloadImageTask(imageView).execute(product.getThumbnailImageUrl());
        //setContentView(R.layout.activity_product_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

    }

    /*
     AsyncTask which loads URL from online onto ImageView
  */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
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
    /*
     AsyncTask which animates fab along with snackbar
  */
    private class AnimatePurchase extends AsyncTask<String, Void, String> {
        FloatingActionButton fab;
        View v;
        AnimatePurchase(FloatingActionButton fab, View v) {
            this.fab = fab;
            this.v = v;
        }

        protected String doInBackground(String... urls) {


            try {
                Snackbar snackbar = Snackbar.make(v, "Added to Shopping Cart!",Snackbar.LENGTH_SHORT);
                snackbar.show();
                Thread.sleep(2000L);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            fab.show();
        }
    }

}
