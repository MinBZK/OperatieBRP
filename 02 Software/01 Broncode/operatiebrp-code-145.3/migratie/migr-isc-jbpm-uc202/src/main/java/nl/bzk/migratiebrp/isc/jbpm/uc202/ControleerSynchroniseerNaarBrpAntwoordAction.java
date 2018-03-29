/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.EnumMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Controleer synchroniseerNaarBrp antwoord.
 */
@Component("uc202ControleerSynchroniseerNaarBrpAntwoordAction")
public final class ControleerSynchroniseerNaarBrpAntwoordAction implements SpringAction {

    private static final String ONDUIDELIJK = "3d. Onduidelijk";
    private static final String GENEGEERD = "3f. Genegeerd";
    private static final String AFGEKEURD = "3e. Afgekeurd";
    private static final String FOUT = "3c. Fout";
    private static final String NOG_TE_LEVEREN = "3g. Te leveren administratieve handelingen aanwezig";

    private final ControleerSynchroniseerNaarBrpAntwoord controleerSynchroniseerNaarBrpAntwoord;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public ControleerSynchroniseerNaarBrpAntwoordAction(final BerichtenDao berichtenDao) {
        final EnumMap<StatusType, String> transitionResult = new EnumMap<>(StatusType.class);
        transitionResult.put(StatusType.ONDUIDELIJK, ONDUIDELIJK);
        transitionResult.put(StatusType.GENEGEERD, GENEGEERD);
        transitionResult.put(StatusType.AFGEKEURD, AFGEKEURD);
        transitionResult.put(StatusType.FOUT, FOUT);
        transitionResult.put(StatusType.VORIGE_HANDELINGEN_NIET_GELEVERD, NOG_TE_LEVEREN);
        controleerSynchroniseerNaarBrpAntwoord = new ControleerSynchroniseerNaarBrpAntwoord(berichtenDao, transitionResult);
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        return controleerSynchroniseerNaarBrpAntwoord.execute(parameters);
    }
}
