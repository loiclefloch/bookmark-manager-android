package bm.bookmark_manager.common.renderers.bookmark_renderer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.tools.ViewTools;
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
       this.title.setText(bookmark.getMainTitle());
    }

    public void renderTagList(Bookmark bookmark) {
        ViewTools.renderTagList(context, tagListLayout, bookmark.getTags());
    }

    public void setListener(OnBookmarkAction listener) {
        this.listener = listener;
    }
}
