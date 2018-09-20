/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import java.util.Calendar;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;

public final class DatumTijdBuilder {

    private DatumTijdBuilder() {
    }

    public static DatumTijd bouwDatumTijd(final int jaar, final int maand, final int dag, final int uur,
                                          final int minuut, final int seconde)
    {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(jaar, maand - 1, dag, uur, minuut, seconde);
        return new DatumTijd(calendar.getTime());
    }

}
