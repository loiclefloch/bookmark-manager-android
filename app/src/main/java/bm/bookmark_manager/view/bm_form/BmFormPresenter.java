package bm.bookmark_manager.view.bm_form;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.api.RestError;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.Presenter;

public class BmFormPresenter extends Presenter<BmFormViewInterface, BmFormWireframe, BmFormInteractor>
        implements BmFormModuleInterface {

    public static final String EXTRA_MODE = "EXTRA_MODE";
    public static final String EXTRA_BOOKMARK = "EXTRA_BOOKMARK";

    public static final int MODE_EDIT = 1;
    public static final int MODE_NEW = 0;

    protected Bookmark bookmark;

    int mode = -1;

    BmFormPresenter(BmFormViewInterface view, Context context) {
        initPresenter(context, view, new BmFormWireframe(), new BmFormInteractor());
    }

    public void handleIntent(Intent intent) {
        assert intent != null;

        this.mode = intent.getIntExtra(EXTRA_MODE, MODE_NEW);
        this.bookmark = (Bookmark) intent.getSerializableExtra(EXTRA_BOOKMARK);

        if (this.bookmark == null) {
            this.bookmark = new Bookmark();
        }
    }

    // --- BmFormInteractor

    @Override
    public void saveBookmark(String title, String url) {
        this.bookmark.setTitle(title);
        this.bookmark.setUrl(url);

        if (this.mode == MODE_EDIT) {
           editBookmark(bookmark);
        }
        else if (this.mode == MODE_NEW) {
            createBookmark(bookmark);
        }
    }

    // --- Bookmark

    protected void createBookmark(Bookmark bookmark) {
        view.showLoading();
        interactor.createNewBookmark(bookmark, new RestCallback<Bookmark>() {
            @Override
            public void success(Bookmark bookmark) {
                view.hideLoading();
                view.finish();
            }

            @Override
            public void failure(RestError restError) {
                view.hideLoading();
                displayErrorBookmarkSaveFailure(restError);
            }
        });
    }

    protected void editBookmark(Bookmark bookmark) {
        view.hideLoading();
        interactor.updateBookmark(bookmark, new RestCallback<Bookmark>() {
            @Override
            public void success(Bookmark bookmark) {
                view.hideLoading();
                view.finish();
            }

            @Override
            public void failure(RestError restError) {
                view.hideLoading();
                displayErrorBookmarkSaveFailure(restError);
            }
        });
    }

    private void displayErrorBookmarkSaveFailure(RestError restError) {
        String title = context.getString(R.string.error);
        String message = context.getString(R.string.unknown_error);

        if (restError.isApiError()) {
            message = restError.getMessage();
        }
        else {
            handleNetworkError(restError);
        }

        view.displayError(message);
    }
}
