package bm.bookmark_manager.common.view;

import android.content.Context;

public abstract class Presenter<V, W, I> {
    protected Context context;
    protected V view;
    protected W wireframe;
    protected I interactor;

    protected void initPresenter(Context context, V view, W wireframe, I interactor) {
        this.view = view;
        this.context = context;
        this.wireframe = wireframe;
        this.interactor = interactor;
    }
}
