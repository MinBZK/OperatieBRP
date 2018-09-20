/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BerichtStatistiek {

    private int aantalBijhoudingen;

    private int aantalPrevalidaties;

    public BerichtStatistiek(final int aantalBijhoudingen, final int aantalPrevalidaties) {
        this.aantalBijhoudingen = aantalBijhoudingen;
        this.aantalPrevalidaties = aantalPrevalidaties;
    }

    public int getAantalBijhoudingen() {
        return aantalBijhoudingen;
    }

    public void setAantalBijhoudingen(final int aantalBijhoudingen) {
        this.aantalBijhoudingen = aantalBijhoudingen;
    }

    public int getAantalPrevalidaties() {
        return aantalPrevalidaties;
    }

    public void setAantalPrevalidaties(final int aantalPrevalidaties) {
        this.aantalPrevalidaties = aantalPrevalidaties;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
