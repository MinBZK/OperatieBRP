/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * De Class BerichtStatistiek.
 */
public class BerichtStatistiek {

    /** De aantal bijhoudingen. */
    private int aantalBijhoudingen;

    /** De aantal prevalidaties. */
    private int aantalPrevalidaties;

    /**
     * Instantieert een nieuwe bericht statistiek.
     *
     * @param aantalBijhoudingen de aantal bijhoudingen
     * @param aantalPrevalidaties de aantal prevalidaties
     */
    public BerichtStatistiek(final int aantalBijhoudingen, final int aantalPrevalidaties) {
        this.aantalBijhoudingen = aantalBijhoudingen;
        this.aantalPrevalidaties = aantalPrevalidaties;
    }

    /**
     * Stelt het aantal in voor bijhoudingen of prevalidaties.
     *
     * @param isPrevalidatie de is prevalidatie
     * @param aantal de aantal
     */
    public void set(final Boolean isPrevalidatie, final Number aantal) {
        if (Boolean.TRUE.equals(isPrevalidatie)) {
            aantalPrevalidaties = aantal.intValue();
        } else {
            aantalBijhoudingen = aantal.intValue();
        }
    }

    /**
     * Haalt een aantal bijhoudingen op.
     *
     * @return aantal bijhoudingen
     */
    public int getAantalBijhoudingen() {
        return aantalBijhoudingen;
    }

    /**
     * Instellen van aantal bijhoudingen.
     *
     * @param aantalBijhoudingen de nieuwe aantal bijhoudingen
     */
    public void setAantalBijhoudingen(final int aantalBijhoudingen) {
        this.aantalBijhoudingen = aantalBijhoudingen;
    }

    /**
     * Haalt een aantal prevalidaties op.
     *
     * @return aantal prevalidaties
     */
    public int getAantalPrevalidaties() {
        return aantalPrevalidaties;
    }

    /**
     * Instellen van aantal prevalidaties.
     *
     * @param aantalPrevalidaties de nieuwe aantal prevalidaties
     */
    public void setAantalPrevalidaties(final int aantalPrevalidaties) {
        this.aantalPrevalidaties = aantalPrevalidaties;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
