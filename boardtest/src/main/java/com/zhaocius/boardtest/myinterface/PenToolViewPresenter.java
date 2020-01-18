package com.zhaocius.boardtest.myinterface;

import android.util.Log;

/**
 * Created by zhaozi on 16/08/2017.
 */

public class PenToolViewPresenter extends BaseUiPresenter<PenToolViewPresenter.PenToolViewPresenterUI,PenToolViewPresenter.PenToolViewPresenterUICallback> {


    @Override
    protected PenToolViewPresenterUICallback createUICallback(PenToolViewPresenterUI ui) {
        return new PenToolViewPresenterUICallback() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
            @Override
            public void call(){
                Log.d("PenToolView", "call");
            }
        };
    }

    public interface PenToolViewPresenterUI extends BaseUiPresenter.UI<PenToolViewPresenterUICallback>{

    }


    public interface PenToolViewPresenterUICallback{
        void call();

    }
}
