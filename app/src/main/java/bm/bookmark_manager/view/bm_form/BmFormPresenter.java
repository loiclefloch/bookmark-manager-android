package bm.bookmark_manager.view.bm_form;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.Presenter;

public class BmFormPresenter extends Presenter<BmFormViewInterface, BmFormWireframe, BmFormInteractor>
        implements BmFormModuleInterface, BmFormInteractor {

    public static final String EXTRA_MODE = "EXTRA_MODE";
    public static final String EXTRA_BOOKMARK = "EXTRA_BOOKMARK";

    public static final int MODE_EDIT = 1;
    public static final int MODE_NEW = 0;

    protected Bookmark bookmark;

    int mode = 0;

    BmFormWireframe wireframe;

    BmFormPresenter(BmFormViewInterface view, Context context) {
        initPresenter(context, view, new BmFormWireframe(), null);
    }

    public void handleIntent(Intent intent) {
        assert intent != null;

        this.mode = intent.getIntExtra(EXTRA_MODE, MODE_NEW);
        this.bookmark = (Bookmark) intent.getSerializableExtra(EXTRA_BOOKMARK);
    }

    @Override
    public void createNewBookmark(String title, String url) {

    }
}
