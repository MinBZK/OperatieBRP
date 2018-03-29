/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.helper;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * TestDatumUtil.
 */
public class TestDatumUtil {

    public static ZonedDateTime morgen() {
        return DatumUtil.nuAlsZonedDateTime().plus(1, ChronoUnit.DAYS);
    }

    public static ZonedDateTime gisteren() {
        return DatumUtil.nuAlsZonedDateTime().minus(1, ChronoUnit.DAYS);
    }


    public static ZonedDateTime van(final int jaar, final int maand, final int dag) {
        return LocalDate.of(jaar, maand, dag).atStartOfDay(DatumUtil.BRP_ZONE_ID);
    }
}
