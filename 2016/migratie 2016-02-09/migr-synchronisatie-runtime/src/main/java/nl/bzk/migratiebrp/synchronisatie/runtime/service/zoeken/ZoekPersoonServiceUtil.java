/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ZoekPersoonResultaatType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

/**
 * Helper methodes voor ZoekPersoonOpXxxxxxGegevensService's.
 */
public final class ZoekPersoonServiceUtil {
    private ZoekPersoonServiceUtil() {
        // Niet instantieerbaar
    }

    /**
     * Converteer naar Long.
     * 
     * @param value
     *            waarde
     * @return Long, null als waarde null of leeg is.
     */
    public static Long toLong(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Long.valueOf(value);
        }
    }

    /**
     * Converteer naar Integer.
     * 
     * @param value
     *            waarde
     * @return Integer, null als waarde null of leeg is.
     */
    public static Integer toInteger(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Integer.valueOf(value);
        }
    }

    /**
     * Maak een zoek persoon antwoord bericht op basis van de gevonden personen.
     * 
     * @param verzoek
     *            het oorspronkelijke verzoek (voor correlatie)
     * @param gevondenPersonen
     *            de gevonden personen
     * @return het antwoord bericht
     */
    public static ZoekPersoonAntwoordBericht maakZoekPersoonAntwoord(final SyncBericht verzoek, final List<GevondenPersoon> gevondenPersonen) {
        final ZoekPersoonAntwoordBericht result = new ZoekPersoonAntwoordBericht();
        result.setMessageId(MessageId.generateSyncMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        result.setStatus(StatusType.OK);

        if (gevondenPersonen == null || gevondenPersonen.isEmpty()) {
            result.setResultaat(ZoekPersoonResultaatType.GEEN);
        } else if (gevondenPersonen.size() == 1) {
            result.setResultaat(ZoekPersoonResultaatType.GEVONDEN);

            final GevondenPersoon persoon = gevondenPersonen.get(0);
            result.setPersoonId(persoon.getPersoonId());
            result.setAnummer(String.valueOf(persoon.getAdministratienummer()));
            result.setGemeente(persoon.getBijhoudingsgemeente());
        } else {
            result.setResultaat(ZoekPersoonResultaatType.MEERDERE);
        }

        return result;
    }

}
