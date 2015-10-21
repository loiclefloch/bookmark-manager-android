package bm.bookmark_manager.view.bm_list;

import java.util.List;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.model.Tag;

interface BmListModuleInterface {
    void presentAddNewBookmark();
    void openBookmarkLink(final Bookmark bookmark);
    void previewBookmark(final Bookmark bookmark);
    void editBookmark(final Bookmark bookmark);
    void openBookmark(final Bookmark bookmark);

    void searchSubmit(String query);
    void searchChange(String query);

    void filterByName();
    void filterByDate();

    void openEditBookmarkTags(Bookmark bookmark);

    void chooseBookmarkTags(Bookmark bookmark, List<Tag> tags);
}
