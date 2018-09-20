/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.phase;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.core.config.Configuration;
import org.jbpm.jsf.core.config.ConfigurationLocator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public final class ProcessFilePhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.phase.ProcessFilePhaseListener");

    private Configuration configuration = null;

    public ProcessFilePhaseListener() {
    }

    public void beforePhase(PhaseEvent event) {
        log.fine("Entering phase listener");
        final FacesContext facesContext = event.getFacesContext();
        final ExternalContext externalContext = facesContext.getExternalContext();
        synchronized(this) {
            if (configuration == null) {
                try {
                    configuration = ConfigurationLocator.getInstance();
                } catch (Exception ex) {
                    final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
                    try {
                        response.sendError(500, "Error loading jbpm configuration: " + ex.getMessage());
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Error sending 500 response for bad jbpm configuration load", e);
                    } finally {
                        facesContext.responseComplete();
                    }
                    return;
                }
            }
        }
        final String servletPath = externalContext.getRequestServletPath();
        final String pathInfo = externalContext.getRequestPathInfo();
        final String path = servletPath == null ? null : pathInfo == null ? servletPath : servletPath + pathInfo;
        if (path == null) {
            log.fine("Path is null, skipping");
            return;
        }
        final List<Configuration.FileMatcher> fileMatchers = configuration.getMatchers();
        if (log.isLoggable(Level.FINE)) {
            log.fine("Checking path against " + fileMatchers.size() + " matchers");
        }
        for (Configuration.FileMatcher fileMatcher : fileMatchers) {
            final Pattern pattern = fileMatcher.getPattern();
            final Matcher matcher = pattern.matcher(path);
            if (!matcher.matches()) {
                if (log.isLoggable(Level.FINE)) {
                    log.fine("Request pattern '" + pattern + "' did NOT match path info '" + path + "'");
                }
            } else {
                if (log.isLoggable(Level.FINE)) {
                    log.fine("Request pattern '" + pattern + "' MATCHED path info '" + path + "'");
                }
                if (matcher.groupCount() < 1) {
                    log.warning("Configuration pattern '" + pattern + "' does not contain a capturing group for the process ID");
                    continue;
                }
                final String idPart = matcher.group(1);
                final long id = Long.parseLong(idPart);
                final String contentType = fileMatcher.getContentType();
                final String file = fileMatcher.getFile();
                final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
                final JbpmContext jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
                try {
                    final ProcessDefinition processDefinition = jbpmContext.getGraphSession().getProcessDefinition(id);
                    if (processDefinition == null) {
                        try {
                            response.sendError(404, "Process definition " + id + " does not exist");
                            facesContext.responseComplete();
                            break;
                        } catch (IOException e) {
                            log.log(Level.SEVERE, "Failed to send 404 Not Found to client", e);
                        }
                    }
                    if (!processDefinition.getFileDefinition().hasFile(file)) {
                        try {
                            response.sendError(404, "Process definition " + id + " does not contain file '" + file + "'");
                            facesContext.responseComplete();
                            break;
                        } catch (IOException e) {
                            log.log(Level.SEVERE, "Failed to send 404 Not Found to client", e);
                        }
                    }
                    final byte[] bytes;
                    bytes = processDefinition.getFileDefinition().getBytes(file);
                    response.setContentLength(bytes.length);
                    response.setContentType(contentType);
                    try {
                        final OutputStream outputStream = response.getOutputStream();
                        try {
                            outputStream.write(bytes);
                            outputStream.flush();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                log.log(Level.WARNING, "Failed to close output stream", e);
                            }
                        }
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Failed to send process file", e);
                    }
                    facesContext.responseComplete();
                    if (log.isLoggable(Level.FINE)) {
                        log.fine("Sent process file '" + path + "'");
                    }
                    break;
                } finally {
                    jbpmContext.close();
                }
            }
        }
    }

    public void afterPhase(PhaseEvent event) {
        // nothing
    }

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
