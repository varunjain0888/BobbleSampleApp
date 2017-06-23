package com.bobble.bobblesampleapp.adapters;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bobble.bobblesampleapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prashant Gupta on 15-11-2016.
 */

public class GifShareAsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final String TAG = "ImageShareAsAdapter";
    private final int REQUEST_CODE_SHARE_TO_MESSENGER = 100;
    private final String PACKAGE_NAME = "com.facebook.orca";
    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    protected Activity context;
    protected List<ResolveInfo> list = new ArrayList<>();
    protected PackageManager pm;
    Uri finalUri;
    long bobbleAnimationPackId;
    String otfText;
    int sharingPosition;

    public GifShareAsAdapter(Activity context) {
        this.context = context;
        this.finalUri = finalUri;
        this.otfText = otfText;
        this.bobbleAnimationPackId = bobbleAnimationPackId;
        this.sharingPosition = sharingPosition;
        pm = context.getPackageManager();
        sendIntent.setType("image/gif");
        list = pm.queryIntentActivities(sendIntent, 0);

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.google.android.talk")) {
                ResolveInfo resolveInfo = list.get(i);
                list.remove(i);
                list.add(0, resolveInfo);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.facebook.katana")) {
                ResolveInfo resolveInfo = list.get(i);
                list.remove(i);
                list.add(0, resolveInfo);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.bsb.hike")) {
                ResolveInfo resolveInfo = list.get(i);
                list.remove(i);
                list.add(0, resolveInfo);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.facebook.orca")) {
                ResolveInfo resolveInfo = list.get(i);
                list.remove(i);
                list.add(0, resolveInfo);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.whatsapp")) {
                ResolveInfo resolveInfo = list.get(i);
                list.remove(i);
                list.add(0, resolveInfo);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches(context.getPackageName())) {
                list.remove(i);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ActivityInfo activity = list.get(i).activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            if (name.getPackageName().matches("com.facebook.lite") || activity.name.matches("com.facebook.lite.MainActivity") || activity.name.matches("com.facebook.timeline.stagingground.ProfilePictureShareActivityAlias")) {
                list.remove(i);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = new ViewHolderTwo(inflater.inflate(R.layout.item_chooser_horizontal, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderTwo vh2 = (ViewHolderTwo) holder;
        setUpViewHolderTwo(vh2, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        LinearLayout containerLayout;

        public ViewHolderTwo(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon);
            name = (TextView) v.findViewById(R.id.name);
            containerLayout = (LinearLayout) v.findViewById(R.id.containerLayout);
        }
    }

    private void setUpViewHolderTwo(ViewHolderTwo vh2, final int position) {
        vh2.icon.setImageDrawable(list.get(position).loadIcon(pm));
        vh2.name.setText(list.get(position).loadLabel(pm));
        vh2.containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
