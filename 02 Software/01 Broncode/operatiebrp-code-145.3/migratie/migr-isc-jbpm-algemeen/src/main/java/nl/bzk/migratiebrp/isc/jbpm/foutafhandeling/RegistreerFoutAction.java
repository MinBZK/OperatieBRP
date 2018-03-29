/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.migratiebrp.isc.jbpm.common.ProcessInstanceUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Registreer de fout.
 */
@Component("foutafhandelingRegistreerFoutAction")
public final class RegistreerFoutAction implements SpringAction {

    @Autowired
    private FoutenDao foutenDao;

    /**
     * Zet de waarde van fouten dao.
     * @param foutenDao fouten dao
     */
    public void setFoutenDao(final FoutenDao foutenDao) {
        this.foutenDao = foutenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        // Fout
        final String foutcode = (String) parameters.get(FoutafhandelingConstants.FOUT);
        final String foutmelding = (String) parameters.get(FoutafhandelingConstants.FOUTMELDING);
        final String bronPartijCode = (String) parameters.get(FoutafhandelingConstants.BRON_PARTIJ_CODE);
        final String doelPartijCode = (String) parameters.get(FoutafhandelingConstants.DOEL_PARTIJ_CODE);

        // Process
        final ExecutionContext context = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = ProcessInstanceUtil.getRootProcessInstance(context.getProcessInstance());

        final String process = processInstance.getProcessDefinition().getName();
        final long processId = processInstance.getId();

        // Opslaan
        final long id = foutenDao.registreerFout(foutcode, foutmelding, process, processId, bronPartijCode, doelPartijCode);

        // Results
        final Map<String, Object> result = new HashMap<>();
        result.put(FoutafhandelingConstants.REGISTRATIE_ID, id);
        return result;
    }
}
