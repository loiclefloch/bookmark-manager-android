package bm.bookmark_manager.common.renderers.tag_renderer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Tag;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public abstract class TagRenderer extends Renderer<Tag> {

    public interface OnTagAction {
        void onTagClicked(Tag tag);

        void onTagLongClicked(Tag tag);
    }

    private final Context context;

    private OnTagAction listener;

    public TagRenderer(Context context) {
        this.context = context;
    }

    @Bind(R.id.tag_title)
    TextView title;

    @Bind(R.id.tag_color_view)
    View color;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(R.layout.renderer__tag, parent, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @OnClick(R.id.tag_row)
    void onTagClicked() {
        if (listener != null) {
            Tag tag = getContent();
            listener.onTagClicked(tag);
        }
    }

    @OnLongClick(R.id.tag_row)
    boolean onTagLongClicked() {
        if (listener != null) {
            Tag tag = getContent();
            listener.onTagLongClicked(tag);
            return true;
        }
        return false;
    }

    @Override
    public void render() {
        Tag tag = getContent();

        renderTitle(tag);
        renderColor(tag);
    }

    private void renderColor(Tag tag) {
        this.color.setBackgroundColor(Color.parseColor(tag.getColor()));
    }

    private void renderTitle(Tag tag) {
       this.title.setText(tag.getName());
    }

    public void setListener(OnTagAction listener) {
        this.listener = listener;
    }
}
