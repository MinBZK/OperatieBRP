/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak het pf01 bericht.
 */
@Component("foutafhandelingMaakPfAction")
public final class MaakPfAction implements SpringAction {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakPfAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final Lo3Bericht bericht = (Lo3Bericht) berichtenDao.leesBericht((Long) parameters.get(FoutafhandelingConstants.BERICHT_LO3));
        if (bericht == null) {
            throw new IllegalStateException("Geen LO3 bericht om met Pf03 te beantwoorden.");
        }
        final Boolean indicatieCyclusFout = (Boolean) parameters.get(FoutafhandelingConstants.INDICATIE_CYCLUS_FOUT);

        final Lo3Bericht pfBericht;
        if (bericht instanceof OnbekendBericht || Boolean.TRUE.equals(indicatieCyclusFout)) {
            pfBericht = new Pf01Bericht(bericht.getMessageId());
        } else if (bericht instanceof OngeldigeSyntaxBericht) {
            pfBericht = new Pf02Bericht(bericht.getMessageId());
        } else {
            pfBericht = new Pf03Bericht(bericht.getMessageId());
        }

        pfBericht.setBronPartijCode(bericht.getDoelPartijCode());
        pfBericht.setDoelPartijCode(bericht.getBronPartijCode());

        final Map<String, Object> result = new HashMap<>();
        result.put(FoutafhandelingConstants.BERICHT_PF, berichtenDao.bewaarBericht(pfBericht));
        return result;
    }
}
