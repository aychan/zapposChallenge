package com.se.aychan.ilovezappos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by aychan on 2/7/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Product[] mDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        //product fragment layout aspects
        private TextView productName;
        private TextView brandName;
        private TextView price;
        private TextView originalPrice;
        private TextView percentOff;
        private ImageView productImage;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            this.productName = (TextView) view.findViewById(R.id.product_name);
            this.brandName = (TextView) view.findViewById(R.id.product_brand);
            this.price = (TextView) view.findViewById(R.id.product_price);
            this.originalPrice = (TextView) view.findViewById(R.id.product_price_original);
            this.percentOff = (TextView) view.findViewById(R.id.product_percent_off);
            this.productImage = (ImageView) view.findViewById(R.id.product_img);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(Product[] myDataset) {
        mDataSet = myDataset;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        holder.productName.setText(mDataSet[position].getProductName());
        holder.brandName.setText(mDataSet[position].getBrandName());
        holder.price.setText(mDataSet[position].getPrice());
        holder.originalPrice.setText(mDataSet[position].getOriginalPrice());
        holder.percentOff.setText(mDataSet[position].getPercentOff());
        new DownloadImageTask(holder.productImage).execute(mDataSet[position].getThumbnailImageUrl());
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
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
