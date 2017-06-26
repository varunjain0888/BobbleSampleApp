package com.bobble.bobblesampleapp.services;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by aamir on 14/12/16.
 */

public class BackgroundJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case BackgroundJob.TAG:
                return new BackgroundJob(); // creating a job
            default:
                return null;
        }
    }
}
