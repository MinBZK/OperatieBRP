/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.statistieken;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * De Class StatistiekResponse.
 */
public class StatistiekResponse {

    /** De geboorte statistiek. */
    private BerichtStatistiek geboorteStatistiek;

    /** De huwelijk statistiek. */
    private BerichtStatistiek huwelijkStatistiek;

    /** De verhuizing statistiek. */
    private BerichtStatistiek verhuizingStatistiek;

    /** De adrescorrectie statistiek. */
    private BerichtStatistiek adrescorrectieStatistiek;

    /**
     * Haalt een geboorte statistiek op.
     *
     * @return geboorte statistiek
     */
    public BerichtStatistiek getGeboorteStatistiek() {
        return geboorteStatistiek;
    }

    /**
     * Instellen van geboorte statistiek.
     *
     * @param geboorteStatistiek de nieuwe geboorte statistiek
     */
    public void setGeboorteStatistiek(final BerichtStatistiek geboorteStatistiek) {
        this.geboorteStatistiek = geboorteStatistiek;
    }

    /**
     * Haalt een huwelijk statistiek op.
     *
     * @return huwelijk statistiek
     */
    public BerichtStatistiek getHuwelijkStatistiek() {
        return huwelijkStatistiek;
    }

    /**
     * Instellen van huwelijk statistiek.
     *
     * @param huwelijkStatistiek de nieuwe huwelijk statistiek
     */
    public void setHuwelijkStatistiek(final BerichtStatistiek huwelijkStatistiek) {
        this.huwelijkStatistiek = huwelijkStatistiek;
    }

    /**
     * Haalt een verhuizing statistiek op.
     *
     * @return verhuizing statistiek
     */
    public BerichtStatistiek getVerhuizingStatistiek() {
        return verhuizingStatistiek;
    }

    /**
     * Instellen van verhuizing statistiek.
     *
     * @param verhuizingStatistiek de nieuwe verhuizing statistiek
     */
    public void setVerhuizingStatistiek(final BerichtStatistiek verhuizingStatistiek) {
        this.verhuizingStatistiek = verhuizingStatistiek;
    }

    /**
     * Haalt een adrescorrectie statistiek op.
     *
     * @return adrescorrectie statistiek
     */
    public BerichtStatistiek getAdrescorrectieStatistiek() {
        return adrescorrectieStatistiek;
    }

    /**
     * Instellen van adrescorrectie statistiek.
     *
     * @param adresCorrectieStatistiek de nieuwe adrescorrectie statistiek
     */
    public void setAdrescorrectieStatistiek(final BerichtStatistiek adresCorrectieStatistiek) {
        adrescorrectieStatistiek = adresCorrectieStatistiek;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
