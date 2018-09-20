/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.springframework.web.servlet.tags.form.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.PropertyAccessor;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;


/**
 * Deze custom tag zorgt ervoor dat de form velden gebonden kan worden binnen
 * een tile die met Ajax call wordt geladen zonder dat de html form element op
 * de pagina geschreven hoeft te worden.
 *
 *
 */
public class FormBindTag extends FormTag {

    private static final long serialVersionUID = 4562681109052710374L;

    /** Caching a previous nested path, so that it may be reset. */
    private String            previousNestedPath;

    /**
     * Deze methode is een copy van de overschreven methode van de FormTag,
     * enige verschil is dat het niks schrijft naar de pagina maar alleen de
     * command attributen bschikbaar stelt voor de form bindings.
     *
     * @param tagWriter de TagWriter
     * @return EVAL_BODY_INCLUDE
     * @throws JspException JspException
     */
    @Override
    protected int writeTagContent(final TagWriter tagWriter) throws JspException {
        // Expose the form object name for nested tags...
        String modelAttribute = resolveModelAttribute();
        this.pageContext.setAttribute(MODEL_ATTRIBUTE_VARIABLE_NAME, modelAttribute, PageContext.REQUEST_SCOPE);
        this.pageContext.setAttribute(COMMAND_NAME_VARIABLE_NAME, modelAttribute, PageContext.REQUEST_SCOPE);

        // Save previous nestedPath value, build and expose current nestedPath
        // value.
        // Use request scope to expose nestedPath to included pages too.
        this.previousNestedPath =
            (String) this.pageContext.getAttribute(NESTED_PATH_VARIABLE_NAME, PageContext.REQUEST_SCOPE);
        this.pageContext.setAttribute(NESTED_PATH_VARIABLE_NAME, modelAttribute
            + PropertyAccessor.NESTED_PROPERTY_SEPARATOR, PageContext.REQUEST_SCOPE);

        return EVAL_BODY_INCLUDE;
    }

    /**
     * Override de doEndTag zodat er geen html /form tag geschreven wordt.
     *
     * @return EVAL_PAGE;
     * @throws JspException JspException
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
