/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

/**
 * Typering van Partij.
 */
public enum SoortPartij {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null),
    /**
     * Gemeente.
     */
    GEMEENTE("Gemeente"),
    /**
     * Minister.
     */
    MINISTER("Minister"),
    /**
     * Aangewezen bestuursorgaan.
     */
    AANGEWEZEN_BESTUURSORGAAN("Aangewezen Bestuursorgaan "),
    /**
     * Privaatrechtelijk rechtspersoon met wettelijke taak.
     */
    PRIVAATRECHTERLIJK_RECHTSPERSOON_MET_WETTELIJKE_TAAK("Privaatrechtelijk rechtspersoon met wettelijke taak"),
    /**
     * Samenwerkingsverband.
     */
    SAMENWERKINGSVERBAND("Samenwerkingsverband");

    private String omschrijving;

    /**
     * Constructor voor de initialisatie van deze enumeratie.
     *
     * @param omschrijving omschrijving.
     */
    private SoortPartij(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
