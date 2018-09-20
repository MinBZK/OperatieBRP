/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.statistieken;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StatistiekResponse {

    private BerichtStatistiek geboorteStatistiek;

    private BerichtStatistiek huwelijkStatistiek;

    private BerichtStatistiek verhuizingStatistiek;

    private BerichtStatistiek adrescorrectieStatistiek;

    public BerichtStatistiek getGeboorteStatistiek() {
        return geboorteStatistiek;
    }

    public void setGeboorteStatistiek(final BerichtStatistiek geboorteStatistiek) {
        this.geboorteStatistiek = geboorteStatistiek;
    }

    public BerichtStatistiek getHuwelijkStatistiek() {
        return huwelijkStatistiek;
    }

    public void setHuwelijkStatistiek(final BerichtStatistiek huwelijkStatistiek) {
        this.huwelijkStatistiek = huwelijkStatistiek;
    }

    public BerichtStatistiek getVerhuizingStatistiek() {
        return verhuizingStatistiek;
    }

    public void setVerhuizingStatistiek(final BerichtStatistiek verhuizingStatistiek) {
        this.verhuizingStatistiek = verhuizingStatistiek;
    }

    public BerichtStatistiek getAdrescorrectieStatistiek() {
        return adrescorrectieStatistiek;
    }

    public void setAdrescorrectieStatistiek(final BerichtStatistiek adresCorrectieStatistiek) {
        adrescorrectieStatistiek = adresCorrectieStatistiek;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
