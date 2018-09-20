/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.logging.Logger;

/**
 *
 */
public final class DeployProcessActionListener implements JbpmActionListener {
    private final ValueExpression archiveExpression;
    private final ValueExpression targetExpression;

    public DeployProcessActionListener(final ValueExpression archiveExpression, final ValueExpression targetExpression) {
        this.archiveExpression = archiveExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "deployProcess";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final InputStream inputStream;
            final Object archiveValue = archiveExpression.getValue(elContext);
            if (archiveValue == null) {
                context.setError("Error deploying process", "Archive is null");
                return;
            }
            if (archiveValue instanceof InputStream) {
                inputStream = (InputStream) archiveValue;
            } else if (archiveValue instanceof byte[]) {
                final byte[] bytes = (byte[]) archiveValue;
                inputStream = new ByteArrayInputStream(bytes);
            } else {
                context.setError("Error deploying process", "Type of 'archive' attribute is not recognized");
                return;
            }
            final ProcessDefinition processDefinition;
            try {
                ZipInputStream zis = new ZipInputStream(inputStream);
                try {
                    final JbpmContext jbpmContext = context.getJbpmContext();
                    processDefinition = ProcessDefinition.parseParZipInputStream(zis);
                    jbpmContext.deployProcessDefinition(processDefinition);
                } finally {
                    try {
                        zis.close();
                    } catch (IOException e) {
                        log.warning("Error closing zip input stream after deploy: " + e.getMessage());
                    }
                }
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warning("Error closing input stream after deploy: " + e.getMessage());
                }
            }
            context.addSuccessMessage("New process deployed");
            context.getJbpmContext().getSession().flush();
            if (targetExpression != null) {
                targetExpression.setValue(elContext, processDefinition);
            }
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error deploying process", ex);
            return;
        }
    }

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.handler.DeployProcessActionHandler");
}
