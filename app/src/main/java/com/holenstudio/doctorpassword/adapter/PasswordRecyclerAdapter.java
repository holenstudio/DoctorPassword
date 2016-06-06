package com.holenstudio.doctorpassword.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.holenstudio.doctorpassword.R;
import com.holenstudio.doctorpassword.helper.MyItemTouchCallback;
import com.holenstudio.doctorpassword.model.PasswordInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by Holen on 2016/5/23.
 */
public class PasswordRecyclerAdapter extends RecyclerView.Adapter implements MyItemTouchCallback.ItemTouchAdapter{
    private static final String TAG = "PasswordRecyclerAdapter";
    private Context mContext;
    private List<PasswordInfo> mPswList;

    public PasswordRecyclerAdapter(List<PasswordInfo> pswList) {
        this.mPswList = pswList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_password_cardview, parent, false);
        return new PasswordViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PasswordInfo pswInfo = mPswList.get(position);
        if (pswInfo.getDeletable() > 0) {
            ((PasswordViewHoler) holder).setVisibility(false);
        } else {
            ((PasswordViewHoler) holder).setVisibility(true);
            ((PasswordViewHoler) holder).setTitleText(pswInfo.getTitle());
            ((PasswordViewHoler) holder).setUsernameText(pswInfo.getUsername());
            ((PasswordViewHoler) holder).setSiteText(pswInfo.getSite());
        }
    }

    @Override
    public int getItemCount() {
        return mPswList == null? 0: mPswList.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if ((fromPosition == mPswList.size()) || (toPosition == mPswList.size())){
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mPswList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mPswList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        mPswList.remove(position);
        notifyItemRemoved(position);
    }

    public static class PasswordViewHoler extends RecyclerView.ViewHolder {
        private TextView mTitleView;
        private TextView mUsernameView;
        private TextView mSiteView;
        private CardView mCardView;
        private boolean mIsSelected;

        public PasswordViewHoler(View view) {
            super(view);

            mTitleView = (TextView) view.findViewById(R.id.title);
            mUsernameView = (TextView) view.findViewById(R.id.username);
            mSiteView = (TextView) view.findViewById(R.id.site);
            mCardView = (CardView) view.findViewById(R.id.card_view);
        }

        public void setTitleText(String title) {
            mTitleView.setText(title);
        }

        public void setUsernameText(String username) {
            mUsernameView.setText(username);
        }

        public void setSiteText(String site) {
            mSiteView.setText(site);
        }

        public void setSelectable(boolean selected) {
            if (selected) {
                mCardView.setCardBackgroundColor(Color.LTGRAY);
                mIsSelected = true;
            } else {
                mCardView.setCardBackgroundColor(Color.WHITE);
                mIsSelected = false;
            }
        }

        public boolean getSelectable() {
            return mIsSelected;
        }

        public void setVisibility(boolean visibale) {
            if (visibale) {
                mCardView.setVisibility(View.VISIBLE);
            } else {
                mCardView.setVisibility(View.GONE);
            }
        }
    }

}
