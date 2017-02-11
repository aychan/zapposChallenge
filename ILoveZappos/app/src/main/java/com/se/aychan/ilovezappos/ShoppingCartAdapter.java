package com.se.aychan.ilovezappos;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.se.aychan.ilovezappos.databinding.ListShoppingCartItemsBinding;

import java.io.InputStream;

/**
 * Created by aychan on 2/9/17.
 * Adapter's primary usage is for the RecyclerView and Binding data to Views
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private Product[] mDataSet;

    // Databinding
    ListShoppingCartItemsBinding binding;

    //interface
    private onCartItemClickedListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private View view;
        public ViewHolder(final View view) {
            super(view);
            this.view = view;
            this.productImage = (ImageView) view.findViewById(R.id.product_img);
        }
    }
    
    public ShoppingCartAdapter(Product[] mDataSet, onCartItemClickedListener mListener){
        this.mDataSet = mDataSet;
        this.mListener = mListener;
    }
    
    
    
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.list_shopping_cart_items,parent,false);
        View root = binding.getRoot();
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartAdapter.ViewHolder holder, final int position) {
        binding.setProduct(mDataSet[holder.getAdapterPosition()]);
        new ShoppingCartAdapter.DownloadImageTask(holder.productImage).execute(mDataSet[position].getThumbnailImageUrl());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(holder.view.getContext())
                        .setTitle("Delete Item")
                        .setMessage("Delete "+mDataSet[position].getProductName()+" from your shopping cart?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                ShoppingCartSingleton.getInstance().removeFromCart(mDataSet[position]);
                                mListener.onCartItemInteraction(mDataSet[position]);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });
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


    interface onCartItemClickedListener{
        void onCartItemInteraction(Product product);
    }
}
