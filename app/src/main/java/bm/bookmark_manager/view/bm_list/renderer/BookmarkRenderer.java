package bm.bookmark_manager.view.bm_list.renderer;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.model.Tag;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public abstract class BookmarkRenderer extends Renderer<Bookmark> {

    public interface OnBookmarkAction {
        void onBookmarkClicked(Bookmark bookmark);

        void onBookmarkLongClicked(Bookmark bookmark);
    }

    private final Context context;

    private OnBookmarkAction listener;

    public BookmarkRenderer(Context context) {
        this.context = context;
    }

    @Bind(R.id.bookmark_title)
    TextView title;

    @Bind(R.id.bookmark_description)
    TextView description;

    @Bind(R.id.bookmark_tag_list_grid_layout)
    GridLayout tagListLayout;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(R.layout.renderer__bookmark, parent, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @OnClick(R.id.bookmark_row)
    void onBookmarkClicked() {
        if (listener != null) {
            Bookmark bookmark = getContent();
            listener.onBookmarkClicked(bookmark);
        }
    }

    @OnLongClick(R.id.bookmark_row)
    boolean onBookmarkLongClicked() {
        if (listener != null) {
            Bookmark bookmark = getContent();
            listener.onBookmarkLongClicked(bookmark);
            return true;
        }
        return false;
    }

    @Override
    public void render() {
        Bookmark bookmark = getContent();

        renderTitle(bookmark);
        renderDescription(bookmark);
        renderTagList(bookmark);
    }

    private void renderDescription(Bookmark bookmark) {
        if (!TextUtils.isEmpty(bookmark.getDescription())) {
            this.description.setText(bookmark.getDescription());
            this.description.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(bookmark.getNotes())) {
            this.description.setText(bookmark.getNotes());
            this.description.setVisibility(View.VISIBLE);
        } else {
            this.description.setVisibility(View.GONE);
        }
    }

    private void renderTitle(Bookmark bookmark) {
        String title;

        if (!TextUtils.isEmpty(bookmark.getName())) {
            title = bookmark.getName();
        } else {
            title = bookmark.getTitle();
        }
        this.title.setText(title);
    }

    public void renderTagList(Bookmark bookmark) {

        tagListLayout.removeAllViews();

        if (bookmark.getTags().isEmpty()) {
            tagListLayout.setVisibility(View.GONE);
        } else {
            tagListLayout.setVisibility(View.VISIBLE);
        }

        int i = 0;

        int size = bookmark.getTags().size();

        if (size > 0) {
            tagListLayout.setColumnCount(size);
            for (Tag tag : bookmark.getTags()) {

                TextView tagView = new TextView(context);
                tagView.setText(tag.getName());
                tagView.setTextColor(Color.WHITE);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 10;
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(0);
                tagView.setLayoutParams(param);

                tagView.setPadding(8, 2, 8, 2);
                tagView.setBackgroundColor(Color.parseColor(tag.getColor()));

                tagListLayout.addView(tagView);
                ++i;
            }
        }

    }

    public void setListener(OnBookmarkAction listener) {
        this.listener = listener;
    }
}
