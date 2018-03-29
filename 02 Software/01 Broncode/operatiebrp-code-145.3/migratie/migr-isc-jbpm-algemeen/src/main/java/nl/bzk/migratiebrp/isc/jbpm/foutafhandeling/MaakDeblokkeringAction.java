/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak het deblokkerings bericht.
 */
@Component("foutafhandelingMaakDeblokkeringAction")
public final class MaakDeblokkeringAction implements SpringAction {

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakDeblokkeringAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final BlokkeringVerzoekBericht bericht =
                (BlokkeringVerzoekBericht) berichtenDao.leesBericht((Long) parameters.get(FoutafhandelingConstants.BERICHT_BLOKKERING));
        if (bericht == null) {
            throw new IllegalStateException("Geen blokkering bericht om te deblokkeren.");
        }

        final DeblokkeringVerzoekBericht deblokkeringBericht = new DeblokkeringVerzoekBericht();
        deblokkeringBericht.setCorrelationId(bericht.getMessageId());
        deblokkeringBericht.setANummer(bericht.getANummer());
        deblokkeringBericht.setProcessId(bericht.getProcessId());

        final Map<String, Object> result = new HashMap<>();
        result.put(FoutafhandelingConstants.BERICHT_VERZOEK_DEBLOKKERING, berichtenDao.bewaarBericht(deblokkeringBericht));

        return result;
    }
}
