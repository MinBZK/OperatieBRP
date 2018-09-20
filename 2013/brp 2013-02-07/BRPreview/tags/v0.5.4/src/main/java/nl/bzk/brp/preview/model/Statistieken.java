/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.model;

import java.io.Serializable;


/**
 * Deze klasse bevat de statistieken die zijn bijgehouden voor de berichten.
 *
 */
public class Statistieken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int               aantalPrevalidaties;
    private int               aantalGeboorteBerichten;
    private int               aantalVerhuisBerichten;

    public int getAantalVerhuisBerichten() {
        return aantalVerhuisBerichten;
    }

    public void setAantalVerhuisBerichten(final int aantalVerhuisBerichten) {
        this.aantalVerhuisBerichten = aantalVerhuisBerichten;
    }

    public int getAantalGeboorteBerichten() {
        return aantalGeboorteBerichten;
    }

    public void setAantalGeboorteBerichten(final int aantalGeboorteBerichten) {
        this.aantalGeboorteBerichten = aantalGeboorteBerichten;
    }

    public int getAantalPrevalidaties() {
        return aantalPrevalidaties;
    }

    public void setAantalPrevalidaties(final int aantalPrevalidaties) {
        this.aantalPrevalidaties = aantalPrevalidaties;
    }

}
