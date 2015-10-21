package bm.bookmark_manager.view.bm_list;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.api.RestError;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.tools.search.Search;
import bm.bookmark_manager.common.view.Presenter;
import bm.bookmark_manager.common.view.RootWireframe;

public class BmListPresenter extends Presenter<BmListViewInterface, BmListWireframe, BmListInteractor>
        implements BmListModuleInterface {

    List<Bookmark> bookmarksList;
    Search search = new Search();

    BmListPresenter(BmListViewInterface view, Context context) {
        initPresenter(context, view, new BmListWireframe(), new BmListInteractor());
        changeSearchType(Search.Filter.DATE, false);
    }

    @Override
    public void presentAddNewBookmark() {
        wireframe.presentAddInterface(context);
    }

    @Override
    public void openBookmarkLink(final Bookmark bookmark) {
        RootWireframe.openLinkInBrowser(context, bookmark.getUrl());
    }

    @Override
    public void previewBookmark(final Bookmark bookmark) {
        if (bookmark.isReadable()) {
            wireframe.presentPreviewInterface(context, bookmark);
        }
    }

    @Override
    public void editBookmark(final Bookmark bookmark) {
        wireframe.presentEditInterface(context, bookmark);
    }

    @Override
    public void openBookmark(Bookmark bookmark) {
        wireframe.presentBookmarkInterface(context, bookmark);
    }

    @Override
    public void searchSubmit(String query) {
        search.setQuery(query);
        view.hideKeyboard();
        update();
    }



    @Override
    public void searchChange(String query) {
        search.setQuery(query);
        update();
    }

    @Override
    public void filterByName() {
        changeSearchType(Search.Filter.NAME);
    }

    @Override
    public void filterByDate() {
        changeSearchType(Search.Filter.DATE);
    }

    private void changeSearchType(int type) {
        changeSearchType(type, true);
    }

    private void changeSearchType(int type, boolean shouldUpdate) {
        search.setFilter(type);
        view.setCurrentFilter(type);
        if (shouldUpdate) {
            update();
        }
    }

    // -- Search


    // --

    public void load() {
        view.showLoading();
        refresh();
    }

    public void refresh() {

        view.showListRefreshDialog();
        interactor
                .getBookmarks(new RestCallback<List<Bookmark>>() {
                    @Override
                    public void success(List<Bookmark> bookmarksResponse) {
                        bookmarksList = bookmarksResponse;
                        update();
                        view.hideListRefreshDialog();
                        view.hideLoading();
                    }

                    @Override
                    public void failure(RestError restError) {
                        view.hideListRefreshDialog();
                        view.hideLoading();

                        Logger.e("Error while getting data: ".concat(String.valueOf(restError)));

                        view.showLoadingErrorMessage();
                        view.showNoContentMessage();

                        update();
                    }
                });
    }

    public void update() {
        List<Bookmark> bookmarks;

        if (null == this.bookmarksList) {
            view.showNoContentMessage();
            return ;
        }
        bookmarks = (List<Bookmark>) search.search(this.bookmarksList);

        view.reloadEntries(bookmarks);
    }

}
