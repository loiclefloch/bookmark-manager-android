package bm.bookmark_manager.view.bm_preview;

import android.os.Bundle;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.view.BaseAppCompatActivity;
import butterknife.Bind;

public class BmPreviewView extends BaseAppCompatActivity
        implements BmPreviewViewInterface {

    BmPreviewModuleInterface presenter;

    // -- views
    @Bind(R.id.bm_preview__web_view)
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(R.layout.activity__bm_preview, R.id.bm_preview__);
        initToolbar(getString(R.string.activity_preview_view_title));

        presenter = new BmPreviewPresenter(this, this);
        presenter.handleIntent(getIntent());

        webView.getSettings().setJavaScriptEnabled(false);
    }

    @Override
    public void setHtml(String html) {
        Logger.i(html);
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

}
