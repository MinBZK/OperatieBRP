/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Maak een deblokkering bericht (obv blokkeringBericht).
 */
@Component("uc302MaakDeblokkeringAction")
public final class MaakDeblokkeringAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BlokkeringVerzoekBericht blokkeringBericht =
                (BlokkeringVerzoekBericht) parameters.get("blokkeringBericht");

        final DeblokkeringVerzoekBericht deblokkeringBericht = new DeblokkeringVerzoekBericht();
        deblokkeringBericht.setANummer(blokkeringBericht.getANummer());

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = executionContext.getProcessInstance();
        final Long processInstanceId = processInstance.getId();

        deblokkeringBericht.setProcessId(String.valueOf(processInstanceId));
        deblokkeringBericht.setGemeenteRegistratie(blokkeringBericht.getGemeenteRegistratie());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("deblokkeringBericht", deblokkeringBericht);
        return result;
    }
}
