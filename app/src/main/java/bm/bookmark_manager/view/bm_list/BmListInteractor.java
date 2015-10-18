package bm.bookmark_manager.view.bm_list;

import java.util.List;

import bm.bookmark_manager.common.api.ApiManager;
import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.model.Bookmark;

public class BmListInteractor {

    public void getBookmarks(RestCallback<List<Bookmark>> callback) {
        ApiManager.getInstance().getBookmarks(callback);
    }
}
