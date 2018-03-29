/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * SelectiePersoon.
 */
@JsonAutoDetect
public final class SelectiePersoonBericht {

    private String persoonHistorieVolledigGegevens;
    private String afnemerindicatieGegevens;

    /**
     * Gets persoon historie volledig gegevens.
     * @return the persoon historie volledig gegevens
     */
    public String getPersoonHistorieVolledigGegevens() {
        return persoonHistorieVolledigGegevens;
    }

    /**
     * Sets persoon historie volledig gegevens.
     * @param persoonHistorieVolledigGegevens the persoon historie volledig gegevens
     */
    public void setPersoonHistorieVolledigGegevens(String persoonHistorieVolledigGegevens) {
        this.persoonHistorieVolledigGegevens = persoonHistorieVolledigGegevens;
    }

    /**
     * Gets afnemerindicatie gegevens.
     * @return the afnemerindicatie gegevens
     */
    public String getAfnemerindicatieGegevens() {
        return afnemerindicatieGegevens;
    }

    /**
     * Sets afnemerindicatie gegevens.
     * @param afnemerindicatieGegevens the afnemerindicatie gegevens
     */
    public void setAfnemerindicatieGegevens(String afnemerindicatieGegevens) {
        this.afnemerindicatieGegevens = afnemerindicatieGegevens;
    }
}
