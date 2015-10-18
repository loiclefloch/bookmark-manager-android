package bm.bookmark_manager.common.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import bm.bookmark_manager.R;

public class BaseFragment extends Fragment {

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getText(R.string.loading));
        progressDialog.setCancelable(false);
    }

}
