/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.phase;

import java.util.Map;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.core.impl.JbpmJsfContextImpl;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JbpmPhaseListener implements PhaseListener {
    private static final long serialVersionUID = 1L;

    public void beforePhase(PhaseEvent phaseEvent) {
        log.fine("Before phase " + phaseEvent.getPhaseId().toString());
    }

    public void afterPhase(PhaseEvent phaseEvent) {
        log.fine("After phase " + phaseEvent.getPhaseId().toString());
        final FacesContext facesContext = phaseEvent.getFacesContext();
        final JbpmJsfContextImpl jbpmJsfContext = getJbpmJsfContext(facesContext);
        if (jbpmJsfContext != null) {
            if (facesContext.getResponseComplete()) {
                // No matter what phase, context should be closed.
                closeContext(jbpmJsfContext);
                removeJbpmJsfContext(facesContext);
            } else if (phaseEvent.getPhaseId() == PhaseId.INVOKE_APPLICATION) {
                // Application was invoked; a new transaction should be begun.
                handleOutcome(facesContext, jbpmJsfContext);
                closeContext(jbpmJsfContext);
                removeJbpmJsfContext(facesContext);
            } else if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE) {
                // Response was rendered and possibly more actions invoked.
                closeContext(jbpmJsfContext);
                removeJbpmJsfContext(facesContext);
            }
        }
    }

    private void commitTxnMessages(final FacesContext facesContext, final JbpmJsfContextImpl jbpmJsfContext) {
        if (! jbpmJsfContext.isError()) {
            for (FacesMessage msg : jbpmJsfContext.getMessages()) {
                facesContext.addMessage(null, msg);
            }
        }
    }

    private void handleOutcome(final FacesContext facesContext, final JbpmJsfContextImpl jbpmJsfContext) {
        final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        if (jbpmJsfContext.isError()) {
            navigationHandler.handleNavigation(facesContext, jbpmJsfContext.getOutcomeActionName(), "error");
        } else {
            final String outcome = jbpmJsfContext.getOutcome();
            if (outcome != null) {
                navigationHandler.handleNavigation(facesContext, jbpmJsfContext.getOutcomeActionName(), outcome);
            }
        }
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    private JbpmJsfContextImpl getJbpmJsfContext(FacesContext facesContext) {
        final Map<String,Object> requestMap = facesContext.getExternalContext().getRequestMap();
        return (JbpmJsfContextImpl) requestMap.get(AbstractHandler.JBPM_JSF_CONTEXT_KEY);
    }

    private void removeJbpmJsfContext(FacesContext facesContext) {
        final Map<String,Object> requestMap = facesContext.getExternalContext().getRequestMap();
        requestMap.remove(AbstractHandler.JBPM_JSF_CONTEXT_KEY);
    }

    private void closeContext(final JbpmJsfContextImpl jbpmJsfContext) {
        if (jbpmJsfContext.hasJbpmContext()) {
            log.fine("Closing jBPM context");
            if (jbpmJsfContext.isError()) {
                log.fine("Context has an error status; setting transaction to roll back");
                jbpmJsfContext.getJbpmContext().setRollbackOnly();
            }
            final FacesContext context = FacesContext.getCurrentInstance();
            try {
                jbpmJsfContext.getJbpmContext().close();
            } catch (RuntimeException ex) {
                try {
                    jbpmJsfContext.getJbpmContext().setRollbackOnly();
                } catch (Exception ex2) {
                    log.log(Level.WARNING, "Failed to set rollback on jBPM context after close failed", ex2);
                }
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Closing the database context " +
                    "failed", "An exception of type " + ex.getClass().getName() + " was thrown, with the message: " +
                    ex.getMessage()));
                return;
            }
            commitTxnMessages(context, jbpmJsfContext);
        }
    }

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.phase.JbpmPhaseListener");
}
