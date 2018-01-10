package com.jbheng.minimart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * The {@link RecyclerViewAdapter} class.
 * <p>The adapter provides access to the items in the {@link ItemViewHolder}
 * or the {@link NativeExpressAdViewHolder}.</p>
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;       // todo: probably redundant

    // An Activity's Context.
    private final Context mContext;

    // The list of Native Express ads and menu items.
    private final Vector<Object> mRecyclerViewItems;

    /**
     * For this example app, the recyclerViewItems list contains only
     * {@link Product} types.
     */
    public RecyclerViewAdapter(Context context, Vector<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    /**
     * The {@link ItemViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView menuItemName;
        private TextView menuItemDescription;
        private TextView menuItemPrice;
        private TextView menuItemCategory;
        private ImageView menuItemImage;

        ItemViewHolder(View view) {
            super(view);
            menuItemImage = view.findViewById(R.id.menu_item_image);
            menuItemName = view.findViewById(R.id.menu_item_name);
            menuItemPrice = view.findViewById(R.id.menu_item_price);
            menuItemCategory = view.findViewById(R.id.menu_item_category);
            menuItemDescription = view.findViewById(R.id.menu_item_description);
        }
    }

    /**
     * The {@link NativeExpressAdViewHolder} class.
     */
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {
        return MENU_ITEM_VIEW_TYPE;
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
     * Replaces the content in the views that make up the menu item view and the
     * Native Express ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int viewType = getItemViewType(position);

        ItemViewHolder menuItemHolder = (ItemViewHolder) holder;
        Product menuItem = (Product) mRecyclerViewItems.get(position);

        // Get the menu item image resource ID.
        String imageName = menuItem.getImageName();
        int imageResID = mContext.getResources().getIdentifier(imageName, "drawable",
                mContext.getPackageName());

        // Add the menu item details to the menu item view.
        menuItemHolder.menuItemImage.setImageResource(imageResID);
        menuItemHolder.menuItemName.setText(menuItem.getName());
        menuItemHolder.menuItemPrice.setText(menuItem.getPrice());
        menuItemHolder.menuItemCategory.setText(menuItem.getCategory());
        menuItemHolder.menuItemDescription.setText(menuItem.getDescription());
    }

}
