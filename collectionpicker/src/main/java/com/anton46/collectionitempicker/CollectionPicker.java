package com.anton46.collectionitempicker;

import com.anton46.collectionpicker.R;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionPicker extends LinearLayout {

    public static final int LAYOUT_WIDTH_OFFSET = 3;

    private ViewTreeObserver mViewTreeObserver;
    private LayoutInflater mInflater;

    private List<Item> mItems = new ArrayList<>();
    private LinearLayout mRow;
    private HashMap<String, Object> mCheckedItems = new HashMap<>();
    private OnItemClickListener mClickListener;
    private int mWidth;
    private int mItemMargin = 10;
    private int textPaddingLeft = 8;
    private int textPaddingRight = 8;
    private int textPaddingTop = 5;
    private int texPaddingBottom = 5;
    private int mAddIcon = android.R.drawable.ic_menu_add;
    private int mCancelIcon = android.R.drawable.ic_menu_close_clear_cancel;
    private int mLayoutBackgroundColorNormal = R.color.blue;
    private int mLayoutBackgroundColorPressed = R.color.red;
    private int mTextColor = android.R.color.white;
    private int mRadius = 10;
    private boolean mInitialized;

    private boolean simplifiedTags;


    public CollectionPicker(Context context) {
        this(context, null);
    }

    public CollectionPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CollectionPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typeArray = context
                .obtainStyledAttributes(attrs, R.styleable.CollectionPicker);
        this.mItemMargin = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_itemMargin,
                        Utils.dpToPx(this.getContext(), mItemMargin));
        this.textPaddingLeft = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_textPaddingLeft,
                        Utils.dpToPx(this.getContext(), textPaddingLeft));
        this.textPaddingRight = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_textPaddingRight,
                        Utils.dpToPx(this.getContext(),
                                textPaddingRight));
        this.textPaddingTop = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_textPaddingTop,
                        Utils.dpToPx(this.getContext(), textPaddingTop));
        this.texPaddingBottom = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_textPaddingBottom,
                        Utils.dpToPx(this.getContext(),
                                texPaddingBottom));
        this.mAddIcon = typeArray.getResourceId(R.styleable.CollectionPicker_cp_addIcon, mAddIcon);
        this.mCancelIcon = typeArray.getResourceId(R.styleable.CollectionPicker_cp_cancelIcon,
                mCancelIcon);
        this.mLayoutBackgroundColorNormal = typeArray.getColor(
                R.styleable.CollectionPicker_cp_itemBackgroundNormal,
                mLayoutBackgroundColorNormal);
        this.mLayoutBackgroundColorPressed = typeArray.getColor(
                R.styleable.CollectionPicker_cp_itemBackgroundPressed,
                mLayoutBackgroundColorPressed);
        this.mRadius = (int) typeArray
                .getDimension(R.styleable.CollectionPicker_cp_itemRadius, mRadius);
        this.mTextColor = typeArray
                .getColor(R.styleable.CollectionPicker_cp_itemTextColor, mTextColor);
        this.simplifiedTags = typeArray
                .getBoolean(R.styleable.CollectionPicker_cp_simplified, false);
        typeArray.recycle();

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        mViewTreeObserver = getViewTreeObserver();
        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    drawItemView();
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    /**
     * Selected flags
     */
    public void setCheckedItems(HashMap<String, Object> checkedItems) {
        mCheckedItems = checkedItems;
    }

    public HashMap<String, Object> getCheckedItems() {
        return mCheckedItems;
    }

    public void drawItemView() {
        if (!mInitialized) {
            return;
        }

        clearUi();

        float totalPadding = getPaddingLeft() + getPaddingRight();
        int indexFrontView = 0;

        LayoutParams itemParams = getItemLayoutParams();

        for (int i = 0; i < mItems.size(); i++) {
            final Item item = mItems.get(i);
            if (mCheckedItems != null && mCheckedItems.containsKey(item.id)) {
                item.isSelected = true;
            }

            final int position = i;
            final View itemLayout = createItemView(item);

            if (!simplifiedTags) {
                itemLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        animateView(v);
                        item.isSelected = !item.isSelected;
                        if (item.isSelected) {
                            mCheckedItems.put(item.id, item);
                        } else {
                            mCheckedItems.remove(item.id);
                        }

                        if (isJellyBeanAndAbove()) {
                            itemLayout.setBackground(getSelector(item));
                        } else {
                            itemLayout.setBackgroundDrawable(getSelector(item));
                        }
                        ImageView iconView = (ImageView) itemLayout.findViewById(R.id.item_icon);
                        iconView.setBackgroundResource(getItemIcon(item.isSelected));
                        if (mClickListener != null) {
                            mClickListener.onClick(item, position);
                        }
                    }
                });
            }

            TextView itemTextView = (TextView) itemLayout.findViewById(R.id.item_name);
            itemTextView.setText(item.text);
            itemTextView.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight,
                    texPaddingBottom);
            itemTextView.setTextColor(getResources().getColor(mTextColor));

            float itemWidth = itemTextView.getPaint().measureText(item.text) + textPaddingLeft
                    + textPaddingRight;

            // if (!simplifiedTags) {
            ImageView indicatorView = (ImageView) itemLayout.findViewById(R.id.item_icon);
            indicatorView.setBackgroundResource(getItemIcon(item.isSelected));
            indicatorView.setPadding(0, textPaddingTop, textPaddingRight, texPaddingBottom);

            if (simplifiedTags) {
                indicatorView.setVisibility(View.GONE);
            }

            itemWidth += Utils.dpToPx(getContext(), 30) + textPaddingLeft
                    + textPaddingRight;

            if (mWidth <= totalPadding + itemWidth + Utils
                    .dpToPx(this.getContext(), LAYOUT_WIDTH_OFFSET)) {
                totalPadding = getPaddingLeft() + getPaddingRight();
                indexFrontView = i;
                addItemView(itemLayout, itemParams, true, i);
            } else {
                if (i != indexFrontView) {
                    itemParams.leftMargin = mItemMargin;
                    totalPadding += mItemMargin;
                }
                addItemView(itemLayout, itemParams, false, i);
            }
            totalPadding += itemWidth;
        }
        // }
    }

    private View createItemView(Item item) {
        View view = mInflater.inflate(R.layout.item_layout, this, false);
        if (isJellyBeanAndAbove()) {
            view.setBackground(getSelector(item));
        } else {
            view.setBackgroundDrawable(getSelector(item));
        }

        return view;
    }

    private LayoutParams getItemLayoutParams() {
        LayoutParams itemParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        itemParams.bottomMargin = mItemMargin / 2;
        itemParams.topMargin = mItemMargin / 2;

        return itemParams;
    }

    private int getItemIcon(Boolean isSelected) {
        return isSelected ? mCancelIcon : mAddIcon;
    }

    private void clearUi() {
        removeAllViews();
        mRow = null;
    }

    private void addItemView(View itemView, ViewGroup.LayoutParams chipParams, boolean newLine,
            int position) {
        if (mRow == null || newLine) {
            mRow = new LinearLayout(getContext());
            mRow.setGravity(Gravity.CENTER);
            mRow.setOrientation(HORIZONTAL);

            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            mRow.setLayoutParams(params);

            addView(mRow);
        }

        mRow.addView(itemView, chipParams);
        animateItemView(itemView, position);
    }

    private StateListDrawable getSelector(Item item) {
        return item.isSelected ? getSelectorSelected() : getSelectorNormal();
    }

    private StateListDrawable getSelectorNormal() {
        StateListDrawable states = new StateListDrawable();

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mLayoutBackgroundColorPressed);
        gradientDrawable.setCornerRadius(mRadius);

        states.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mLayoutBackgroundColorNormal);
        gradientDrawable.setCornerRadius(mRadius);

        states.addState(new int[]{}, gradientDrawable);

        return states;
    }

    private StateListDrawable getSelectorSelected() {
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mLayoutBackgroundColorNormal);
        gradientDrawable.setCornerRadius(mRadius);

        states.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);

        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mLayoutBackgroundColorPressed);
        gradientDrawable.setCornerRadius(mRadius);

        states.addState(new int[]{}, gradientDrawable);

        return states;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public void clearItems() {
        mItems.clear();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private boolean isJellyBeanAndAbove() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
    }

    private void animateView(final View view) {
        view.setScaleY(1f);
        view.setScaleX(1f);

        view.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(100)
                .setStartDelay(0)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        reverseAnimation(view);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void reverseAnimation(View view) {
        view.setScaleY(1.2f);
        view.setScaleX(1.2f);

        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
                .setListener(null)
                .start();
    }

    private void animateItemView(View view, int position) {
        long animationDelay = 600;

        animationDelay += position * 30;

        view.setScaleY(0);
        view.setScaleX(0);
        view.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(null)
                .setStartDelay(animationDelay)
                .start();
    }

}
