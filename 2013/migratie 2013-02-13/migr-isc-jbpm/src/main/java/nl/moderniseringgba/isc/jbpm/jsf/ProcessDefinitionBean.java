/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Backing bean om de proces definition te exposeren.
 */
public final class ProcessDefinitionBean {

    /**
     * Geef de huidige (task) process definitie.
     * 
     * @return (task) process definitie
     */
    public ProcessDefinition getProcessDefinition() {
        final TaskInstance task = JbpmJsfUtil.getTaskInstance();
        return task.getProcessInstance().getProcessDefinition();
    }

    /**
     * Geef de root process definitie.
     * 
     * @return root process definitie.
     */
    public ProcessDefinition getRootProcessDefinition() {
        final TaskInstance task = JbpmJsfUtil.getTaskInstance();

        ProcessInstance processInstance = task.getProcessInstance();
        while (processInstance.getSuperProcessToken() != null) {
            processInstance = processInstance.getSuperProcessToken().getProcessInstance();
        }

        return processInstance.getProcessDefinition();
    }

}
