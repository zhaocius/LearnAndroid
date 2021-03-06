package com.zhaocius.boardtest.myinterface;

import android.util.Log;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by zhaozi on 16/08/2017.
 */
   public abstract class BaseUiPresenter<U extends BaseUiPresenter.UI<UC>,UC> extends BasePresenter{
    private static final String TAG = "BaseUiPresenter";
    public interface UI<UC>{
        void setCallback(UC callback);
        int getID();
    }

    private final Set<U> mUIs;

    private final Set<U> mUnmodefiableUIs;

    public BaseUiPresenter(){
        mUIs=new CopyOnWriteArraySet<U>();
        mUnmodefiableUIs= Collections.unmodifiableSet(mUIs);
    }
    public synchronized final void attachUI(U ui) {
        if(ui==null)
            return;
        Log.d(TAG, "attachUI: "+mUIs.size());
        mUIs.add(ui);
        Log.d(TAG, "attachUI: "+mUIs.size());
        ui.setCallback(createUICallback(ui));
        if(isIniated()){
            onUIAttached(ui);
            populateUI(ui);
        }


    }

    public synchronized final void detachUI(U ui){
        Log.d(TAG, "detachUI: ");
        if(ui==null)
            return;
        onUIDetached(ui);
        ui.setCallback(null);
        mUIs.remove(ui);
    }

    protected final Set<U> getUIs(){
        return mUIs;
    }

    protected  void onInited(){
        Log.d(TAG, "onInited: ");
        if(!mUIs.isEmpty()){
            Log.d(TAG, "onInited:mUIs is not empty ");
            for(U ui:mUIs){
                onUIAttached(ui);
                populateUI(ui);
            }
        }
    }
    protected  void onUIAttached(U ui){

    }
    protected  void onUIDetached(U ui){

    }
    protected synchronized final void populateUIs(){
        for(U ui: mUIs){
            populateUI(ui);
        }
    }
    protected void populateUI(U ui){

    }
    protected abstract UC createUICallback(U ui);




}
