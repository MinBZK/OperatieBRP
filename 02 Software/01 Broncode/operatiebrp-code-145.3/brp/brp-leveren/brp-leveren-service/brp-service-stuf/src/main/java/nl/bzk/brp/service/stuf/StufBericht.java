/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

/**
 * StufBericht.
 */
public final class StufBericht {

    private String inhoud;
    private String soortSynchronisatie;

    /**
     * @param inhoud inhoud
     * @param soortSynchronisatie soortSynchronisatie
     */
    public StufBericht(String inhoud, String soortSynchronisatie) {
        this.inhoud = inhoud;
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * Gets inhoud.
     * @return the inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Gets soort synchronisatie.
     * @return the soort synchronisatie
     */
    public String getSoortSynchronisatie() {
        return soortSynchronisatie;
    }


}
