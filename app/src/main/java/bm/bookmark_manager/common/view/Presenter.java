package bm.bookmark_manager.common.view;

import android.content.Context;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.api.RestError;
import retrofit.RetrofitError;

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

    /**
     * Handle generic network errors
     *
     * @param restError the rest api error
     * @return the message error
     */
    protected String handleNetworkError(RestError restError) {

        if (restError.getError().getKind() == RetrofitError.Kind.NETWORK) {
            return context.getString(R.string.error_http);
        }
        return context.getString(R.string.unknown_error);
    }


}
