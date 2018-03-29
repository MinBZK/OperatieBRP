/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;

/**
 * BrpNu.
 */
public final class BrpNu {


    private static final ThreadLocal<BrpNu> NU = ThreadLocal.withInitial(() -> null);

    private final ZonedDateTime datum;
    private final Integer nuAlsIntegerDatumNederland;

    private BrpNu(final ZonedDateTime datum) {
        this.datum = datum;
        this.nuAlsIntegerDatumNederland = DatumFormatterUtil.vanLocalDateNaarInteger(DatumUtil.vanZonedDateTimeNaarLocalDateNederland(datum));
    }

    /**
     * @return het threadlocal nu moment
     */
    public static BrpNu get() {
        final BrpNu brpNu = NU.get();
        if (brpNu == null) {
            throw new NullPointerException("brp nu niet gezet");
        }
        return brpNu;
    }

    /**
     * @param datum datum
     */
    public static void set(final ZonedDateTime datum) {
        NU.set(new BrpNu(datum));
    }

    /**
     * Zet {@link BrpNu} op {@link DatumUtil#nuAlsZonedDateTime()}.
     */
    public static void set() {
        NU.set(new BrpNu(DatumUtil.nuAlsZonedDateTime()));
    }


    public ZonedDateTime getDatum() {
        return datum;
    }

    /**
     * @return datum in nederland
     */
    public Integer alsIntegerDatumNederland() {
        return nuAlsIntegerDatumNederland;
    }
}
