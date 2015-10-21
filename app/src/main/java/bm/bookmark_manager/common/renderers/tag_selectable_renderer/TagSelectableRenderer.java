package bm.bookmark_manager.common.renderers.tag_selectable_renderer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Tag;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class TagSelectableRenderer extends Renderer<Tag> {

    public interface OnSelectAction {
        void onSelect(Tag tag);
    }

    private final Context context;

    private OnSelectAction listener;

    public TagSelectableRenderer(Context context) {
        this.context = context;
    }

    @Bind(R.id.tag_title)
    TextView title;

    @Bind(R.id.tag_color_view)
    View color;

    @Bind(R.id.tag_selected_icon)
    ImageView selectedIcon;

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(R.layout.renderer__tag_selectable, parent, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @OnClick(R.id.tag_row)
    void onTagClicked() {
        if (listener != null) {
            Tag tag = getContent();
            listener.onSelect(tag);
        }
    }

    @Override
    public void render() {
        Tag tag = getContent();

        renderTitle(tag);
        renderColor(tag);
        renderSelected(tag);
    }

    protected void renderSelected(Tag tag) {
        if (tag.isSelected()) {
            selectedIcon.setVisibility(View.VISIBLE);
        }
        else {
            selectedIcon.setVisibility(View.GONE);
        }
    }

    private void renderColor(Tag tag) {
        this.color.setBackgroundColor(Color.parseColor(tag.getColor()));
    }

    private void renderTitle(Tag tag) {
       this.title.setText(tag.getName());
    }

    public void setListener(OnSelectAction listener) {
        this.listener = listener;
    }
}
