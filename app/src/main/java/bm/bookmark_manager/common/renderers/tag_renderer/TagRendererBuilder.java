package bm.bookmark_manager.common.renderers.tag_renderer;

import android.content.Context;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import bm.bookmark_manager.common.model.Tag;

public class TagRendererBuilder extends RendererBuilder<Tag> {

    @Inject
    public TagRendererBuilder(Context context, TagRenderer.OnTagAction onTagAction) {
        Collection<Renderer<Tag>> prototypes = getPrototypes(context, onTagAction);
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

        prototypeClass = NormalTagRenderer.class;

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
                                                TagRenderer.OnTagAction onTagClickedListener) {

        List<Renderer<Tag>> prototypes = new LinkedList<Renderer<Tag>>();
        NormalTagRenderer likeTagRenderer = new NormalTagRenderer(context);
        likeTagRenderer.setListener(onTagClickedListener);
        prototypes.add(likeTagRenderer);

        return prototypes;
    }
}