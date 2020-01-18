package com.zhaozi.jumper.presenter;

/**
 * Created by apple on 18/1/13.
 */

public abstract class BasePresenter<U extends BasePresenter.UI<UC>,UC> {
    public interface UI<UC>{
       void setPresenterCallback(UC uc);
    }
    private U u;
    protected abstract UC createUC(U u);
    public void init(U u){
        u.setPresenterCallback(createUC(u));
        this.u=u;
        onInited();
    }
    protected U getUI(){
        return this.u;
    }
    public  void onInited(){

    }
}
