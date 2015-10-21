package bm.bookmark_manager.view.bm_list;

import java.util.List;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.view.ViewInterface;

public interface BmListViewInterface extends ViewInterface {
    void reloadEntries(List<Bookmark> bookmarkList);
    void showNoContentMessage();
    void showUpcomingDisplayData();

    void showLoadingTagsErrorMessage();
    void showLoadingErrorMessage();

    void displayBookmarkUpdatedMessage();
    void displayBookmarkUpdateErrorMessage(String error);

    void showListRefreshDialog();
    void hideListRefreshDialog();


    /**
     * Set the current search filter apply
     * @param filter
     */
    void setCurrentFilter(int filter);

    /**
     * Set the current search order apply
     * @param order
     */
    void setCurrentOrder(int order);

    void displayChooseBookmarkPicker(Bookmark bookmark, List<Tag> tagList);
}
