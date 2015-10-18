package bm.bookmark_manager.view.bm_view;

import android.content.Intent;

public interface BmViewModuleInterface {

    void handleIntent(Intent intent);

    void openBookmarkLink();
    void editBookmark();
    void previewBookmark();
}
