package com.jbheng.minimart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

/**
 * The {@link RecyclerViewAdapter} class.
 * <p>The adapter provides access to the items in the {@link ItemViewHolder}
 * or the {@link }.</p>
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // An Activity's Context.
    private final Context mContext;

    // The list of product items.
    private final Vector<Object> mRecyclerViewItems;

    public RecyclerViewAdapter(Context context, Vector<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    /**
     * The {@link ItemViewHolder} class.
     * Provides a reference to each view in the product item view.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productItemName;
        private TextView productItemDescription;
        private TextView productItemPrice;
        private TextView productItemCategory;
        private ImageView productItemImage;

        ItemViewHolder(View view) {
            super(view);
            productItemImage = view.findViewById(R.id.product_item_image);
            productItemName = view.findViewById(R.id.product_item_name);
            productItemPrice = view.findViewById(R.id.product_item_price);
            productItemCategory = view.findViewById(R.id.product_item_category);
            productItemDescription = view.findViewById(R.id.product_item_description);

        }

    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    /**
     * Creates a new view for a menu item view or a Native Express ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.menu_item_container, viewGroup, false);
        return new ItemViewHolder(menuItemLayoutView);
    }

    /**
     * Replaces the content in the views that make up the product item view.
     * This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ItemViewHolder productItemHolder = (ItemViewHolder) holder;
        final Product productItem = (Product) mRecyclerViewItems.get(position);

        // Get product item image resource ID.
        String imageName = productItem.getImageName();
        int imageResID = mContext.getResources().getIdentifier(imageName, "drawable",
                mContext.getPackageName());

        // Add the menu item details to the menu item view.
        productItemHolder.productItemImage.setImageResource(imageResID);       // todo: use Picasso here
        productItemHolder.productItemName.setText(productItem.getName());
        productItemHolder.productItemPrice.setText(productItem.getPrice());
        productItemHolder.productItemCategory.setText(productItem.getCategory());
        productItemHolder.productItemDescription.setText(productItem.getDescription());

        // Set click listener on view
        productItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("RVAdapter","Clicked on item");
                    Toast.makeText(mContext, "Clicked on item " + productItem.getName(), Toast.LENGTH_SHORT).show();
                    // todo: swap list for product fragment here
//                    TextView tickerTv = (TextView) v.findViewById(R.id.stockId);
//                    String ticker = tickerTv.getText().toString();
//                    FragmentUtils.addFragment(mFragmentManager,
//                            ContextMenuDialogFragment.newInstance(ticker,mBrokerName,Constants.DETAIL_DIALOG_TYPE_POSITION),
//                            ContextMenuDialogFragment.TAG);
                } catch (Exception e) {
                    Log.e("RVAdapter","onClick: exception: ",e);
                }
            }
        });
    }

}
