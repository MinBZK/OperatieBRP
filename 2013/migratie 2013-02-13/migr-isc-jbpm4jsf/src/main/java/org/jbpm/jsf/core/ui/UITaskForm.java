/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.ui;

import org.jboss.gravel.common.ui.HasJsfCoreAttributes;

import javax.faces.component.UICommand;

/**
 *
 */
public final class UITaskForm extends UICommand implements HasJsfCoreAttributes {
    public static final String COMPONENT_FAMILY = "jbpm4jsf.core";
    public static final String COMPONENT_TYPE = "jbpm4jsf.core.TaskForm";
    public static final String RENDERER_TYPE = null;

    public UITaskForm() {
        setRendererType(RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }
}
