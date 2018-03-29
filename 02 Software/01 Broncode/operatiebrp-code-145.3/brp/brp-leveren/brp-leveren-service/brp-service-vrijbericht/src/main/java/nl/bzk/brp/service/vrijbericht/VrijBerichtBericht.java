/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

/**
 * VrijBericht.
 */
public final class VrijBerichtBericht {

    private String soortNaam;
    private String inhoud;

    /**
     * Gets soort naam.
     * @return the soort naam
     */
    public String getSoortNaam() {
        return soortNaam;
    }

    /**
     * Sets soort naam.
     * @param soortNaam the soort naam
     */
    public void setSoortNaam(String soortNaam) {
        this.soortNaam = soortNaam;
    }

    /**
     * Gets inhoud.
     * @return the inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Sets inhoud.
     * @param inhoud the inhoud
     */
    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }
}
