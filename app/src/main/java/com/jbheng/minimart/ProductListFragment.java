
package com.jbheng.minimart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Product List Fragment/View
 */
public class ProductListFragment extends Fragment {

    private static final String TAG = ProductListFragment.class.getName();

    protected RecyclerView mRecyclerView;

    // List of Products that populate RecyclerView.
    private Vector<Object> mProducts = new Vector<>();

    private AdapterInterface mAdapterIntf;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.product_list_layout, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.scrollToPosition(0);        // todo: option - goto arg position

        // Update the RecyclerView item's list with menu items and Native Express ads.
        addMenuItemsFromJson();
        // todo: add initial products to list

        // Create adapter on Activity lifecycle and set into RecyclerView
        mAdapterIntf.setAdapter(new RecyclerViewAdapter(getContext(), mProducts));
        mRecyclerView.setAdapter(mAdapterIntf.getAdapter());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Initialize dataset on a background thread
//        new LoadData().execute("");       // todo: remove when done w/ AsyncTask; can call fetchData here
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAdapterIntf = (AdapterInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AdapterInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapterIntf = null;
    }

    /**
     * Adds {@link Product}'s from a JSON file.
     */
    private void addMenuItemsFromJson() {
        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String menuItemName = menuItemObject.getString("name");
                String menuItemDescription = menuItemObject.getString("description");
                String menuItemPrice = menuItemObject.getString("price");
                String menuItemCategory = menuItemObject.getString("category");
                String menuItemImageName = menuItemObject.getString("photo");

                Product menuItem = new Product(menuItemName, menuItemDescription, menuItemPrice,
                        menuItemCategory, menuItemImageName);
                mProducts.add(menuItem);
            }
        } catch (IOException | JSONException exception) {
            Log.e(MainActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    /**
     * Reads the JSON file and converts the JSON data to a {@link String}.
     *
     * @return A {@link String} representation of the JSON data.
     * @throws IOException if unable to read the JSON file.
     */
    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.menu_items_json);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }


    // AsyncTask for fetching data from Network
    // todo: NOTE: rotation is not handled correctly as the inner class
    // has a reference to the Activity (destroyed during rotation). We
    // should have used task.cancel in onDestroy and tested etc.
    // The Adapter would not be correct. I think we fixed orientation here.
   /* private class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground");

            String jsonData = Utils.getDataFromUrl(EARTHQUAKES_DATA_URL);
            Log.i(TAG, "doInBackground: result: " + jsonData);

            // parse array of json and build mDataSet
            mDataset = Earthquake.parseEarthquakes(jsonData);

            // todo: could add different sorting here e.g.nsort on magnitude vs. date here; data is already sorted

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // set data into RecyclerView and refresh; check if view is created first
            mAdapter.setData(mDataset);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }*/
}
