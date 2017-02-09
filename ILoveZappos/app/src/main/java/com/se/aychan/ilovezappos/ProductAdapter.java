package com.se.aychan.ilovezappos;

import android.databinding.DataBindingUtil;
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

import com.se.aychan.ilovezappos.databinding.FragmentProductBinding;

import java.io.InputStream;

/**
 * Created by aychan on 2/7/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Product[] mDataSet;

    // Databinding
    private FragmentProductBinding binding;

    // Interface
    public onProductClickedListener mListener;

    // Provides reference to the views for each Product
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        //product fragment layout aspects
        private TextView productName;
        private TextView brandName;
        private TextView price;
        private TextView originalPrice;
        private TextView percentOff;
        private ImageView productImage;

        public ViewHolder(final View view) {
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
    public ProductAdapter(Product[] myDataset, onProductClickedListener listener) {
        mDataSet = myDataset;
        mListener = listener;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product, parent, false);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_product,parent,false);
        View root = binding.getRoot();
        return new ViewHolder(root);
    }



    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, final int position) {
        // TODO: 2/8/17 holder.bind(mDataSet[position]); <-- the real databinding method, except for the image
        binding.setProduct(mDataSet[holder.getAdapterPosition()]);
//        holder.productName.setText(mDataSet[position].getProductName());
//        holder.brandName.setText(mDataSet[position].getBrandName());
//        holder.price.setText(mDataSet[position].getPrice());
//        holder.originalPrice.setText(mDataSet[position].getOriginalPrice());
//        holder.percentOff.setText(mDataSet[position].getPercentOff());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Product Adapter", mDataSet[holder.getAdapterPosition()].getProductName());
                Product product = mDataSet[holder.getAdapterPosition()];
                if(product != null){
                    mListener.onProductInteraction(product);
                }else{
                    Log.d("Product Adapter", product.getProductName());
                }

            }
        });
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



    interface onProductClickedListener{
        void onProductInteraction(Product product);
    }
}
