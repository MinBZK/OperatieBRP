/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskformlayout.ui;

import org.jboss.gravel.common.ui.UIGravelBase;

import javax.faces.context.FacesContext;

import java.io.Serializable;

public final class UIDataCell extends UIGravelBase {

    public static final String COMPONENT_TYPE = "jbpm4jsf.tfl.DataCell";
    public static final String COMPONENT_FAMILY = "jbpm4jsf.tfl";
    public static final String RENDERER_TYPE = null;

    private static final long serialVersionUID = 1L;

    public UIDataCell() {
        setRendererType(RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    // properties

    private String headerClass;
    private String headerStyle;
    private String styleClass;
    private String style;

    public String getHeaderClass() {
        return headerClass;
    }

    public void setHeaderClass(final String headerClass) {
        this.headerClass = headerClass;
    }

    public String getHeaderStyle() {
        return getAttributeValue("headerStyle", headerStyle);
    }

    public void setHeaderStyle(final String headerStyle) {
        this.headerStyle = headerStyle;
    }

    public String getStyleClass() {
        return getAttributeValue("styleClass", styleClass);
    }

    public void setStyleClass(final String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyle() {
        return getAttributeValue("style", style);
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    // state mgmt

    private State state;

    public Object saveState(FacesContext context) {
        if (state == null) {
            state = new State();
        }
        state.superState = super.saveState(context);
        state.headerClass = headerClass;
        state.headerStyle = headerStyle;
        state.styleClass = styleClass;
        state.style = style;
        return state;
    }

    public void restoreState(FacesContext context, Object stateObject) {
        state = ((State)stateObject);
        headerClass = state.headerClass;
        headerStyle = state.headerStyle;
        styleClass = state.styleClass;
        style = state.style;
        super.restoreState(context, state.superState);
    }

    static class State implements Serializable {
        private static final long serialVersionUID = 1L;

        Object superState;

        String headerClass;
        String headerStyle;
        String styleClass;
        String style;
    }
}
