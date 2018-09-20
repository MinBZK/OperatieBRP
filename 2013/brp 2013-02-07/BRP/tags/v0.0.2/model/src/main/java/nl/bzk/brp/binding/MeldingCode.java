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
    VERH0001("Algemene fout opgetreden vanwege fouten bij de verhuizing");

    private final String omschrijving;

    /**
     * Standaard constructor die direct de standaard omschrijving van de melding initialiseert.
     * @param omschrijving de standaard omschrijving van de melding.
     */
    private MeldingCode(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de naam/code van de meldingcode.
     * @return de naam/code van de meldingcode.
     */
    public String getNaam() {
        return this.name();
    }

    /**
     * Retourneert de standaard omschrijving van de melding.
     * @return de standaard omschrijving van de melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
