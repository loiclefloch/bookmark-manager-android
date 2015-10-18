package bm.bookmark_manager.view.bm_view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.tools.ViewTools;
import bm.bookmark_manager.common.view.BaseAppCompatActivity;
import butterknife.Bind;
import butterknife.OnClick;

public class BmViewView extends BaseAppCompatActivity
        implements BmViewViewInterface {

    BmViewModuleInterface presenter;

    // -- views

    @Bind(R.id.bm_view__title_text_view)
    TextView titleTextView;
    @Bind(R.id.bm_view__description_text_view)
    TextView descriptionTextView;
    @Bind(R.id.bm_view__notes_linear_layout)
    LinearLayout notesLinearLayout;
    @Bind(R.id.bm_view__notes_text_view)
    TextView notesTextView;
    @Bind(R.id.bm_view__tags_grid_layout)
    GridLayout tagListLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(R.layout.activity__bm_view, R.id.bm_view__);
        initToolbar(getString(R.string.activity_bm_view_title)); // temporary title

        presenter = new BmViewPresenter(this, this);
        presenter.handleIntent(getIntent());
    }

//    @OnClick(R.id.bm_view__url)
    void openLink(View v) {
        presenter.openBookmarkLink();
    }

    // --- BmViewViewInterface

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            descriptionTextView.setVisibility(View.GONE);
        }
        else {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(description);
        }
    }

    public void setNotes(String notes) {

        if (TextUtils.isEmpty(notes)) {
            notesLinearLayout.setVisibility(View.GONE);
        }
        else {
            notesLinearLayout.setVisibility(View.VISIBLE);
            notesTextView.setText(notes);
        }
    }

    public void setTagList(List<Tag> tags) {
        ViewTools.renderTagList(BmViewView.this, tagListLayout, tags);
    }

    // -- Events

    @OnClick(R.id.bm_view__open_link_btn)
    void openLinkOnClick(View v) {
        presenter.openBookmarkLink();
    }

    @OnClick(R.id.bm_view__edit_btn)
    void editOnClick(View v) {
        presenter.editBookmark();
    }

    @OnClick(R.id.bm_view__preview_btn)
    void previewOnClick(View v) {
        presenter.previewBookmark();
    }

}

