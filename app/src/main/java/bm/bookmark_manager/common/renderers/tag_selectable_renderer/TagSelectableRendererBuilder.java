package bm.bookmark_manager.common.renderers.tag_selectable_renderer;

import android.content.Context;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import bm.bookmark_manager.common.model.Tag;

public class TagSelectableRendererBuilder extends RendererBuilder<Tag> {

    @Inject
    public TagSelectableRendererBuilder(Context context, TagSelectableRenderer.OnSelectAction onSelectAction) {
        Collection<Renderer<Tag>> prototypes = getPrototypes(context, onSelectAction);
        setPrototypes(prototypes);
    }

    /**
     * Method to declare Tag-TagRenderer mapping.
     * Normal bookmarks will be rendered using NormalTagRenderer.
     *
     * @param content used to map object-renderers.
     * @return TagRenderer subtype class.
     */
    @Override
    protected Class getPrototypeClass(Tag content) {
        Class prototypeClass;

        prototypeClass = NormalTagSelectableRenderer.class;

        return prototypeClass;
    }

    /**
     * Create a list of prototypes to configure RendererBuilder.
     * The list of Renderer<Tag> that contains all the possible renderers that our RendererBuilder
     * is going to use.
     *
     * @return Renderer<Tag> prototypes for RendererBuilder.
     */
    private List<Renderer<Tag>> getPrototypes(Context context,
                                                TagSelectableRenderer.OnSelectAction onSelectListener) {

        List<Renderer<Tag>> prototypes = new LinkedList<Renderer<Tag>>();
        NormalTagSelectableRenderer normalTagRenderer = new NormalTagSelectableRenderer(context);
        normalTagRenderer.setListener(onSelectListener);
        prototypes.add(normalTagRenderer);

        return prototypes;
    }
}