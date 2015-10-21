package bm.bookmark_manager.view.bm_list;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.api.RestError;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.tools.Tools;
import bm.bookmark_manager.common.tools.search.Search;
import bm.bookmark_manager.common.view.Presenter;
import bm.bookmark_manager.common.view.RootWireframe;

public class BmListPresenter extends Presenter<BmListViewInterface, BmListWireframe, BmListInteractor>
        implements BmListModuleInterface {

    List<Bookmark> bookmarksList;
    List<Tag> tagsList;

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
        updateBookmarkListView();
    }

    @Override
    public void searchChange(String query) {
        search.setQuery(query);
        updateBookmarkListView();
    }

    @Override
    public void filterByName() {
        changeSearchType(Search.Filter.NAME);
    }

    @Override
    public void filterByDate() {
        changeSearchType(Search.Filter.DATE);
    }

    @Override
    public void openEditBookmarkTags(final Bookmark bookmark) {

        if (tagsList != null) {

            // -- copy the list of tags
            List<Tag> copyOfTagsList = Tools.TagTools.cloneTagList(tagsList);

            // -- select tags that the bookmark have
            for (Tag tag : copyOfTagsList) {
                if (bookmark.haveTag(tag)) {
                    tag.select();
                }
            }

            view.displayChooseBookmarkPicker(bookmark, copyOfTagsList);
        }
        else {
            view.showLoading();
            interactor.getTags(new RestCallback<List<Tag>>() {
                @Override
                public void success(List<Tag> tags) {
                    tagsList = tags;
                    openEditBookmarkTags(bookmark); // retry
                    view.hideLoading();
                }

                @Override
                public void failure(RestError restError) {
                    tagsList = null;
                    view.hideLoading();
                    view.showLoadingTagsErrorMessage();
                }
            });
        }
    }

    @Override
    public void chooseBookmarkTags(final Bookmark bookmark, List<Tag> tags) {
        bookmark.setTags(tags);

        view.showLoading();
        interactor.putBookmark(bookmark, new RestCallback<Bookmark>() {
            @Override
            public void success(Bookmark updatedBookmark) {
                // updateBookmarkListView bookmark on list
                bookmarksList.remove(bookmark);
                bookmarksList.add(updatedBookmark);

                updateBookmarkListView();

                view.hideLoading();
                view.displayBookmarkUpdatedMessage();
            }

            @Override
            public void failure(RestError restError) {
                view.hideLoading();
                view.displayBookmarkUpdateErrorMessage(restError.getMessage());
            }
        });
    }

    // -- Search tools

    private void changeSearchType(int type) {
        changeSearchType(type, true);
    }

    private void changeSearchType(int type, boolean shouldUpdate) {
        search.setFilter(type);
        view.setCurrentFilter(type);
        if (shouldUpdate) {
            updateBookmarkListView();
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
                        updateBookmarkListView();

                        view.hideListRefreshDialog();
                        view.hideLoading();

                        // -- get tags in background (totally async)
                        interactor.getTags(new RestCallback<List<Tag>>() {
                            @Override
                            public void success(List<Tag> tags) {
                                tagsList = tags;
                            }

                            @Override
                            public void failure(RestError restError) {
                                view.showLoadingTagsErrorMessage();
                            }
                        });
                    }

                    @Override
                    public void failure(RestError restError) {
                        view.hideListRefreshDialog();
                        view.hideLoading();

                        Logger.e("Error while getting data: ".concat(String.valueOf(restError)));

                        view.showLoadingErrorMessage();
                        view.showNoContentMessage();

                        updateBookmarkListView();
                    }
                });
    }

    public void updateBookmarkListView() {
        List<Bookmark> bookmarks;

        if (null == this.bookmarksList) {
            view.showNoContentMessage();
            return ;
        }
        bookmarks = (List<Bookmark>) search.search(this.bookmarksList);

        view.reloadEntries(bookmarks);
    }

}
