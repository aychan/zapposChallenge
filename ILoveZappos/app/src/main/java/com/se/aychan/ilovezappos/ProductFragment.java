package com.se.aychan.ilovezappos;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.se.aychan.ilovezappos.databinding.FragmentProductBinding;

import java.io.InputStream;
import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnProductFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment implements Serializable{
    protected final String TAG = getClass().getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private Product product;


    private OnProductFragmentInteractionListener mListener;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param product Parameter 1.
     * @return A new instance of fragment ProductFragment.
     */
    public static ProductFragment newInstance(Product product) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, product);

        fragment.setArguments(args);
//        this.product = product;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Product mParam1 = getArguments().getParcelable(ARG_PARAM1);
            Log.d(TAG, "Product: " + mParam1.getProductName());
            this.product = mParam1;
        }else{
            Log.d(TAG, "Arguments are null");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View root = inflater.inflate(R.layout.fragment_product, container, false);
        // No longer using given inflater, will be using data binding with this fragment
        FragmentProductBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product,container,false);
        // TODO: 2/7/17 design fragment UI
        View root = binding.getRoot();
        initializeViews(root);
        binding.setProduct(product);
        return root;
    }

    private void initializeViews(View root) {
        ImageView productImage = (ImageView) root.findViewById(R.id.product_img);
        new DownloadImageTask(productImage).execute(product.getThumbnailImageUrl());
//        TextView productBrand = (TextView)root.findViewById(R.id.product_brand);
//        TextView productName = (TextView) root.findViewById(R.id.product_name);
//        TextView productPrice = (TextView) root.findViewById(R.id.product_price);
//        TextView productPriceOriginal = (TextView) root.findViewById(R.id.product_price_original);
//
//        productBrand.setText(Html.fromHtml(product.getBrandName()));
//        productName.setText(Html.fromHtml(product.getProductName()));
//        productPrice.setText(product.getPrice());
//        productPriceOriginal.setText(product.getOriginalPrice());

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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProductFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductFragmentInteractionListener) {
            mListener = (OnProductFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductFragmentInteractionListener {
        void onProductFragmentInteraction(Uri uri);
    }
}
