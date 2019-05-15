package com.zhaocius.webviewtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    private WebView webView;
    public static String webUrl = "file:///android_asset/index1/index.html";   //访问的页面

    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.id_webview);
        initWebView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.reload();
    }

    public void initWebView() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置JS脚本
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
      //  webSettings.setUseWideViewPort(true); // 关键点，自适应网页
      // webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 仅支持双击缩放，不支持触摸缩放

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容，如果设置缓存，会在data／data下生成缓存
        WebViewClient wvc = new WebViewClient() {   //处理各种通知&请求事件，
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  //复写这个方法，打开网页不调用系统浏览器，在本webview显示
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {  //加载开始提示
                Log.d(TAG, "onPageStarted: " + "开始加载了");
            }

            @Override
            public void onPageFinished(WebView view, String url) {   //加载结束提示
                Log.d(TAG, "onPageFinished: " + "结束加载了");
            }

        };
        webView.setWebViewClient(wvc);

        webView.setWebChromeClient(new WebChromeClient() {      //辅助处理javascript对话框，网站图标，网站标题等


            @Override
            public void onReceivedTitle(WebView view, String title) {  //标题
                Log.d(TAG, "onReceivedTitle: " + "标题在这里" + title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {   //进度条
                if (newProgress < 100) {
                    Log.d(TAG, "onProgressChanged: " + "newProgress" + newProgress);
                } else if (newProgress == 100) {
                    Log.d(TAG, "onProgressChanged: " + "newProgress" + newProgress);
                }
            }

            /*** 视频播放相关的方法 **/
            //播放视频时候，需要花一定时间来进行数据缓冲，用这个函数来提供一个数据缓冲时显示的试图，如：显示一个转轮动画
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(MainActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            //在api18被启用，通知主机应用webview需要显示一个特定方向的custom view
            //view 即将要显示的view，全屏的时候，就让framelayout全屏显示，视频就在framelayout内播放了。要用到回调函数。
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                Log.d(TAG, "onShowCustomView: " + view);
                showCustomView(view, callback);
            }

            //退出视频通知
            @Override
            public void onHideCustomView() {
                Log.d(TAG, "onHideCustomView: ");
                hideCustomView();
            }
        });

        // 加载Web地址
        webView.loadUrl(webUrl);
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {     //如果framelayout不是空的，直接隐藏
            callback.onCustomViewHidden();   //隐藏
            return;
        }
        //开始播放//获取顶层view
        MainActivity.this.getWindow().getDecorView();
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(MainActivity.this); //这个是全屏界面
        Log.d(TAG, "showCustomView: " + view);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);  //将video放到当前视图
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {      //是否可以后退
                    webView.goBack();    //后退网页
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}
