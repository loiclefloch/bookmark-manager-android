package bm.bookmark_manager.view.tag_list;

import java.util.List;

import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.view.ViewInterface;

public interface TagListViewInterface extends ViewInterface {
    void reloadEntries(List<Tag> tagList);
    void showNoContentMessage();
    void showUpcomingDisplayData();
    void showLoadingErrorMessage();

    void showListRefreshDialog();
    void hideListRefreshDialog();

    void setCurrentFilter(int type);
    void setCurrentOrder(int order);
}
