package com.se.aychan.ilovezappos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingCartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartAdapter.onCartItemClickedListener{

    private final String TAG = getClass().getSimpleName();
    //RecyclerView Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Views
    private TextView totalCost;

    private OnFragmentInteractionListener mListener;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    public static ShoppingCartFragment newInstance() {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        Product[] products = ShoppingCartSingleton.getInstance().getAllFromCart();
        mAdapter = new ShoppingCartAdapter(products, this);
        mRecyclerView.setAdapter(mAdapter);
        totalCost = (TextView)view.findViewById(R.id.totalCost);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        double totalVal = Math.round(ShoppingCartSingleton.getInstance().getTotalCost()*100.00)/100.00;
        String result = "$" + totalVal;
        totalCost.setText(result);
    }

    @Override
    public void onCartItemInteraction(Product product) {
        setUserVisibleHint(true);
        mListener.onCartFragmentInteraction(product);
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
    public interface OnFragmentInteractionListener {
        void onCartFragmentInteraction(Product product);
    }
}
