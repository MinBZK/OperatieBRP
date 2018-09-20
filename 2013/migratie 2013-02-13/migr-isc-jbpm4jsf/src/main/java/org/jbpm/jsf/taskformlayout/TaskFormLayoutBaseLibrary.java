/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskformlayout;

import org.jbpm.jsf.taskformlayout.ui.UIDataForm;
import org.jbpm.jsf.taskformlayout.ui.UIDataCell;
import org.jbpm.jsf.taskformlayout.ui.UIDataArea;
import org.jbpm.jsf.taskformlayout.ui.UIDataSection;
import org.jbpm.jsf.taskformlayout.renderer.DataFormRenderer;

import com.sun.facelets.tag.AbstractTagLibrary;

import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.faces.render.RenderKit;

/**
 *
 */
public abstract class TaskFormLayoutBaseLibrary extends AbstractTagLibrary {
    public TaskFormLayoutBaseLibrary(final String namespace) {
        super(namespace);

        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final Application application = facesContext.getApplication();
        final RenderKit renderKit = facesContext.getRenderKit();

        application.addComponent(UIDataForm.COMPONENT_TYPE, UIDataForm.class.getName());
        addComponent("dataform", UIDataForm.COMPONENT_TYPE, UIDataForm.RENDERER_TYPE);
        renderKit.addRenderer(UIDataForm.COMPONENT_FAMILY, UIDataForm.RENDERER_TYPE, new DataFormRenderer());

        application.addComponent(UIDataArea.COMPONENT_TYPE, UIDataArea.class.getName());
        addComponent("dataarea", UIDataArea.COMPONENT_TYPE, UIDataArea.RENDERER_TYPE);

        application.addComponent(UIDataSection.COMPONENT_TYPE, UIDataSection.class.getName());
        addComponent("datasection", UIDataSection.COMPONENT_TYPE, UIDataSection.RENDERER_TYPE);

        application.addComponent(UIDataCell.COMPONENT_TYPE, UIDataCell.class.getName());
        addComponent("datacell", UIDataCell.COMPONENT_TYPE, UIDataCell.RENDERER_TYPE);
    }
}
