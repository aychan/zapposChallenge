package com.se.aychan.ilovezappos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
public class TestActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, ShoppingCartFragment.OnFragmentInteractionListener{
    protected final String TAG = this.getClass().getSimpleName();
    protected String KEY = "b743e26728e16b81da139182bb2094357c31d331";

    //Views
//    private TextView resultTV;
//    private ImageView productImg;
    private ProgressBar progressBar;
    private android.support.v7.widget.SearchView searchView;
    private FrameLayout productFragmentLayout;

    //Fragments
    Fragment productFragment;

    //Retrofit Variables
    private ZapposService service;

    //RecyclerView Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // View Pager Variables
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        assert toolbar != null;
//        toolbar.addView(new EditText(getApplicationContext()));
//        setSupportActionBar(toolbar);
        initializeViews();
        createRetrofit();

//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        // TODO: 2/7/17 do i make the adapter here? or wait until user input
        //mAdapter = new ProductAdapter(myDataset);
        //mRecyclerView.setAdapter(mAdapter);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);



    }


    /*
    link View variables to Layout Views
     */
    void initializeViews(){
        //productFragmentLayout = (FrameLayout)findViewById(R.id.productFragmentLayout);
        searchView = (android.support.v7.widget.SearchView)findViewById(R.id.SearchView);

//        resultTV = (TextView)findViewById(R.id.resultTV);
//        productImg = (ImageView)findViewById(R.id.productIV);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        assert progressBar != null;
        progressBar.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                final Call<SearchQuery> result = service.listSearch(query,KEY);
                result.enqueue(new Callback<SearchQuery>() {
                    @Override
                    public void onResponse(Call<SearchQuery> call, Response<SearchQuery> response) {
                        Log.d(TAG, String.valueOf(response.raw()));
                        if(response.isSuccessful()){
                            Product[] array = response.body().getResults();

                            //FragmentManager fragmentManager = getSupportFragmentManager();
                            //FragmentTransaction fragmentTransation = fragmentManager.beginTransaction();
                            /*
                                When first search, productFragent will be null and will then be created
                                Once the user updates the search, fragment will be replaced!
                                todo recyclerView, or CardView!
                             */
                            if(array.length == 0){
                                Toast.makeText(TestActivity.this, "No Products Match Your Search :(", Toast.LENGTH_SHORT).show();
                            }else {
                                /*
                                    Clear the Singleton, add new data for Fragment use
                                 */
                                SearchSingleton.getInstance().clearQuery();
                                SearchSingleton.getInstance().addAll(array);
                                mSectionsPagerAdapter.notifyDataSetChanged();


//                                if (productFragment != null) {
//                                    fragmentTransation.remove(productFragment);
//                                    productFragment = ProductFragment.newInstance(array[0]);
//                                    fragmentTransation.replace(R.id.productFragmentLayout, productFragment);
//                                } else {
//                                    productFragment = ProductFragment.newInstance(array[0]);
//                                    fragmentTransation.add(productFragmentLayout.getId(), productFragment, "productFragment");
//                                }
//                                fragmentTransation.commit();
                                // TODO: 2/7/17 there are multiple items which are same but just have different color, find way to group them together
//                                mAdapter = new ProductAdapter(array);
//                                mRecyclerView.setAdapter(mAdapter);
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            Log.d(TAG, "Inefficient Query");
                        }

                    }
                    @Override
                    public void onFailure(Call<SearchQuery> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
    /*
    Given one product, display onto screen
     */
    void displayProduct(Product product) {
        Log.d(TAG, product.getBrandName() + " " + product.getProductName() + " " + product.getProductUrl() + " " + product.getThumbnailImageUrl());
        String productName = null;
        try {
            productName = URLDecoder.decode(product.getBrandName() + " " + product.getProductName(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        resultTV.setText(Html.fromHtml(productName));
//        //Set thumbnail image onto display
//        new DownloadImageTask(productImg).execute(product.getThumbnailImageUrl());

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

    @Override
    public void onSearchFragmentInteraction(Product product) {
        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("product",product);
        // TODO: 2/9/17 transition animation
        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(TestActivity.this, Pair.create(this,"selectProduct")).toBundle();
        startActivity(intent);
    }

    @Override
    public void onCartFragmentInteraction(Product product) {
        Log.d(TAG,"Updating Shopping Cart");
        mSectionsPagerAdapter.notifyDataSetChanged();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = new Fragment();
            switch (position){
                case 0:
                    fragment = SearchFragment.newInstance();
//                     notifyDataSetChanged();
                    break;

                case 1:
                    fragment = ShoppingCartFragment.newInstance();
                    break;
                
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            //return super.getItemPosition(object);
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Search";
                case 1:
                    return "Cart";
                
            }
            return null;
        }

    }

    @Override
    protected void onPostResume() {
        mSectionsPagerAdapter.notifyDataSetChanged();
        super.onPostResume();
    }
}
