/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak het Sync VrijBericht.
 */
@Component("foutafhandelingMaakVbAction")
public final class MaakVbAction implements SpringAction {

    private static final String FOUTMELDING_START = "Het versturen van het onderstaande vrij bericht aan ";
    private static final String FOUTMELDING_EINDE = " is niet gelukt: ";
    private static final int MAXIMALE_BERICHT_LENGTE = 99_000;
    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakVbAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final VrijBerichtNotificatieBericht bericht =
                (VrijBerichtNotificatieBericht) berichtenDao.leesBericht((Long) parameters.get(FoutafhandelingConstants.BERICHT_LO3));

        final VrijBerichtVerzoekBericht vbBericht = new VrijBerichtVerzoekBericht();
        vbBericht.setOntvangendePartij(parameters.get(FoutafhandelingConstants.DOEL_PARTIJ_CODE).toString());
        vbBericht.setVerzendendePartij(parameters.get(FoutafhandelingConstants.BRON_PARTIJ_CODE).toString());

        StringBuilder vbBerichtInhoud = new StringBuilder(FOUTMELDING_START);
        vbBerichtInhoud.append(parameters.get(FoutafhandelingConstants.DOEL_PARTIJ_CODE).toString());
        vbBerichtInhoud.append(FOUTMELDING_EINDE);
        vbBerichtInhoud.append(bericht.getVrijBerichtInhoud());
        if (vbBerichtInhoud.length() > MAXIMALE_BERICHT_LENGTE) {
            vbBericht.setBericht(vbBerichtInhoud.substring(0, MAXIMALE_BERICHT_LENGTE));
        } else {
            vbBericht.setBericht(vbBerichtInhoud.toString());
        }
        vbBericht.setReferentienummer(bericht.getReferentieNummer());

        final Map<String, Object> result = new HashMap<>();
        result.put(FoutafhandelingConstants.BERICHT_VB, berichtenDao.bewaarBericht(vbBericht));
        return result;
    }
}
