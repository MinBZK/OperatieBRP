/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.identity.hibernate.IdentitySession;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.config.ConfigurationLocator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public final class JbpmJsfContextImpl implements JbpmJsfContext {

    private JbpmContext jbpmContext;
    private IdentitySession identitySession;
    private List<FacesMessage> messages = new ArrayList<FacesMessage>();
    private String outcome;
    private boolean error;
    private String actionName;
    private String outcomeActionName;
    private boolean enableNavigation;

    public boolean hasJbpmContext() {
        return jbpmContext != null;
    }

    public JbpmContext getJbpmContext() {
        final boolean debug = log.isLoggable(Level.FINE);
        if (jbpmContext==null) {
            jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
            final Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (userPrincipal != null && ConfigurationLocator.getInstance().useJsfActorId()) {
                jbpmContext.setActorId(userPrincipal.getName());
            }
            if (debug) log.fine(toString() + " using NEW " + jbpmContext.toString());
        } else {
            if (debug) log.fine(toString() + " using cached " + jbpmContext.toString());
        }
        return jbpmContext;
    }

    public IdentitySession getIdentitySession() {
        if (identitySession==null) {
          identitySession = new IdentitySession(getJbpmContext().getSession());
        }
        return identitySession;
    }

    public void addSuccessMessage(String message) {
        if (message != null) {
            messages.add(new FacesMessage(message, null));
            if (log.isLoggable(Level.FINE)) {
                log.fine("Added success message: " + message);
            }
        }
    }

    public void addSuccessMessage(String message, String detail) {
        if (message != null) {
            messages.add(new FacesMessage(message, detail));
            if (log.isLoggable(Level.FINE)) {
                log.fine("Added success message: " + message + " (detail: " + detail + ")");
            }
        }
    }

    public void addSuccessMessage(FacesMessage.Severity severity, String message, String detail) {
        if (severity == null) {
            throw new NullPointerException("addSuccessMessage cannot accept null as the value of the severity parameter");
        }
        if (message != null) {
            messages.add(new FacesMessage(severity, message, detail));
            if (log.isLoggable(Level.FINE)) {
                log.fine("Added success message: severity " + severity + ", message: " + message + " (detail: " + detail + ")");
            }
        }
    }

    public void selectOutcome(String outcomeName) {
        if (outcomeName != null && enableNavigation) {
            outcome = outcomeName;
            outcomeActionName = actionName;
            if (log.isLoggable(Level.FINE)) {
                log.fine("Selected outcome " + outcomeName);
            }
        }
    }

    public String getOutcome() {
        return outcome;
    }

    public boolean isError() {
        return error;
    }

    public void setError(String message) {
        error = true;
        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "Error message \"" + message + "\"");
        }
    }

    public void setError(String message, String detail) {
        error = true;
        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail));
        }
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "Error message \"" + message + "\" (" + detail + ")");
        }
    }

    public void setError(String message, Throwable cause) {
        error = true;
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append("An exception of type \"");
        detailBuilder.append(cause.getClass().getName());
        detailBuilder.append("\" was thrown.");
        final String exMsg = cause.getMessage();
        if (exMsg != null) {
            detailBuilder.append("  The message is: ");
            detailBuilder.append(exMsg);
        }
        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detailBuilder.toString()));
        }
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "Error message \"" + message + "\" (exception thrown: " + cause.getMessage() + ")", cause);
        }
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(final String actionName) {
        this.actionName = actionName;
    }

    public String getOutcomeActionName() {
        return outcomeActionName;
    }

    public void setEnableNavigation(final boolean enableNavigation) {
        this.enableNavigation = enableNavigation;
    }

    public void reset() {
        messages = new ArrayList<FacesMessage>();
        error = false;
        outcome = null;
        outcomeActionName = null;
        actionName = null;
    }

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.impl.JbpmJsfContextImpl");

    public List<FacesMessage> getMessages() {
        return messages;
    }
}
