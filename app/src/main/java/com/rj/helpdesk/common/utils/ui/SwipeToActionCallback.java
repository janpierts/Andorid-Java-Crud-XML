package com.rj.helpdesk.common.utils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SwipeToActionCallback extends ItemTouchHelper.SimpleCallback {

    private final Drawable deleteIcon;
    private final Drawable updateIcon;
    private final GradientDrawable backgroundDelete;
    private final GradientDrawable backgroundUpdate;
    private final float cornerRadius;

    public SwipeToActionCallback(Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete);
        updateIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_edit);
        cornerRadius = 12 * context.getResources().getDisplayMetrics().density;
        backgroundDelete = new GradientDrawable();
        backgroundDelete.setColor(Color.parseColor("#F44336"));
        backgroundDelete.setCornerRadius(cornerRadius);
        backgroundUpdate = new GradientDrawable();
        backgroundUpdate.setColor(Color.parseColor("#4CAF50"));
        backgroundUpdate.setCornerRadius(cornerRadius);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

        if (dX > 0) {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft + updateIcon.getIntrinsicWidth();
            backgroundUpdate.setBounds(itemView.getLeft(), itemView.getTop(),itemView.getLeft() + ((int) dX-10), itemView.getBottom());
            backgroundUpdate.draw(c);
            if(dX > iconRight-15){
                updateIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                updateIcon.draw(c);
            }
        } else if (dX < 0) {
            int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            backgroundDelete.setBounds(itemView.getRight() + ((int) dX+10), itemView.getTop(), itemView.getRight(), itemView.getBottom());
            backgroundDelete.draw(c);
            if(dX < -1*(iconRight-iconLeft+65)){
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                deleteIcon.draw(c);
            }
        } else {
            backgroundDelete.setBounds(0, 0, 0, 0);
            backgroundUpdate.setBounds(0, 0, 0, 0);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}