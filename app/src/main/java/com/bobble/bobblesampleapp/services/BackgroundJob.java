package com.bobble.bobblesampleapp.services;

import android.support.annotation.NonNull;

import com.bobble.bobblesampleapp.networking.Networking;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.bobble.bobblesampleapp.util.AppUtils;
import com.bobble.bobblesampleapp.util.BLog;
import com.bobble.bobblesampleapp.util.GrowthAppWorkUtil;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by aamir on 14/12/16.
 */

public class BackgroundJob extends Job {

    public static final String TAG = "BackgroundJob_Job";
    private static final long intervalTime = TimeUnit.HOURS.toMillis(12);//12 hrs of periodic interval
    private static final long flexTime = TimeUnit.MINUTES.toMillis(30); // 30 minutes of flex time

    //60000 = 1 min
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        try {
            if (AppUtils.isAppIsInBackground(getContext())) {
                BLog.d(TAG, "xxmm: Running Job........ "+params.getId());
                GrowthAppWorkUtil.logEventsToServer(getContext());
                if (!new BobblePrefs(getContext()).isRegistered().get()) {
                    Networking.registerUser(getContext(), false);
                }
            } /*else {
                EventBus.getDefault().post("connectivityReceiver");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAILURE;
        }
        return Result.SUCCESS;
    }


    public static int scheduleBackgroundJob() {
        int jobId = new JobRequest.Builder(TAG)
                .setPeriodic(intervalTime, flexTime) //after every 12 hrs and the job can be executed between 11 hrs 30 min to 12 hrs
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED) // the device must be connected to network
                .setRequirementsEnforced(true)
                .setPersisted(true) // job persisted after reboot
                .setUpdateCurrent(true) // update the running job
                .build()
                .schedule();
        BLog.d(TAG, "Job Scheduled....... "+jobId);
        return jobId;
    }

    @Override
    protected void onCancelJob() {

    }
}
