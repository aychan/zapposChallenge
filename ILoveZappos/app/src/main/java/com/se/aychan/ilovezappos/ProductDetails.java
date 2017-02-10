package com.se.aychan.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.se.aychan.ilovezappos.databinding.ActivityProductDetailsBinding;

import java.io.InputStream;

public class ProductDetails extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;
    private String TAG = getClass().getSimpleName();
    // Views
    private ImageView imageView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/9/17 pretty sure these product details are correct, but the ones in recyclerview are now for some reason
        super.onCreate(savedInstanceState);
        final Product product = getIntent().getParcelableExtra("product");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        binding.setProduct(product);
        imageView = (ImageView) findViewById(R.id.product_img);
        fab = (FloatingActionButton) findViewById(R.id.productFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartSingleton.getInstance().addToCart(product);
                Snackbar snackbar = Snackbar.make(v, "Added to Shopping Cart!",Snackbar.LENGTH_SHORT);
                snackbar.show();
                Log.d(TAG, "count: "+ ShoppingCartSingleton.getInstance().getCartCount());
                // TODO: 2/9/17 notify ShoppingCartFragment that there was addition

            }
        });
        new DownloadImageTask(imageView).execute(product.getThumbnailImageUrl());
        //setContentView(R.layout.activity_product_details);
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

}
