package bm.bookmark_manager.common.view;

public interface ViewInterface {

    void displayError(String message);
    void displayToast(String title);

    void setToolbarTitle(String title);

    void showLoading();
    void hideLoading();

    void hideKeyboard();
}
