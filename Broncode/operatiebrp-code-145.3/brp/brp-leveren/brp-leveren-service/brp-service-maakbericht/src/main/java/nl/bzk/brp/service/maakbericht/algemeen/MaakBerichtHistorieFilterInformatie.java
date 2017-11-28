/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.algemeen;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;

/**
 * Bevat informatie m.b.t gebruik van historie filter : {@link HistorieVorm} en formeel/materieel peilmoment.
 */
public class MaakBerichtHistorieFilterInformatie {
    private HistorieVorm historieVorm;
    private Integer peilmomentMaterieel;
    private ZonedDateTime peilmomentFormeel;

    /**
     * Constructor.
     * @param historieVorm {@link HistorieVorm}
     * @param peilmomentMaterieel materieel peilmoment
     * @param peilmomentFormeel formeel peilmoment
     */
    public MaakBerichtHistorieFilterInformatie(HistorieVorm historieVorm, Integer peilmomentMaterieel, ZonedDateTime peilmomentFormeel) {
        this.historieVorm = historieVorm;
        this.peilmomentMaterieel = peilmomentMaterieel;
        this.peilmomentFormeel = peilmomentFormeel;
    }

    public HistorieVorm getHistorieVorm() {
        return historieVorm;
    }

    public Integer getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    public ZonedDateTime getPeilmomentFormeel() {
        return peilmomentFormeel;
    }

}
