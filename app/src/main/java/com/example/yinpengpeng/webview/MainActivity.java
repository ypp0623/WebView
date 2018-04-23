package com.example.yinpengpeng.webview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Handler mHandler=new Handler();

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.myweb);

        webView.loadUrl("https://www.baidu.com/?tn=78000241_5_hao_pg");
        webView.getSettings().setJavaScriptEnabled(true);//设置JavaScript支持
        webView.requestFocus();//设置焦点支持
        webView.canGoBack();//设置goback支持

        //希望点击链接由自己处理，而不是新开 Android 的系统 browser 中响应该链接,以下是打开的几个阶段

        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        // 处理网页中的一些对话框信息（提示对话框，带选择的对话框，带输入的对话框）

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示对话框");
                builder.setMessage(message);
                builder.setPositiveButton("ok", new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //此处可以获取加载的进度
                super.onProgressChanged(view, newProgress);
            }
        });

        // 与网页进行交互的 addJavascriptInterface()的方法
        webView.addJavascriptInterface(new Object(){
            public void androidMethod(final String str){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //
                    }
                });
            }
        },"demo");




    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断网页是否可以会到上一个界面
        if (keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}