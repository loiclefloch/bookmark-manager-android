package bm.bookmark_manager.view.bm_form;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.view.BaseAppCompatActivity;
import butterknife.Bind;
import butterknife.OnClick;

public class BmFormView extends BaseAppCompatActivity implements BmFormViewInterface {

    BmFormPresenter presenter;

    // -- views
    @Bind(R.id.bm_add__title_edit_text)
    EditText titleEditText;

    @Bind(R.id.bm_add__url_edit_text)
    EditText urlEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(R.layout.activity__bm_add, R.id.bm_add__);
        initToolbar(getString(R.string.activity_bm_add_title));

        presenter = new BmFormPresenter(this, this);
        presenter.handleIntent(getIntent());
    }

    @OnClick(R.id.bm_add__validate_button)
    void validateOnClick(View v) {
        presenter.saveBookmark(titleEditText.getText().toString(), urlEditText.getText().toString());
    }

    // --- BmFormViewInterface

    @Override
    public void setTitle(String title) {
        titleEditText.setText(title);
    }

    @Override
    public void setUrl(String url) {
        urlEditText.setText(url);
    }

}
