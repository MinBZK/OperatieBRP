/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Maak blokkering.
 */
@Component("uc301MaakBlokkeringAction")
public final class MaakBlokkeringAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    protected MaakBlokkeringAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) berichtenDao.leesBericht((Long) parameters.get("input"));
        final ZoekPersoonAntwoordBericht zoekPersoonAntwoordBericht;
        zoekPersoonAntwoordBericht =
                (ZoekPersoonAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("zoekPersoonBinnenGemeenteAntwoordBericht"));

        final BlokkeringVerzoekBericht blokkeringBericht = new BlokkeringVerzoekBericht();
        final String aNummer = zoekPersoonAntwoordBericht.getAnummer();
        blokkeringBericht.setANummer(aNummer);

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = executionContext.getProcessInstance();
        final Long processInstanceId = processInstance.getId();

        blokkeringBericht.setProcessId(String.valueOf(processInstanceId));
        blokkeringBericht.setPersoonsaanduiding(PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA);

        blokkeringBericht.setGemeenteNaar(ii01Bericht.getBronPartijCode());
        blokkeringBericht.setGemeenteRegistratie(ii01Bericht.getDoelPartijCode());

        final Map<String, Object> result = new HashMap<>();
        result.put("blokkeringBericht", berichtenDao.bewaarBericht(blokkeringBericht));
        return result;
    }

}
