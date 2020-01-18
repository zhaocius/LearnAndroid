package com.zhaozi.jumper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.zhaozi.jumper.presenter.ImagePresenter;

/**
 * Created by apple on 18/1/24.
 */

public class BgImageView extends ImageView implements ImagePresenter.BgImageViewUI {

    private ImagePresenter.ImagePresenterCallback imagePresenterCallback;
    public BgImageView(Context context) {
        super(context);
    }

    public BgImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenterCallback(ImagePresenter.ImagePresenterCallback imagePresenterCallback) {
        this.imagePresenterCallback=imagePresenterCallback;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        imagePresenterCallback.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return imagePresenterCallback.onTouchEvent(event);
    }

    public void invalidateSelf(){
        this.invalidate();
    }
}
