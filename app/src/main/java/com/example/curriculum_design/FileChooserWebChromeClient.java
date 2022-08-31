package com.example.curriculum_design;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import static android.app.Activity.RESULT_OK;

public class FileChooserWebChromeClient extends WebChromeClient {
    static class FileChooserWebChromeClientBuild{
        UploadMessage uploadMessage;
        ActivityCallBack callBack;
        FileChooserWebChromeClientBuild(ActivityCallBack callBack){
            this.uploadMessage = new UploadMessage();
            this.callBack = callBack;
        }
        public FileChooserWebChromeClient build(){
            return new FileChooserWebChromeClient(this);
        }
    }
    public static FileChooserWebChromeClient createBuild(ActivityCallBack callBack){
        return new FileChooserWebChromeClientBuild(callBack).build();
    }
    FileChooserWebChromeClientBuild build;
    private FileChooserWebChromeClient(FileChooserWebChromeClientBuild build){
        this.build = build;
    }

    public interface ActivityCallBack{
        void FileChooserBack(Intent intent);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        build.uploadMessage.setUploadMessageAboveL(filePathCallback);
        build.callBack.FileChooserBack(build.uploadMessage.openImageChooserActivity(fileChooserParams.getAcceptTypes()));
        return true;
    }
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        //uploadMessage = valueCallback;
        build.uploadMessage.setUploadMessage(valueCallback);
        build.callBack.FileChooserBack (build.uploadMessage.openImageChooserActivity("image/*"));
    }

    // For Android  >= 3.0
    public void openFileChooser(ValueCallback valueCallback, String acceptType) {
        build.uploadMessage.setUploadMessage(valueCallback);
        build.callBack.FileChooserBack(build.uploadMessage.openImageChooserActivity(acceptType));
    }

    //For Android  >= 4.1
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        build.uploadMessage.setUploadMessage(valueCallback);
        build.callBack.FileChooserBack(build.uploadMessage.openImageChooserActivity(acceptType));
    }

    public UploadMessage getUploadMessage(){
        return build.uploadMessage;
    }
}

class UploadMessage {

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    public final static int FILE_CHOOSER_RESULT_CODE = 10000;

    public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public void setUploadMessageAboveL(ValueCallback<Uri[]> uploadMessageAboveL) {
        this.uploadMessageAboveL = uploadMessageAboveL;
    }

    public Intent openImageChooserActivity(String[] acceptType) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        i.putExtra(Intent.EXTRA_MIME_TYPES, acceptType);
        return i;
    }

    public Intent openImageChooserActivity(String acceptType) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(acceptType);
        return Intent.createChooser(i, "Image Chooser");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == uploadMessage && null == uploadMessageAboveL) return;
        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
        if (uploadMessageAboveL != null) {
            onActivityResultAboveL(requestCode, resultCode, data);
        } else if (uploadMessage != null) {
            uploadMessage.onReceiveValue(result);
            uploadMessage = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}
