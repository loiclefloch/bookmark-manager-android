package bm.bookmark_manager.view.bm_view;

import java.util.List;

import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.view.ViewInterface;

public interface BmViewViewInterface extends ViewInterface {
    void finish();

    void setTitle(String title);
    void setDescription(String description);
    void setNotes(String notes);
    void setTagList(List<Tag> tags);
}
