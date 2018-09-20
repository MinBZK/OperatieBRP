/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.jbpm.spring.SpringAction;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Registreer de fout.
 */
@Component("foutafhandelingRegistreerFoutAction")
public final class RegistreerFoutAction implements SpringAction {

    private FoutenDao foutenDao = new JbpmFoutenDao();

    public void setFoutenDao(final FoutenDao foutenDao) {
        this.foutenDao = foutenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        // Fout
        final String foutcode = (String) parameters.get(FoutafhandelingConstants.FOUT);
        final String foutmelding = (String) parameters.get(FoutafhandelingConstants.FOUTMELDING);
        final String bronGemeente = (String) parameters.get(FoutafhandelingConstants.BRON_GEMEENTE);
        final String doelGemeente = (String) parameters.get(FoutafhandelingConstants.DOEL_GEMEENTE);

        // Process
        final ExecutionContext context = ExecutionContext.currentExecutionContext();
        ProcessInstance processInstance = context.getProcessInstance();
        while (processInstance.getSuperProcessToken() != null) {
            processInstance = processInstance.getSuperProcessToken().getProcessInstance();
        }

        final String process = processInstance.getProcessDefinition().getName();
        final long processId = processInstance.getId();

        // Opslaan
        final long id =
                foutenDao.registreerFout(foutcode, foutmelding, process, processId, bronGemeente, doelGemeente);

        // Results
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(FoutafhandelingConstants.REGISTRATIE_ID, id);
        return result;
    }
}
