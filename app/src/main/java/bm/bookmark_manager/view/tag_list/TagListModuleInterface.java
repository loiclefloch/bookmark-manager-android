package bm.bookmark_manager.view.tag_list;

import bm.bookmark_manager.common.model.Tag;

public interface TagListModuleInterface {

    void searchChange(String s);

    void presentAddNewTag();

    void presentEditTag(Tag tag);

    void refresh();

    void searchSubmit(String query);

    void load();

    void filterByName();

    void filterByDate();
}
