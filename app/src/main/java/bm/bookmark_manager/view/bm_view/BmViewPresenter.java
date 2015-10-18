package bm.bookmark_manager.view.bm_view;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.Presenter;
import bm.bookmark_manager.common.view.RootWireframe;

public class BmViewPresenter
        extends Presenter<BmViewViewInterface, BmViewWireframe, BmViewInteractor>
        implements BmViewModuleInterface {

    public static final String EXTRA_BOOKMARK = "EXTRA_BOOKMARK";

    protected Bookmark bookmark;

    BmViewPresenter(BmViewViewInterface view, Context context) {
        initPresenter(context, view, new BmViewWireframe(), new BmViewInteractor());
    }

    public void handleIntent(Intent intent) {
        assert intent != null;

        this.bookmark = (Bookmark) intent.getSerializableExtra(EXTRA_BOOKMARK);
        if (this.bookmark == null) {
            view.finish();
        }

        refresh();
    }

    public void refresh() {
        view.setToolbarTitle(bookmark.getTitle());

        view.setTitle(bookmark.getTitle());
        view.setDescription(bookmark.getDescription());
        view.setNotes(bookmark.getNotes());
    }

    // -- BmViewModuleInterface

    @Override
    public void openBookmarkLink() {
        RootWireframe.openLinkInBrowser(context, bookmark.getUrl());
    }
}
