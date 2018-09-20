/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

/**
 * Enumeratie voor alle meldingscodes.
 */
public enum MeldingCode {

    /** ALG001: Algemene fout met een meestal onbekende oorzaak. */
    ALG0001("Algemene fout"),
    /** VERH0001: Algemene fout opgetreden vanwege fouten bij de verhuizing. */
    VERH0001("Algemene fout opgetreden vanwege fouten bij de verhuizing"),
    /** AUTH0001: Partij is niet geauthenticeerd voor bijhoudingen. */
    AUTH0001("Partij niet geauthenticeerd"),
    /** REF0001: Een onbekende referentie of code is gebruikt. */
    REF0001("Onbekende referentie gebruikt"),
    /** BRAL0012: Business rule - ongeldige bsn. */
    BRAL0012("Bsn nummer is ongeldig"),
    /** BRAL2032: Business rule - soort adres verplicht voor NL adressen.*/
    BRAL2032("Soort adres verplicht voor Nederlandse adressen"),
    /** BRAL0102: Business rule - Datum (deels) onbekend. */
    BRAL0102("Datum ongeldig formaat"),
    /** MR0502: Geef een waarschuwing indien er al iemand op het adres ingeschreven staat.*/
    MR0502("Reeds persoon ingeschreven op het adres");



    private final String omschrijving;

    /**
     * Standaard constructor die direct de standaard omschrijving van de melding initialiseert.
     *
     * @param omschrijving de standaard omschrijving van de melding.
     */
    private MeldingCode(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de naam/code van de meldingcode.
     *
     * @return de naam/code van de meldingcode.
     */
    public String getNaam() {
        return name();
    }

    /**
     * Retourneert de standaard omschrijving van de melding.
     *
     * @return de standaard omschrijving van de melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
