package com.zhaocius.boardtest.myinterface;

import android.util.Log;

/**
 * Created by zhaozi on 16/08/2017.
 */

public class MainPresenter extends BaseUiPresenter<MainPresenter.MainPresenterUI, MainPresenter.MainPresenterUICallback> {

    private static final String TAG = "MainPresenter";
    PenToolViewPresenter mPenToolViewPresenter;
    BoardViewPresenter mBoardViewPresenter;

    public MainPresenter() {
        super();
        mPenToolViewPresenter = new PenToolViewPresenter();
        mBoardViewPresenter = new BoardViewPresenter();
    }


    public PenToolViewPresenter getPenToolViewPresenter() {
        return mPenToolViewPresenter;
    }

    public BoardViewPresenter getBoardViewPresenter() {
        return mBoardViewPresenter;
    }

    @Override
    protected void onUIAttached(MainPresenterUI ui) {
        Log.d(TAG, "onUIAttached: ");
        super.onUIAttached(ui);

    }

    @Override
    protected void onInited() {
        Log.d(TAG, "onInited: ");
        super.onInited();

    }


    @Override
    protected MainPresenterUICallback createUICallback(MainPresenterUI ui) {
        return new MainPresenterUICallback() {
            @Override
            public void call() {
                Log.d("zz", "call: ");
            }

        };
    }

    public interface MainPresenterUI extends BaseUiPresenter.UI<MainPresenterUICallback> {

    }


    public interface MainPresenterUICallback {


        void call();

    }
}
