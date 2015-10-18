package bm.bookmark_manager.view.bm_list;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.api.RestError;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.Presenter;

public class BmListPresenter extends Presenter<BmListViewInterface, BmListWireframe, BmListInteractor>
        implements BmListModuleInterface {


    BmListPresenter(BmListViewInterface view, Context context) {
        initPresenter(context, view, new BmListWireframe(), new BmListInteractor());
    }

    @Override
    public void presentAddNewBookmark() {
        wireframe.presentAddInterface(context);
    }

    @Override
    public void openBookmarkLink(final Bookmark bookmark) {
        wireframe.openLinkInBrowser(context, bookmark.getUrl());
    }

    @Override
    public void previewBookmark(final Bookmark bookmark) {

    }

    @Override
    public void editBookmark(final Bookmark bookmark) {

    }

    @Override
    public void openBookmark(Bookmark bookmark) {
        wireframe.presentBookmarkInterface(context, bookmark);
    }

    public void load() {
        refresh();
    }

    public void refresh() {

        interactor
                .getBookmarks(new RestCallback<List<Bookmark>>() {
                    @Override
                    public void success(List<Bookmark> bookmarksResponse) {
                        view.reloadEntries(bookmarksResponse);
                    }

                    @Override
                    public void failure(RestError restError) {
                        Logger.e("Error while getting data: ".concat(String.valueOf(restError)));

                        view.showLoadingErrorMessage();
                        view.showNoContentMessage();
                    }
                });
    }

}
