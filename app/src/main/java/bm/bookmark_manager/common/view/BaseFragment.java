package bm.bookmark_manager.common.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.tools.ViewTools;

public class BaseFragment extends Fragment implements ViewInterface {

    ProgressDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProgressDialog();
    }

    private void initProgressDialog() {
        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage(getText(R.string.loading));
        loadingDialog.setCancelable(false);
    }

    // -- ViewInterface Handle errors

    @Override
    public void displayError(String message) {
        if (getView() != null) {
            ViewTools.displayError(getView(), message);
        }
    }

    @Override
    public void displayToast(String title) {
        ViewTools.displayToast(getContext(), title);
    }

    @Override
    public void setToolbarTitle(String title) {
        // TODO: setToolbarTitle
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.hide();
    }


}
