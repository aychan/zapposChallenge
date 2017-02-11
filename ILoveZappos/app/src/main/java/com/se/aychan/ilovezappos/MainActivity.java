package com.se.aychan.ilovezappos;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
    MainActivity (Originally TestActivity)
    USAGE: GET & POST of the Square Retrofit ReST Software to access Zappos Servers
    Holds Viewpager to house the SearchFragment and ShoppingCartFragment
 */
public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, ShoppingCartFragment.OnFragmentInteractionListener{
    protected final String TAG = this.getClass().getSimpleName();
//    protected String KEY = "b743e26728e16b81da139182bb2094357c31d331";

    //Views
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

    // String Variables
    public static String queryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initializeViews();
        createRetrofit();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

    }


    /*
    link View variables to Layout Views
     */
    void initializeViews(){
        searchView = (android.support.v7.widget.SearchView)findViewById(R.id.SearchView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        assert progressBar != null;
        progressBar.setVisibility(View.INVISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.queryText = query;
                progressBar.setVisibility(View.VISIBLE);
                final Call<SearchQuery> result = service.listSearch(query,getResources().getString(R.string.KEY));
                result.enqueue(new Callback<SearchQuery>() {
                    @Override
                    public void onResponse(Call<SearchQuery> call, Response<SearchQuery> response) {
                        Log.d(TAG, String.valueOf(response.raw()));
                        if(response.isSuccessful()){
                            searchView.clearFocus();
                            Product[] array = response.body().getResults();

                            if(array.length == 0){
                                Toast.makeText(MainActivity.this, "No Products Match Your Search :(", Toast.LENGTH_SHORT).show();
                            }else {
                                /*
                                    Clear the Singleton, add new data for Fragment use
                                 */
                                SearchSingleton.getInstance().clearQuery();
                                SearchSingleton.getInstance().addAll(array);
                                mSectionsPagerAdapter.notifyDataSetChanged();
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
        // Transition Animation
        searchView.setQuery("", false);
        searchView.setIconified(true);
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
                    break;

                case 1:
                    fragment = ShoppingCartFragment.newInstance();
                    break;
                
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
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
                    return "Shopping Cart";
                
            }
            return null;
        }

    }

    @Override
    protected void onPostResume() {
        mSectionsPagerAdapter.notifyDataSetChanged();
        super.onPostResume();
    }
    @Override
    protected void onResume(){
        searchView.clearFocus();
        super.onResume();

    }

}
