package bm.bookmark_manager.view.bm_view;

import bm.bookmark_manager.common.view.ViewInterface;

public interface BmViewViewInterface extends ViewInterface {
    void finish();

    void setTitle(String title);
    void setUrl(String url);
}
