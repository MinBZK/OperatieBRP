/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

/**
 * DTO voor een gevonden persoon.
 */
public final class GevondenPersoon {

    private final Long persoonId;
    private final String administratienummer;
    private final String bijhoudingsgemeente;

    /**
     * Constructor.
     * @param persoonId technische id persoon
     * @param administratienummer actueel administratienummer
     * @param bijhoudingsgemeente actuele bijhoudingsgemeente
     */
    public GevondenPersoon(final Long persoonId, final String administratienummer, final String bijhoudingsgemeente) {
        super();
        this.persoonId = persoonId;
        this.administratienummer = administratienummer;
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    /**
     * Geef de waarde van persoon id.
     * @return technisch id persoon
     */
    public Long getPersoonId() {
        return persoonId;
    }

    /**
     * Geef de waarde van administratienummer.
     * @return actueel administratienummer
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geef de waarde van bijhoudingsgemeente.
     * @return actuele bijhoudingsgemeente
     */
    public String getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

}
