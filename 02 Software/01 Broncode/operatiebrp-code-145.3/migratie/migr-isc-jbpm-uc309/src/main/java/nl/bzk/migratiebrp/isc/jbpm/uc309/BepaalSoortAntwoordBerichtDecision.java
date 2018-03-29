/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.Map;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;

import org.springframework.stereotype.Component;

/**
 * Bepaal welk antwoord gegeven dient te worden.
 */
@Component("uc309BepaalSoortAntwoordBerichtDecision")
public class BepaalSoortAntwoordBerichtDecision implements SpringDecision {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public BepaalSoortAntwoordBerichtDecision(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public final String execute(final Map<String, Object> parameters) {
        final Long verwerkToevalligeGebeurtenisAntwoordBerichtId = (Long) parameters.get("verwerkToevalligeGebeurtenisAntwoordBericht");
        final VerwerkToevalligeGebeurtenisAntwoordBericht verwerkToevalligeGebeurtenisAntwoordBericht =
                (VerwerkToevalligeGebeurtenisAntwoordBericht) berichtenDao.leesBericht(verwerkToevalligeGebeurtenisAntwoordBerichtId);
        final String decision;
        if (StatusType.OK.equals(verwerkToevalligeGebeurtenisAntwoordBericht.getStatus())) {
            decision = "4a. maak null bericht";
        } else if (StatusType.AFGEKEURD.equals(verwerkToevalligeGebeurtenisAntwoordBericht.getStatus())) {
            decision = "4c. afgekeurd";
        } else {
            decision = "4b. maak tf21 bericht";
        }
        return decision;
    }
}
