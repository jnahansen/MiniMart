
package com.jbheng.minimart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private int mPosition;

    private ImageView prodIv;
    private TextView nameTv;
    private TextView priceTv;
    private TextView shortDescrTv;
    private TextView longDescrTv;

    private Product mProduct;

    /**
     * @return A new instance of fragment.
     */
    public static ProductDetailFragment newInstance(int pos) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, pos);
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

        // Restart app if no product data here
        if(! Products.getInstance().haveProducts()) {
            startActivity(new Intent(getContext(),MainActivity.class));
            getActivity().finish();
            return;
        }

        // Get product position
        if (getArguments() != null) {
            mPosition = getArguments().getInt(Constants.POSITION);
            if (Products.getInstance().isValidPosition(mPosition)) {
                // Set a preference for last detail product position looked at
                PreferenceManager.getDefaultSharedPreferences(App.getMyAppContext()).edit().putInt(Constants.LAST_PRODUCT_DETAIL_INDEX, mPosition);
                mProduct = Products.getInstance().getProducts().get(mPosition);
                return;
            }
        }

        Log.e(TAG, "onCreate: unexpected problem, mPosition: " + String.valueOf(mPosition) + ", leaving");
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.product_detail_layout, container, false);
        rootView.setTag(TAG);

        try {

            // Listen for swipe L/R gestures to move to next/previous product
            final GestureDetector gdt = new GestureDetector(new GestureListener());
            // Set listener on layout view
            View layout = rootView.findViewById(R.id.detailScrollViewId);
            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View view, final MotionEvent event) {
                    return gdt.onTouchEvent(event);
                }
            });


            prodIv = rootView.findViewById(R.id.productImageId);
            // Get product item image url and load using Picasso
            String imageUrl = mProduct.getProductImage();
            Utils.setIconUsingPicasso(imageUrl, prodIv);

            nameTv = rootView.findViewById(R.id.productNameId);
            nameTv.setText(mProduct.getProductName());

            priceTv = rootView.findViewById(R.id.productPriceId);
            priceTv.setText(mProduct.getPrice());

            shortDescrTv = rootView.findViewById(R.id.productShortDescrId);
            if (!TextUtils.isEmpty(mProduct.getShortDescription()))
                shortDescrTv.setText(Html.fromHtml(mProduct.getShortDescription()));

            longDescrTv = rootView.findViewById(R.id.productLongDescrId);
            if (!TextUtils.isEmpty(mProduct.getLongDescription()))
                longDescrTv.setText(Html.fromHtml(mProduct.getLongDescription()));

        } catch (Exception e) {
            Log.e(TAG, "onCreateView: exception ", e);
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

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //Log.i(TAG, "SWIPE R TO L");
                moveToProduct(mPosition+1,true);
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //Log.i(TAG, "SWIPE L TO R");
                moveToProduct(mPosition-1,false);
                return true;
            }

            // below unused
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Bottom to top
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Top to bottom
            }
            return false;
        }
    }

    private void moveToProduct(int pos,boolean forward) {
        try {
            // Check position
            if(pos < 0 || pos >= Products.getInstance().size()) {
                Log.i(TAG,"moveToProduct: index out of range, leaving");
                return;
            }
            // Start new activity
            ProductDetailActivity.startProductDetailActivity(pos);
            // Finish previous product activity
            getActivity().finish();
            // Animate new product onto screen
            if(forward)
                getActivity().overridePendingTransition(R.anim.to_left,R.anim.nothing);
            else
                getActivity().overridePendingTransition(R.anim.to_right,R.anim.nothing);

        } catch (Exception e) {
            Log.e(TAG,"moveToProduct: exception ",e);
        }
    }

}
