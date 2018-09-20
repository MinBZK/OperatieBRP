/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * Copyright 2004-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.js.ajax.tiles2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Attribute;
import org.apache.tiles.Attribute.AttributeType;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Definition;
import org.apache.tiles.context.ChainedTilesRequestContextFactory;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.springframework.js.ajax.AjaxHandler;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.tiles2.TilesView;


/**
 * Tiles view implementation that is able to handle partial rendering for Spring
 * Javascript Ajax requests.
 *
 * <p>
 * This implementation uses the {@link SpringJavascriptAjaxHandler} by default to determine whether the current request
 * is an Ajax request. On an Ajax request, a "fragments" parameter will be extracted from the request in order to
 * determine which attributes to render from the current tiles view.
 * </p>
 *
 * @author Jeremy Grelle
 * @author David Winterfeldt
 */
//CHECKSTYLE:OFF
public class AjaxTiles21View extends TilesView {

    private static final String        FRAGMENTS_PARAM = "fragments";

    private TilesRequestContextFactory tilesRequestContextFactory;

    private AjaxHandler                ajaxHandler     = new SpringJavascriptAjaxHandler();

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (tilesRequestContextFactory == null) {
            tilesRequestContextFactory = new ChainedTilesRequestContextFactory();
            tilesRequestContextFactory.init(new HashMap<String, String>());
        }
    }

    public AjaxHandler getAjaxHandler() {
        return ajaxHandler;
    }

    public void setAjaxHandler(final AjaxHandler ajaxHandler) {
        this.ajaxHandler = ajaxHandler;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void renderMergedOutputModel(final Map model, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {

        ServletContext servletContext = getServletContext();
        if (ajaxHandler.isAjaxRequest(request, response)) {

            String[] attrNames = getRenderFragments(model, request, response);
            if (attrNames.length == 0) {
                logger.warn("An Ajax request was detected, but no fragments were specified to be re-rendered.  "
                    + "Falling back to full page render.  This can cause unpredictable results when processing "
                    + "the ajax response on the client.");
                super.renderMergedOutputModel(model, request, response);
                return;
            }

            BasicTilesContainer container =
                (BasicTilesContainer) ServletUtil.getCurrentContainer(request, servletContext);
            if (container == null) {
                throw new ServletException("Tiles container is not initialized. "
                    + "Have you added a TilesConfigurer to your web application context?");
            }

            exposeModelAsRequestAttributes(model, request);
            JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));

            TilesRequestContext tilesRequestContext =
                tilesRequestContextFactory.createRequestContext(container.getApplicationContext(), new Object[] {
                    request, response });
            Definition compositeDefinition =
                container.getDefinitionsFactory().getDefinition(getUrl(), tilesRequestContext);
            Map flattenedAttributeMap = new HashMap();
            flattenAttributeMap(container, tilesRequestContext, flattenedAttributeMap, compositeDefinition, request,
                    response);

            // initialize the session before rendering any fragments. Otherwise
            // views that require the session which has
            // not otherwise been initialized will fail to render
            request.getSession();
            response.flushBuffer();
            for (int i = 0; i < attrNames.length; i++) {
                Attribute attributeToRender = (Attribute) flattenedAttributeMap.get(attrNames[i]);

                if (attributeToRender == null) {
                    throw new ServletException("No tiles attribute with a name of '" + attrNames[i]
                        + "' could be found for the current view: " + this);
                } else {

                    container.render(attributeToRender, response.getWriter(), new Object[] { request, response });
                }
            }
        } else {
            super.renderMergedOutputModel(model, request, response);
        }
    }

    @SuppressWarnings("rawtypes")
    protected String[] getRenderFragments(final Map model, final HttpServletRequest request, final HttpServletResponse response) {
        String attrName = request.getParameter(FRAGMENTS_PARAM);
        String[] renderFragments = StringUtils.commaDelimitedListToStringArray(attrName);
        return StringUtils.trimArrayElements(renderFragments);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void flattenAttributeMap(final BasicTilesContainer container, final TilesRequestContext requestContext,
            final Map resultMap, final Definition compositeDefinition, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception
    {
        if (compositeDefinition.getAttributes() != null && compositeDefinition.getAttributes().size() > 0) {
            Iterator i = compositeDefinition.getAttributes().keySet().iterator();
            while (i.hasNext()) {
                Object key = i.next();
                Attribute attr = compositeDefinition.getAttributes().get(key);
                AttributeType attrType =
                    attr.getType() != null ? attr.getType() : detectType(container, requestContext, attr);
                if (AttributeType.DEFINITION.equals(attrType) || AttributeType.TEMPLATE.equals(attrType)) {
                    resultMap.put(key, attr);
                    if (AttributeType.DEFINITION.equals(attrType)) {
                        Definition nestedDefinition =
                            container.getDefinitionsFactory().getDefinition(attr.getValue().toString(), requestContext);
                        if (nestedDefinition != null && nestedDefinition != compositeDefinition) {
                            flattenAttributeMap(container, requestContext, resultMap, nestedDefinition, request,
                                    response);
                        }
                    }
                }
            }
        }

        // Process dynamic attributes
        AttributeContext attributeContext = container.getAttributeContext(new Object[] { request, response });

        for (Iterator i = attributeContext.getAttributeNames(); i.hasNext();) {
            String key = (String) i.next();
            Attribute attr = attributeContext.getAttribute(key);
            resultMap.put(key, attr);
        }
    }

    private AttributeType detectType(final BasicTilesContainer container, final TilesRequestContext requestContext, final Attribute attr)
            throws DefinitionsFactoryException
    {
        if (attr.getValue() instanceof String) {
            if (container.getDefinitionsFactory().getDefinition(attr.getValue().toString(), requestContext) != null) {
                return AttributeType.DEFINITION;
            } else if (attr.getValue().toString().startsWith("/")) {
                return AttributeType.TEMPLATE;
            } else {
                return AttributeType.STRING;
            }
        }
        return AttributeType.OBJECT;
    }
}
