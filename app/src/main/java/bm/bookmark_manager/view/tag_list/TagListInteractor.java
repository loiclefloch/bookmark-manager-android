package bm.bookmark_manager.view.tag_list;

import java.util.List;

import bm.bookmark_manager.common.api.ApiManager;
import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.model.Tag;

public class TagListInteractor {
    public void getTags(RestCallback<List<Tag>> restCallback) {
        ApiManager.getInstance().getTags(restCallback);
    }
}
