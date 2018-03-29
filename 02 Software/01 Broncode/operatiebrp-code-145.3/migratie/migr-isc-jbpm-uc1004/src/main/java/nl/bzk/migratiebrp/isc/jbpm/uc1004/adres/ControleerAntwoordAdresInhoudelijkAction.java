/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Controleer antwoord adres inhoudelijk.
 */
@Component("uc1004ControleerAntwoordAdresInhoudelijkAction")
public class ControleerAntwoordAdresInhoudelijkAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String TRANSITIE_TECHNISCHE_FOUT = "3a. Technische fout";
    private static final String TRANSITIE_FUNCTIONELE_FOUT = "3b. functionele fout";

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichtenrepo
     */
    @Inject
    public ControleerAntwoordAdresInhoudelijkAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final AdHocZoekPersonenOpAdresAntwoordBericht antwoord = haalAntwoordOp(parameters);

        final Map<String,Object> result = controleerAntwoord(antwoord);
        LOG.debug("result: {}", result);
        return result;
    }

    private AdHocZoekPersonenOpAdresAntwoordBericht haalAntwoordOp(final Map<String, Object> parameters) {
        if (parameters.containsKey(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL)) {
            final Long berichtId = (Long) parameters.get(AdHocVragenConstanten.ZOEK_PERSOON_ADRES_ANTWOORD_BERICHT_SLEUTEL);
            return (AdHocZoekPersonenOpAdresAntwoordBericht) berichtenDao.leesBericht(berichtId);
        } else {
            throw new IllegalStateException("Geen antwoord bericht ter controle doorgegeven in proces");
        }
    }

    private Map<String, Object> controleerAntwoord(final AdHocZoekPersonenOpAdresAntwoordBericht antwoord) {
        final Map<String, Object> resultaat = new HashMap<>();
        if (antwoord.getFoutreden() != null) {
            if (AdHocZoekAntwoordFoutReden.F.value().equals(antwoord.getFoutreden().value())) {
                resultaat.put(SpringActionHandler.TRANSITION_RESULT, TRANSITIE_TECHNISCHE_FOUT);
                resultaat.put(FOUTMELDING_VARIABELE, "Technische fout in de backend opgetreden");
            } else {
                resultaat.put(AdHocVragenConstanten.XF01_FOUTREDEN, antwoord.getFoutreden().value());
                resultaat.put(SpringActionHandler.TRANSITION_RESULT, TRANSITIE_FUNCTIONELE_FOUT);
            }
        }
        return resultaat;
    }

}
