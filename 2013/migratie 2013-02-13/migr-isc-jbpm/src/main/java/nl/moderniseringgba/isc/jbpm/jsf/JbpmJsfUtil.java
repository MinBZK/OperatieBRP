/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.jbpm.JbpmContext;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Utilities voor jbpm in jsf.
 */
public final class JbpmJsfUtil {

    private JbpmJsfUtil() {
    }

    /**
     * Geef de huidige jbpm context.
     * 
     * @return jbpm context
     */
    public static JbpmContext getJbpmContext() {
        final Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();

        // Class org.jbpm.jsf.JbpmJsfContext
        final Object jbpmJsfContext = requestMap.get(AbstractHandler.JBPM_JSF_CONTEXT_KEY);
        try {
            return (JbpmContext) jbpmJsfContext.getClass().getMethod("getJbpmContext", (Class[]) null)
                    .invoke(jbpmJsfContext, (Object[]) null);
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (final SecurityException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Geef de huidige task instantie.
     * 
     * @return task instantie
     */
    public static TaskInstance getTaskInstance() {
        final Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        return (TaskInstance) requestMap.get("task");
    }

}
