package bm.bookmark_manager.view.bm_form;

import bm.bookmark_manager.common.view.ViewInterface;

public interface BmFormViewInterface extends ViewInterface {

    void finish();

    void setTitle(String title);
    void setUrl(String url);
}
