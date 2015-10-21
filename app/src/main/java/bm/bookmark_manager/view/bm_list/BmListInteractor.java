package bm.bookmark_manager.view.bm_list;

import java.util.List;

import bm.bookmark_manager.common.api.ApiManager;
import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.model.Tag;

public class BmListInteractor {

    public void getBookmarks(RestCallback<List<Bookmark>> callback) {
        ApiManager.getInstance().getBookmarks(callback);
    }

    public void putBookmark(Bookmark bookmark, RestCallback<Bookmark> callback) {
        ApiManager.getInstance().putBookmark(bookmark, callback);
    }

    public void getTags(RestCallback<List<Tag>> callback) {
        ApiManager.getInstance().getTags(callback);
    }

}
