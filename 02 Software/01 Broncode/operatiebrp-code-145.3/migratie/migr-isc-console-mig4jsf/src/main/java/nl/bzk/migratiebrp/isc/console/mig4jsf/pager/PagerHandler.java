/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pager;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;

/**
 * Handler voor pager tag.
 */
@TldTag(name = "pager", description = "Calculate paging information for use by another component.",
        attributes = {@TldAttribute(name = "target",
                description = "An EL expression into which the paging information will be stored. The paging"
                        + " information is stored in a bean with the following properties: <ul>"
                        + "<li><code>page</code> - the current actual page number (if the requested page is "
                        + "past the end, it will be clamped to this value</li>"
                        + "<li><code>totalPages</code> - the total number of pages available (0 for an empty "
                        + "data set)</li>"
                        + "<li><code>pageSize</code> - the number of results per page</li>"
                        + "<li><code>thisPageSize</code> - the number of results on the current page</li>"
                        + "<li><code>firstPage</code> - a boolean that is true if this is the first page</li>"
                        + "<li><code>lastPage</code> - a boolean that is true if this is the last page</li>"
                        + "<li><code>first</code> - the index of the first item in the collection to "
                        + "display</li>"
                        + "</ul>", required = true, deferredType = Object.class),
                @TldAttribute(name = "pageSize",
                        description = "The number of results that will be displayed per page.  If not given, defaults" + " to 10.",
                        deferredType = int.class),
                @TldAttribute(name = "page", description = "The current page number.  If not given, defaults to 1.",
                        deferredType = int.class)})
public final class PagerHandler extends TagHandler {
    private final TagAttribute targetTagAttribute;
    private final TagAttribute pageSizeTagAttribute;
    private final TagAttribute pageTagAttribute;

    /**
     * Constructor.
     * @param config config
     */
    public PagerHandler(final TagConfig config) {
        super(config);
        targetTagAttribute = getRequiredAttribute("target");
        pageSizeTagAttribute = getAttribute("pageSize");
        pageTagAttribute = getAttribute("page");
    }

    @Override
    public void apply(final FaceletContext ctx, final UIComponent parent) {
        if (!(parent instanceof ActionSource)) {
            throw new TagException(tag, "Parent component is not an ActionSource");
        }
        if (ComponentSupport.isNew(parent)) {
            ((ActionSource) parent).addActionListener(new PagerActionListener(
                    pageTagAttribute == null ? null : pageTagAttribute.getValueExpression(ctx, int.class),
                    pageSizeTagAttribute == null ? null : pageSizeTagAttribute.getValueExpression(ctx, int.class),
                    targetTagAttribute.getValueExpression(ctx, Object.class)));
        }
    }
}
