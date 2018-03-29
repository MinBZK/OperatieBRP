/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Antwoordberichtresultaat.
 */
public final class AntwoordBerichtResultaat {

    private final String xml;
    private final ZonedDateTime datumVerzending;
    private final String referentienummerAttribuutAntwoord;
    private final List<Melding> meldingList;

    /**
     * Constructor.
     * @param xml het XML bericht
     * @param datumVerzending de datum van verzending
     * @param referentienummerAttribuutAntwoord het antwoord referentienummer
     * @param meldingList de lijst met meldingen
     */
    public AntwoordBerichtResultaat(final String xml, final ZonedDateTime datumVerzending, final String referentienummerAttribuutAntwoord,
                                    final List<Melding> meldingList) {
        this.xml = xml;
        this.datumVerzending = datumVerzending;
        this.referentienummerAttribuutAntwoord = referentienummerAttribuutAntwoord;
        this.meldingList = meldingList;
    }

    public String getXml() {
        return xml;
    }

    public ZonedDateTime getDatumVerzending() {
        return datumVerzending;
    }

    public String getReferentienummerAttribuutAntwoord() {
        return referentienummerAttribuutAntwoord;
    }

    public List<Melding> getMeldingList() {
        return meldingList;
    }
}
