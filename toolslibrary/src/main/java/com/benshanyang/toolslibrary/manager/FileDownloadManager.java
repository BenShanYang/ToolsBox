package com.benshanyang.toolslibrary.manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * @ClassName: APKDownloadManager
 * @Description: 文件下载管理类
 * @Author: YangKuan
 * @Date: 2020/5/9 14:22
 */
public abstract class FileDownloadManager {
    private DownloadManager downloadManager;
    private Context context;
    private long downloadId;
    private String url;
    private String name;

    private String path;

    public FileDownloadManager(Context context, String url) {
        this(context, url, getFileNameByUrl(url));
    }

    public FileDownloadManager(Context context, String url, String name) {
        this.context = context;
        this.url = url;
        this.name = name;
    }

    /**
     * 开始下载
     */
    public void download() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(name);
        //request.setMimeType("application/vnd.android.package-archive");//下载完成后安装应用
        request.setDescription("文件正在下载中......");
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(file));
        path = file.getAbsolutePath();

        //获取DownloadManager
        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            onPrepare();
            downloadId = downloadManager.enqueue(request);
        }

        //注册广播接收者，监听下载状态
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager.Query query = new DownloadManager.Query();
            //通过下载的id查找
            query.setFilterById(downloadId);
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:
                        //下载暂停
                        onPause();
                        break;
                    case DownloadManager.STATUS_PENDING:
                        //下载延迟
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        //正在下载
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //下载完成
                        onSuccess(path);
                        cursor.close();
                        context.unregisterReceiver(receiver);
                        break;
                    case DownloadManager.STATUS_FAILED:
                        //下载失败
                        onFailed(new Exception("下载失败"));
                        cursor.close();
                        context.unregisterReceiver(receiver);
                        break;
                }
            }
        }
    };


    /**
     * 通过URL获取文件名
     *
     * @param url
     * @return
     */
    private static final String getFileNameByUrl(String url) {
        String filename = url.substring(url.lastIndexOf("/") + 1);
        filename = filename.substring(0, filename.indexOf("?") == -1 ? filename.length() : filename.indexOf("?"));
        return filename;
    }

    /**
     * 下载暂停
     */
    public void onPause() {

    }

    public abstract void onPrepare();

    public abstract void onSuccess(String path);

    public abstract void onFailed(Throwable throwable);

}
