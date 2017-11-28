/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import java.time.ZonedDateTime;

/**
 * Het resultaatobject voor het maken van het antwoordbericht.
 */
public final class MaakAntwoordBerichtResultaat {

    private final ZonedDateTime datumVerzending;
    private final String xml;
    private final String referentienummerAttribuutAntwoord;

    /**
     * Constructor.
     * @param xml het response bericht in XML formaat
     * @param datumVerzending de datum verzending dat is opgenomen in het bericht
     * @param referentienummerAttribuutAntwoord het referentienummer dat is opgenomen in het bericht
     */
    MaakAntwoordBerichtResultaat(final String xml, final ZonedDateTime datumVerzending,
                                 final String referentienummerAttribuutAntwoord) {
        this.xml = xml;
        this.datumVerzending = datumVerzending;
        this.referentienummerAttribuutAntwoord = referentienummerAttribuutAntwoord;
    }

    /**
     * @return de datum verzending dat is opgenomen in het bericht
     */
    public ZonedDateTime getDatumVerzending() {
        return datumVerzending;
    }

    /**
     * @return het response bericht in XML formaat
     */
    public String getXml() {
        return xml;
    }

    /**
     * @return het referentienummer dat is opgenomen in het bericht
     */
    public String getReferentienummerAttribuutAntwoord() {
        return referentienummerAttribuutAntwoord;
    }
}
