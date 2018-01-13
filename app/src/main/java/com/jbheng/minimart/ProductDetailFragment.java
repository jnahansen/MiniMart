
package com.jbheng.minimart;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbheng.minimart.json.Product;

/**
 * Product List Fragment/View
 */
public class ProductDetailFragment extends Fragment {

    public static final String TAG = ProductDetailFragment.class.getName();

    private int mIndex;

    private ImageView prodIv;
    private TextView nameTv;
    private TextView priceTv;
    private TextView shortDescrTv;
    private TextView longDescrTv;

    private Product mProduct;

    /**
     * @return A new instance of fragment.
     */
    public static ProductDetailFragment newInstance(int index) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        if (getArguments() != null) {
            mIndex = getArguments().getInt(Constants.INDEX);
            // Set a preference for last detail item looked at
            PreferenceManager.getDefaultSharedPreferences(App.getMyAppContext()).edit().putInt(Constants.LAST_PRODUCT_DETAIL_INDEX,mIndex);

            mProduct = Products.getInstance().getProducts().get(mIndex);
            if(mProduct == null) {
                Log.e(TAG, "onCreate: product is null, leaving");
                getActivity().finish();
            }
        } else {
            Log.e(TAG, "onCreate: arguments invalid; missing index for product, leaving");
            getActivity().finish();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.product_detail_layout, container, false);
        rootView.setTag(TAG);

        try {
            prodIv = rootView.findViewById(R.id.productImageId);
            // Get product item image url and load using Picasso
            String imageUrl = mProduct.getProductImage();
            Utils.setIconUsingPicasso(imageUrl, prodIv);

            nameTv = rootView.findViewById(R.id.productNameId);
            nameTv.setText(mProduct.getProductName());

            priceTv = rootView.findViewById(R.id.productPriceId);
            priceTv.setText(mProduct.getPrice());

            shortDescrTv = rootView.findViewById(R.id.productShortDescrId);
            if(!TextUtils.isEmpty(mProduct.getShortDescription()))
                shortDescrTv.setText(Html.fromHtml(mProduct.getShortDescription()));

            longDescrTv = rootView.findViewById(R.id.productLongDescrId);
            if(!TextUtils.isEmpty(mProduct.getLongDescription()))
                longDescrTv.setText(Html.fromHtml(mProduct.getLongDescription()));

        } catch (Exception e) {
            Log.e(TAG,"onCreateView: exception ",e);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AdapterInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
