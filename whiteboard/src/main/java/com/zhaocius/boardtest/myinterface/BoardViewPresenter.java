package com.zhaocius.boardtest.myinterface;

/**
 * Created by zhaozi on 16/08/2017.
 */

public class BoardViewPresenter extends BaseUiPresenter<BoardViewPresenter.BoardViewPresenterUI,BoardViewPresenter.BoardViewPresenterUICallback> {


    @Override
    protected BoardViewPresenterUICallback createUICallback(BoardViewPresenterUI ui) {
        return new BoardViewPresenterUICallback() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    public interface BoardViewPresenterUI extends BaseUiPresenter.UI<BoardViewPresenterUICallback>{

    }


    public interface BoardViewPresenterUICallback  {

    }
}
