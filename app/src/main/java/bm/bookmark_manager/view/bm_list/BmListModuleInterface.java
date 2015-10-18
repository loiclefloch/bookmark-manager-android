package bm.bookmark_manager.view.bm_list;

import bm.bookmark_manager.common.model.Bookmark;

interface BmListModuleInterface {
    void presentAddNewBookmark();
    void openBookmarkLink(final Bookmark bookmark);
    void previewBookmark(final Bookmark bookmark);
    void editBookmark(final Bookmark bookmark);
    void openBookmark(final Bookmark bookmark);

    void searchSubmit(String query);
    void searchChange(String query);
}
