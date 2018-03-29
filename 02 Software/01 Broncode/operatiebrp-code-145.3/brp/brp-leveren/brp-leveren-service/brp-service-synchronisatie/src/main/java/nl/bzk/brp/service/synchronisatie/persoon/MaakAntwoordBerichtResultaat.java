/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Het synchroon bericht resultaat.
 */
final class MaakAntwoordBerichtResultaat {

    private final ZonedDateTime datumVerzending;
    private final String referentienummerAttribuutAntwoord;
    private final List<Melding> meldingList;
    private final Partij partijBrpvoorziening;
    private final String referentieNummer;
    private final SoortMelding hoogsteMeldingNiveau;

    /**
     * Constructor.
     * @param datumVerzending de datum van verzending
     * @param referentienummerAttribuutAntwoord het antwoord referentienummer
     * @param meldingList de lijst met meldingen
     * @param partijBrpvoorziening de BRP partij
     * @param referentieNummer het referentienummer uit het verzoek
     * @param hoogsteMeldingNiveau het hoogste meldingsniveau
     */
    MaakAntwoordBerichtResultaat(final ZonedDateTime datumVerzending,
                                 final String referentienummerAttribuutAntwoord, final List<Melding> meldingList, final Partij partijBrpvoorziening,
                                 final String referentieNummer, final SoortMelding hoogsteMeldingNiveau) {
        this.datumVerzending = datumVerzending;
        this.referentienummerAttribuutAntwoord = referentienummerAttribuutAntwoord;
        this.meldingList = meldingList;
        this.partijBrpvoorziening = partijBrpvoorziening;
        this.referentieNummer = referentieNummer;
        this.hoogsteMeldingNiveau = hoogsteMeldingNiveau;
    }

    /**
     * Gets datum verzending.
     * @return the datum verzending
     */
    ZonedDateTime getDatumVerzending() {
        return datumVerzending;
    }

    /**
     * Gets referentienummer attribuut antwoord.
     * @return the referentienummer attribuut antwoord
     */
    String getReferentienummerAttribuutAntwoord() {
        return referentienummerAttribuutAntwoord;
    }

    /**
     * Gets melding list.
     * @return the melding list
     */
    public List<Melding> getMeldingList() {
        return meldingList;
    }

    /**
     * Gets partij brpvoorziening.
     * @return the partij brpvoorziening
     */
    Partij getPartijBrpvoorziening() {
        return partijBrpvoorziening;
    }

    /**
     * Gets hoogste melding niveau.
     * @return the hoogste melding niveau
     */
    SoortMelding getHoogsteMeldingNiveau() {
        return hoogsteMeldingNiveau;
    }

    /**
     * Gets referentie nummer.
     * @return the referentie nummer
     */
    String getReferentieNummer() {
        return referentieNummer;
    }
}
