package bm.bookmark_manager.view.bm_list;

import java.util.List;

import bm.bookmark_manager.common.model.Bookmark;

public interface BmListViewInterface {
    void reloadEntries(List<Bookmark> bookmarkList);
    void showNoContentMessage();
    void showUpcomingDisplayData();
    void showLoadingErrorMessage();
}
