/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Maak een blokkering bericht (obv verhuisbericht).
 */
@Component("uc302MaakBlokkeringAction")
public final class MaakBlokkeringAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {

        LOG.info("execute(parameters={})", parameters);
        final VerhuizingVerzoekBericht verhuisBericht = (VerhuizingVerzoekBericht) parameters.get("input");

        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht();
        blokkeringBericht.setANummer(verhuisBericht.getANummer());
        blokkeringBericht.setGemeenteNaar(verhuisBericht.getBrpGemeente().getFormattedStringCode());
        blokkeringBericht.setGemeenteRegistratie(verhuisBericht.getBrpGemeente().getFormattedStringCode());

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = executionContext.getProcessInstance();
        final Long processInstanceId = processInstance.getId();

        blokkeringBericht.setProcessId(String.valueOf(processInstanceId));
        blokkeringBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("blokkeringBericht", blokkeringBericht);
        return result;
    }
}
