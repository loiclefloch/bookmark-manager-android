package bm.bookmark_manager.view.bm_view;

import android.os.Bundle;
import android.widget.TextView;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.view.BaseAppCompatActivity;
import butterknife.Bind;

public class BmViewView extends BaseAppCompatActivity
        implements BmViewViewInterface {

    BmViewPresenter presenter;

    // -- views

    @Bind(R.id.bm_view__title)
    TextView titleTextView;
    @Bind(R.id.bm_view__url)
    TextView urlTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(R.layout.activity__bm_view, R.id.bm_view__);
        initToolbar(getString(R.string.activity_bm_view_title)); // temporary title

        presenter = new BmViewPresenter(this, this);
        presenter.handleIntent(getIntent());
    }

    // --- BmViewViewInterface

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setUrl(String url) {
        urlTextView.setText(url);
    }
}

