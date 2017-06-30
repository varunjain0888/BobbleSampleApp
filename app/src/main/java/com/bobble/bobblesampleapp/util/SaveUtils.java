package com.bobble.bobblesampleapp.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.bobble.bobblesampleapp.R;
import com.bobble.bobblesampleapp.preferences.BobblePrefs;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * Created by varunjain on 6/27/17.
 */

public class SaveUtils {
    private static final java.lang.String TAG = SaveUtils.class.getSimpleName();

    public static void saveGifInGalleryInBackgroundThread(final String dstPath, final String srcPath, final Context context, final boolean runMediaScanner) {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    FileUtil.copy(srcPath, dstPath, true);
                    final String[] SCAN_TYPES = {"image/gif"};
                    File photo = new File(dstPath);

                    if (runMediaScanner) {
                        MediaScannerConnection.scanFile(context,
                                new String[]{photo.getAbsolutePath()}, SCAN_TYPES, null);
                    }
                } catch (Exception e) {
                    Utils.logException(Utils.TAG, e);
                }
                return null;
            }
        });
    }
    public static Uri copyAssets(String uri, String copyToPath, Context context) {
        String saveGifPath = null;
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            BLog.e("Failed to get asset file list.", e.getMessage());
        }
        if (files != null) for (String filename : files) {
            if (filename.equalsIgnoreCase(uri)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(filename);
                    File file = new File(copyToPath);
                    file.mkdirs();
                    saveGifPath = copyToPath + File.separator + filename;
                    if (!FileUtil.isFilePresent(context, saveGifPath)) {
                        File outFile = new File(saveGifPath);
                        out = new FileOutputStream(outFile);
                        copyFile(in, out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    int x = 0;
                    BLog.e("Failed to copy asset file: " + filename, e.getMessage());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
// NOOP
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
// NOOP
                        }
                    }
                }
            }
        }
        return UriUtils.getUri(context, saveGifPath);
    }
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
    public static void saveGiforJPG(final Context context, final String path, final int resId, final String type, final String name)
    {
        Task.callInBackground(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try
                {    File file = null;
                    if(type.equalsIgnoreCase("gif")){
                        file = new File(path,name+ ".gif");
                    }else if(type.equalsIgnoreCase("jpg")){
                        file = new File(path,name+ ".jpg");
                    }else if(type.equalsIgnoreCase("png")){
                        file = new File(path,name+ ".png");
                    }else if(type.equalsIgnoreCase("jpeg")){
                        file = new File(path,name+ ".jpeg");
                    }
                    long startTime = System.currentTimeMillis();
                    InputStream is = context.getResources().openRawResource(resId);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] img = new byte[1024];
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                        baos.write(current);
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                    is.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

    }

}
