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
 * <p>The adapter provides access to the items in the {@link MenuItemViewHolder}
 * or the {@link NativeExpressAdViewHolder}.</p>
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

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
     * The {@link MenuItemViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView menuItemName;
        private TextView menuItemDescription;
        private TextView menuItemPrice;
        private TextView menuItemCategory;
        private ImageView menuItemImage;

        MenuItemViewHolder(View view) {
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
//        return (position % MainActivity.ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE
//                : MENU_ITEM_VIEW_TYPE;
    }

    /**
     * Creates a new view for a menu item view or a Native Express ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.menu_item_container, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
//            case NATIVE_EXPRESS_AD_VIEW_TYPE:
//                // fall through
//            default:
//                View nativeExpressLayoutView = LayoutInflater.from(
//                        viewGroup.getContext()).inflate(R.layout.native_express_ad_container,
//                        viewGroup, false);
//                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * Native Express ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
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
                break;
           /* case NATIVE_EXPRESS_AD_VIEW_TYPE:
                // fall through
            default:
                NativeExpressAdViewHolder nativeExpressHolder =
                        (NativeExpressAdViewHolder) holder;
                NativeExpressAdView adView =
                        (NativeExpressAdView) mRecyclerViewItems.get(position);
                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                // The NativeExpressAdViewHolder recycled by the RecyclerView may be a different
                // instance than the one used previously for this position. Clear the
                // NativeExpressAdViewHolder of any subviews in case it has a different
                // AdView associated with it, and make sure the AdView for this position doesn't
                // already have a parent of a different recycled NativeExpressAdViewHolder.
                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }

                // Add the Native Express ad to the native express ad view.
                adCardView.addView(adView);*/
        }
    }

}
