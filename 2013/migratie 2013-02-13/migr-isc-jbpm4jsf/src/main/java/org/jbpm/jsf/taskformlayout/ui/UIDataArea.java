/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskformlayout.ui;

public final class UIDataArea extends UIDataSection {

    public static final String COMPONENT_TYPE = "jbpm4jsf.tfl.DataArea";
    public static final String COMPONENT_FAMILY = "jbpm4jsf.tfl";
    public static final String RENDERER_TYPE = null;

    private static final long serialVersionUID = 1L;

    public UIDataArea() {
        setRendererType(RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
