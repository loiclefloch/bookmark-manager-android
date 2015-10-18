package bm.bookmark_manager.view.bm_preview;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.Presenter;

public class BmPreviewPresenter
        extends Presenter<BmPreviewViewInterface, BmPreviewWireframe, BmPreviewInteractor>
        implements BmPreviewModuleInterface {

    public static final String EXTRA_BOOKMARK = "EXTRA_BOOKMARK";

    protected Bookmark bookmark;

    BmPreviewPresenter(BmPreviewViewInterface view, Context context) {
        initPresenter(context, view, new BmPreviewWireframe(), new BmPreviewInteractor());
    }

    public void refreshView() {
        view.setHtml(bookmark.getReadableContentHtml());
    }

    // -- BmPreviewModuleInterface

    @Override
    public void handleIntent(Intent intent) {
        assert intent != null;

        this.bookmark = (Bookmark) intent.getSerializableExtra(EXTRA_BOOKMARK);
        if (this.bookmark == null) {
            view.finish();
        }

        refreshView();
    }
}
