package bm.bookmark_manager.common.view.ListPicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.List;

import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.renderers.tag_selectable_renderer.TagSelectableRenderer;
import bm.bookmark_manager.common.renderers.tag_selectable_renderer.TagSelectableRendererBuilder;

public class TagPicker extends Picker<Tag> {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        // -- Setup adapter

        AdapteeCollection<Tag> tagCollection = new ListAdapteeCollection<>();
        adapter = new RendererAdapter<Tag>(
                inflater,
                new TagSelectableRendererBuilder(getContext(), new TagSelectableRenderer.OnSelectAction() {
                    @Override
                    public void onSelect(Tag tag) {

                        if (tag.isSelected()) {
                            selectedList.remove(tag);
                            tag.unselect();
                        }
                        else {
                            selectedList.add(tag);
                            tag.select();
                        }
                        notifyDataSetChanged();
                    }
                }),
                tagCollection);
        setAdapter(adapter);

        List<Tag> list = listener.getData();

        // -- Add already selected tags to the selected list
        if (list != null) {
            for (Tag t : list) {
                if (t.isSelected()) {
                    selectedList.add(t);
                }
            }
        }
        setList(list);

        return view;
    }

}
