/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.model.basis.FormeelHistorisch;
import org.junit.Assert;
import org.springframework.test.util.ReflectionTestUtils;

public class HistorieVerwerkerTestUtil {

    private HistorieVerwerkerTestUtil() {
        // empty constructor, util class.
    }

    public static <T extends FormeelHistorisch> void controleer(
            final AbstractHistorieVerwerker<?, T> historieVerwerker,
            final T nieuweHistorie,
            final T actueleRecord,
            final String propertyNaam,
            final Object andereWaarde)
    {
        Assert.assertTrue(historieVerwerker.isHistorieInhoudelijkGelijk(nieuweHistorie, actueleRecord));
        ReflectionTestUtils.setField(nieuweHistorie, propertyNaam, andereWaarde);
        Assert.assertFalse(historieVerwerker.isHistorieInhoudelijkGelijk(nieuweHistorie, actueleRecord));
        ReflectionTestUtils.setField(actueleRecord, propertyNaam, andereWaarde);
        Assert.assertTrue(historieVerwerker.isHistorieInhoudelijkGelijk(nieuweHistorie, actueleRecord));
    }
}
