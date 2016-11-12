package me.wcy.weather.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DecimalFormat;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;
import me.wcy.weather.BuildConfig;
import me.wcy.weather.activity.AboutActivity;
import me.wcy.weather.api.Key;
import me.wcy.weather.model.UpdateInfo;

public class UpdateUtils {
    public static long sDownloadId = 0;

    public static void checkUpdate(final Activity activity) {
        // FIR_KEY是检查更新需要的key，可直接用""代替
        FIR.checkForUpdateInFIR(Key.get(activity, Key.FIR_KEY), new VersionCheckCallback() {
            @Override
            public void onStart() {
                if (activity instanceof AboutActivity) {
                    SnackbarUtils.show(activity, "正在检查更新");
                }
            }

            @Override
            public void onSuccess(String versionJson) {
                if (activity.isFinishing()) {
                    return;
                }
                Gson gson = new Gson();
                UpdateInfo updateInfo;
                try {
                    updateInfo = gson.fromJson(versionJson, UpdateInfo.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    return;
                }
                int version = Integer.valueOf(updateInfo.version);
                if (version > BuildConfig.VERSION_CODE) {
                    updateDialog(activity, updateInfo);
                } else {
                    if (activity instanceof AboutActivity) {
                        SnackbarUtils.show(activity, "已是最新版本");
                    }
                }
            }

            @Override
            public void onFail(Exception exception) {
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private static void updateDialog(final Activity activity, final UpdateInfo updateInfo) {
        String message = String.format("v %1$s(%2$sMB)\n\n%3$s", updateInfo.versionShort,
                b2mb(updateInfo.binary.fsize), updateInfo.changelog);
        new AlertDialog.Builder(activity)
                .setTitle("发现新版本")
                .setMessage(message)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        download(activity, updateInfo);
                    }
                })
                .setNegativeButton("稍后提醒", null)
                .show();
    }

    private static void download(Activity activity, UpdateInfo updateInfo) {
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(updateInfo.installUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        String fileName = String.format("PonyWeather_%s.apk", updateInfo.versionShort);
        request.setDestinationInExternalPublicDir("Download", fileName);
        request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(updateInfo.installUrl));
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);// 不允许漫游
        sDownloadId = downloadManager.enqueue(request);
        SnackbarUtils.show(activity, "正在后台下载");
    }

    private static float b2mb(int b) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        String MB = decimalFormat.format((float) b / 1024 / 1024);
        return Float.valueOf(MB);
    }
}
