/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Maak blokkering.
 */
@Component("uc301MaakBlokkeringAction")
public final class MaakBlokkeringAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) parameters.get("input");
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht =
                (ZoekPersoonAntwoordBericht) parameters.get("zoekPersoonBinnenGemeenteAntwoordBericht");

        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht();
        final String aNummer = zoekPersoonAntwoordBericht.getSingleAnummer();
        blokkeringBericht.setANummer(aNummer);

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = executionContext.getProcessInstance();
        final Long processInstanceId = processInstance.getId();

        blokkeringBericht.setProcessId(String.valueOf(processInstanceId));
        blokkeringBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);
        blokkeringBericht.setGemeenteNaar(ii01Bericht.getBronGemeente());
        blokkeringBericht.setGemeenteRegistratie(ii01Bericht.getDoelGemeente());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("blokkeringBericht", blokkeringBericht);
        return result;
    }

}
