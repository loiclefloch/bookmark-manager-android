package bm.bookmark_manager.view.bm_form;

import bm.bookmark_manager.common.api.ApiManager;
import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.model.Bookmark;

public class BmFormInteractor {

    public void createNewBookmark(Bookmark bookmark, RestCallback<Bookmark> callback) {
        ApiManager.getInstance().postBookmark(bookmark, callback);
    }

    public void updateBookmark(Bookmark bookmark, RestCallback<Bookmark> callback) {
        ApiManager.getInstance().putBookmark(bookmark, callback);
    }
}
