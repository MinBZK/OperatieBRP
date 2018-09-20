/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskformlayout.ui;

import org.jboss.gravel.common.ui.UIGravelBase;

import javax.faces.context.FacesContext;

import java.io.Serializable;

public class UIDataSection extends UIGravelBase {

    public static final String COMPONENT_TYPE = "jbpm4jsf.tfl.DataSection";
    public static final String COMPONENT_FAMILY = "jbpm4jsf.tfl";
    public static final String RENDERER_TYPE = null;

    private static final long serialVersionUID = 1L;

    public UIDataSection() {
        setRendererType(RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    // properties

    private String headerClass;
    private String headerStyle;
    private String footerClass;
    private String footerStyle;
    private String styleClass;
    private String style;

    public String getHeaderClass() {
        return getAttributeValue("headerClass", headerClass);
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

    public String getFooterClass() {
        return getAttributeValue("footerClass", footerClass);
    }

    public void setFooterClass(final String footerClass) {
        this.footerClass = footerClass;
    }

    public String getFooterStyle() {
        return getAttributeValue("footerStyle", footerStyle);
    }

    public void setFooterStyle(final String footerStyle) {
        this.footerStyle = footerStyle;
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
        state.footerClass = footerClass;
        state.footerStyle = footerStyle;
        state.styleClass = styleClass;
        state.style = style;
        return state;
    }

    public void restoreState(FacesContext context, Object stateObject) {
        state = ((State)stateObject);
        headerClass = state.headerClass;
        headerStyle = state.headerStyle;
        footerClass = state.footerClass;
        footerStyle = state.footerStyle;
        styleClass = state.styleClass;
        style = state.style;
        super.restoreState(context, state.superState);
    }

    static class State implements Serializable {
        private static final long serialVersionUID = 1L;

        Object superState;

        String headerClass;
        String headerStyle;
        String footerClass;
        String footerStyle;
        String styleClass;
        String style;
    }
}
