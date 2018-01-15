package com.jbheng.minimart;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
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
 * The {@link RecyclerViewAdapter} class.
 * <p>The adapter provides access to the items in the {@link ItemViewHolder}
 * or the {@link }.</p>
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = RecyclerViewAdapter.class.getName();

    private Context mContext;

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * The {@link ItemViewHolder} class.
     * Provides a reference to each view in the product item view.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productItemName;
        private TextView productItemShortDescription;
        private TextView productItemPrice;
        private ImageView productItemImage;

        ItemViewHolder(View view) {
            super(view);
            productItemImage = view.findViewById(R.id.product_item_image);
            productItemName = view.findViewById(R.id.product_item_name);
            productItemPrice = view.findViewById(R.id.product_item_price);
            productItemShortDescription = view.findViewById(R.id.product_item_short_description);
        }

    }

    @Override
    public int getItemCount() {
        return Products.getInstance().getProducts().size();
    }

    /**
     * Creates a new view for a menu item view or a Native Express ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.product_item_layout, viewGroup, false);
        return new ItemViewHolder(menuItemLayoutView);
    }

    /**
     * Replaces the content in the views that make up the product item view.
     * This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ItemViewHolder productItemHolder = (ItemViewHolder) holder;
        final Product productItem = Products.getInstance().getProducts().get(position);

        // Get product item image url and lazy load using Picasso
        String imageUrl = productItem.getProductImage();
        Utils.setIconUsingPicasso(imageUrl, productItemHolder.productItemImage);

        productItemHolder.productItemName.setText(productItem.getProductName());
        productItemHolder.productItemPrice.setText(productItem.getPrice());

        // Set html from short description if we have it
        if (!TextUtils.isEmpty(productItem.getShortDescription())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)      // Nougat and above
                productItemHolder.productItemShortDescription.setText(Html.fromHtml(productItem.getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            else
                productItemHolder.productItemShortDescription.setText(Html.fromHtml(productItem.getShortDescription()));
        }

        // Set click listener on view
        productItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RVAdapter", "Clicked on item " + String.valueOf(productItem.getProductName()));
                ProductDetailActivity.startProductDetailActivity(mContext,position);
            }
        });
    }

}
