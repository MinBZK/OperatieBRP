/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.alleenlezen.support;

import java.util.Calendar;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;


public final class DatumTijdBuilder {

    private static final int[] CALENDAR_FIELDS =
    { Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND };

    private DatumTijdBuilder() {
    }

    /**
     * Bouw een {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut} instantie op basis van de
     * geleverde velden.
     * De volgorde is: jaar, maand (1 based, dus januari = 1), dag, uur, minuut, seconde
     *
     * @param velden de velden die een datumTijd vormen
     * @return een datumTijd instantie
     */
    public static DatumTijdAttribuut datumTijd(final int... velden) {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();

        for (int i = 0; i < velden.length && i <= 5; i++) {
            int waarde = velden[i];
            if (i == 1) {
                waarde -= 1;
            }
            calendar.set(CALENDAR_FIELDS[i], waarde);
        }

        return new DatumTijdAttribuut(calendar.getTime());
    }

}
